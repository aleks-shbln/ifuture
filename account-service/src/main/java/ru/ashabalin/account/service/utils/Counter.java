package ru.ashabalin.account.service.utils;

public enum Counter {
    GET_AMOUNT("getAmount"),
    ADD_AMOUNT("addAmount");

    private final String methodName;

    Counter(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return this.methodName;
    }
}
