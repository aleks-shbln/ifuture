package ru.ashabalin.account.client.configuration;

import java.util.ArrayList;
import java.util.List;

public class ClientProperties {

    private int rCount = 0;
    private int wCount = 0;
    private List<Long> idList = new ArrayList<>();
    private String uri = "http://localhost:8080";

    public int getrCount() {
        return rCount;
    }

    public void setrCount(int rCount) {
        this.rCount = rCount;
    }

    public int getwCount() {
        return wCount;
    }

    public void setwCount(int wCount) {
        this.wCount = wCount;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ClientProperties{" +
                "rCount=" + rCount +
                ", wCount=" + wCount +
                ", idList=" + idList +
                ", uri='" + uri + '\'' +
                '}';
    }
}
