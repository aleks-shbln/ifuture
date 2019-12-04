package ru.ashabalin.account.service.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashabalin.account.service.dto.StatisticDto;
import ru.ashabalin.account.service.utils.Counter;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StatisticServiceImpl.class})
public class StatisticServiceImplTest {

    @Autowired
    private StatisticServiceImpl statisticService;

    @Test
    public void statisticCountedTest() {
        statisticService.addInvocation(Counter.ADD_AMOUNT);
        statisticService.addInvocation(Counter.GET_AMOUNT);
        Map<String, StatisticDto> statistic = statisticService.getStatistic();

        StatisticDto addStat = statistic.get(Counter.ADD_AMOUNT.getMethodName());
        assertEquals(1L, addStat.getRequestsCount());

        StatisticDto getStat = statistic.get(Counter.GET_AMOUNT.getMethodName());
        assertEquals(1L, getStat.getRequestsCount());
    }

    @Test
    public void resetStatisticTest() {
        statisticService.addInvocation(Counter.ADD_AMOUNT);
        statisticService.addInvocation(Counter.GET_AMOUNT);

        statisticService.resetStatistic();
        Map<String, StatisticDto> statistic = statisticService.getStatistic();

        StatisticDto addStat = statistic.get(Counter.ADD_AMOUNT.getMethodName());
        assertEquals(0L, addStat.getRequestsCount());
        assertEquals(0L, addStat.getRequestsPerLastSecond());
        assertEquals(0L, addStat.getRequestsPerLastTenSecond());
        assertEquals(0L, addStat.getRequestsPerLastMinute());

        StatisticDto getStat = statistic.get(Counter.GET_AMOUNT.getMethodName());
        assertEquals(0L, getStat.getRequestsCount());
        assertEquals(0L, getStat.getRequestsPerLastSecond());
        assertEquals(0L, getStat.getRequestsPerLastTenSecond());
        assertEquals(0L, getStat.getRequestsPerLastMinute());
    }

}
