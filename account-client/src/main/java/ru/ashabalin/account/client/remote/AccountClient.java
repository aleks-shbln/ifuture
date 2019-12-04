package ru.ashabalin.account.client.remote;

import feign.Param;
import feign.RequestLine;

public interface AccountClient {

    @RequestLine("GET /api/v1/account/{id}")
    Long getAmount(@Param("id") Long id);

    @RequestLine("POST /api/v1/account/{id}?value={value}")
    void addAmount(@Param("id") Long id, @Param("value") Long value);

}
