package ru.ashabalin.account.service.services;

import ru.ashabalin.account.service.dto.StatisticDto;
import ru.ashabalin.account.service.utils.Counter;

import java.util.Map;

public interface StatisticService {

    void addInvocation(Counter counter);

    Map<String, StatisticDto> getStatistic();

    void resetStatistic();

}
