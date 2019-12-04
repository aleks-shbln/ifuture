package ru.ashabalin.account.service.support;

import java.util.HashMap;
import java.util.Map;

public class Statistic {

    private long totalCounter = 0;
    private Map<Long, Long> perSecondCounters = new HashMap<>();

    public long getTotalCounter() {
        return totalCounter;
    }

    public void addTotalCounter() {
        this.totalCounter++;
    }

    public Map<Long, Long> getPerSecondCounters() {
        return perSecondCounters;
    }

    public void addPerSecondCounter(Long second) {
        Long currentCounter = this.perSecondCounters.get(second);
        if (currentCounter != null) {
            this.perSecondCounters.put(second, currentCounter + 1L);
        } else {
            this.perSecondCounters.put(second, 1L);
        }
    }

    public void removeOlderThat(Long threshold) {
        this.perSecondCounters.entrySet()
                .removeIf(entry -> entry.getKey() < threshold);
    }
}
