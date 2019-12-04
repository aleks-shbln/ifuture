package ru.ashabalin.account.client.workers;

import feign.RetryableException;
import ru.ashabalin.account.client.configuration.ClientProperties;
import ru.ashabalin.account.client.remote.AccountClient;

public class AmountWriter implements Runnable {

    private static final Long ADD_AMOUNT = 10L;

    private final AccountClient accountClient;
    private final ClientProperties clientProperties;

    public AmountWriter(AccountClient accountClient, ClientProperties clientProperties) {
        this.accountClient = accountClient;
        this.clientProperties = clientProperties;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                for (Long id : clientProperties.getIdList()) {
                    accountClient.addAmount(id, ADD_AMOUNT);
                }
            } catch (Exception ex) {
                System.out.println("Try to 'addAmount' again after error " + ex.getMessage());
            }
        }
    }

}
