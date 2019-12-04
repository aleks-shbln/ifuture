package ru.ashabalin.account.client.workers;

import feign.RetryableException;
import ru.ashabalin.account.client.configuration.ClientProperties;
import ru.ashabalin.account.client.remote.AccountClient;

public class AmountReader implements Runnable {

    private final AccountClient accountClient;
    private final ClientProperties clientProperties;

    public AmountReader(AccountClient accountClient, ClientProperties clientProperties) {
        this.accountClient = accountClient;
        this.clientProperties = clientProperties;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                for (Long id : clientProperties.getIdList()) {
                    accountClient.getAmount(id);
                }
            } catch (Exception ex) {
                System.out.println("Try to 'readAmount' again after error " + ex.getMessage());
            }
        }
    }

}
