package ru.ashabalin.account.service.dto;

public class StatisticDto {

    private Long requestsCount;
    private Long requestsPerLastSecond;
    private Long requestsPerLastTenSecond;
    private Long requestsPerLastMinute;

    public StatisticDto() {
    }

    public StatisticDto(Long requestsCount, Long requestsPerLastSecond, Long requestsPerLastTenSecond, Long requestsPerLastMinute) {
        this.requestsCount = requestsCount;
        this.requestsPerLastSecond = requestsPerLastSecond;
        this.requestsPerLastTenSecond = requestsPerLastTenSecond;
        this.requestsPerLastMinute = requestsPerLastMinute;
    }

    public Long getRequestsCount() {
        return requestsCount;
    }

    public void setRequestsCount(Long requestsCount) {
        this.requestsCount = requestsCount;
    }

    public Long getRequestsPerLastSecond() {
        return requestsPerLastSecond;
    }

    public void setRequestsPerLastSecond(Long requestsPerLastSecond) {
        this.requestsPerLastSecond = requestsPerLastSecond;
    }

    public Long getRequestsPerLastTenSecond() {
        return requestsPerLastTenSecond;
    }

    public void setRequestsPerLastTenSecond(Long requestsPerLastTenSecond) {
        this.requestsPerLastTenSecond = requestsPerLastTenSecond;
    }

    public Long getRequestsPerLastMinute() {
        return requestsPerLastMinute;
    }

    public void setRequestsPerLastMinute(Long requestsPerLastMinute) {
        this.requestsPerLastMinute = requestsPerLastMinute;
    }

    @Override
    public String toString() {
        return "StatisticDto{" +
                "requestsCount=" + requestsCount +
                ", requestsPerLastSecond=" + requestsPerLastSecond +
                ", requestsPerLastTenSecond=" + requestsPerLastTenSecond +
                ", requestsPerLastMinute=" + requestsPerLastMinute +
                '}';
    }
}
