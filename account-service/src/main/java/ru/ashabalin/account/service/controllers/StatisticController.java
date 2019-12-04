package ru.ashabalin.account.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ashabalin.account.service.dto.StatisticDto;
import ru.ashabalin.account.service.services.StatisticService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/statistic")
public class StatisticController {

    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping
    public ResponseEntity<Map<String, StatisticDto>> getStatistic() {
        return ResponseEntity.ok(statisticService.getStatistic());
    }

    @DeleteMapping()
    public ResponseEntity<Void> reset() {
        statisticService.resetStatistic();
        return ResponseEntity.ok().build();
    }

}
