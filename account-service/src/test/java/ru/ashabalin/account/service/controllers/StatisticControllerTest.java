package ru.ashabalin.account.service.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.ashabalin.account.service.dto.StatisticDto;
import ru.ashabalin.account.service.services.StatisticService;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {StatisticController.class})
@WebMvcTest
public class StatisticControllerTest {

    @MockBean
    private StatisticService statisticService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getStatisticTest() throws Exception {
        Map<String, StatisticDto> statisticDtoMap = new HashMap<>();
        statisticDtoMap.put("counterName", new StatisticDto(1L, 2L, 3L, 4L));

        when(statisticService.getStatistic()).thenReturn(statisticDtoMap);
        mockMvc.perform(get("/api/v1/statistic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.counterName.requestsCount", is(1)))
                .andExpect(jsonPath("$.counterName.requestsPerLastSecond", is(2)))
                .andExpect(jsonPath("$.counterName.requestsPerLastTenSecond", is(3)))
                .andExpect(jsonPath("$.counterName.requestsPerLastMinute", is(4)))
        ;
    }

    @Test
    public void resetTest() throws Exception {
        mockMvc.perform(delete("/api/v1/statistic"))
                .andExpect(status().isOk());

        verify(statisticService).resetStatistic();
    }
}
