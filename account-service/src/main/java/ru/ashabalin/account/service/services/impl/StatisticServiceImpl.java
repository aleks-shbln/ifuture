package ru.ashabalin.account.service.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ashabalin.account.service.dto.StatisticDto;
import ru.ashabalin.account.service.support.Statistic;
import ru.ashabalin.account.service.services.StatisticService;
import ru.ashabalin.account.service.utils.Counter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StatisticServiceImpl implements StatisticService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static final long MAX_RETENTION_IN_SEC = 60;
    private static final long DELETE_INTERVAL_IN_MILLIS = MAX_RETENTION_IN_SEC * 1000;

    private ConcurrentHashMap<String, Statistic> counters = new ConcurrentHashMap<>();

    public StatisticServiceImpl() {
        for (Counter counter : Counter.values()) {
            counters.put(counter.getMethodName(), new Statistic());
        }
    }

    @Override
    public void addInvocation(Counter counter) {
        counters.compute(counter.getMethodName(), (key, statistic) -> {
            long epochSecond = Instant.now().getEpochSecond();
            if (statistic == null) {
                statistic = new Statistic();
            }
            statistic.addTotalCounter();
            statistic.addPerSecondCounter(epochSecond);
            return statistic;
        });
    }

    @Override
    public Map<String, StatisticDto> getStatistic() {
        Map<String, StatisticDto> statisticMap = new HashMap<>();
        for (Map.Entry<String, Statistic> entry : counters.entrySet()) {
            Statistic statistic = entry.getValue();
            long requestsSum = statistic.getTotalCounter();

            Map<Long, Long> requestMap = statistic.getPerSecondCounters();
            long epochSec = Instant.now().getEpochSecond() - 1;
            long atSecond = getRequestsAtSecond(requestMap, epochSec);
            long perTenSecond = getRequestsPerTenSecond(requestMap, epochSec, atSecond);
            long perMinute = getRequestsPerMinute(requestMap, epochSec, atSecond);

            String counterName = entry.getKey();
            statisticMap.put(counterName, new StatisticDto(requestsSum, atSecond, perTenSecond, perMinute));
        }
        return statisticMap;
    }

    @Override
    public void resetStatistic() {
        for (Map.Entry<String, Statistic> entry : counters.entrySet()) {
            counters.put(entry.getKey(), new Statistic());
        }
    }

    @Scheduled(fixedDelay = DELETE_INTERVAL_IN_MILLIS, initialDelay = DELETE_INTERVAL_IN_MILLIS)
    public void deleteOldStatistic() {
        long threshold = Instant.now().getEpochSecond() - MAX_RETENTION_IN_SEC;
        for (Map.Entry<String, Statistic> entry : counters.entrySet()) {
            log.debug("Clear statistic of {} older than {} ({})", entry.getKey(), Instant.ofEpochSecond(threshold), threshold);
            entry.getValue().removeOlderThat(threshold);
        }
    }

    private long getRequestsAtSecond(Map<Long, Long> requestMap, long epochSecond) {
        Long requestsInCurrentSecond = requestMap.get(epochSecond);
        return requestsInCurrentSecond == null ? 0 : requestsInCurrentSecond;
    }

    private long getRequestsPerTenSecond(Map<Long, Long> requestMap, long epochSecond, long currentCount) {
        return getRequestsFromPeriod(requestMap, epochSecond, currentCount, 9);
    }

    private long getRequestsPerMinute(Map<Long, Long> requestMap, long epochSecond, long currentCount) {
        return getRequestsFromPeriod(requestMap, epochSecond, currentCount, 59);
    }

    private long getRequestsFromPeriod(Map<Long, Long> requestMap, long startSecond, long currentCount, long period) {
        long requestCounter = currentCount;
        long startOfPeriod = startSecond - period;
        for (long endOfPeriod = (startSecond - 1); endOfPeriod > startOfPeriod; endOfPeriod--) {
            requestCounter += getRequestsAtSecond(requestMap, endOfPeriod);
        }
        return requestCounter;
    }
}
