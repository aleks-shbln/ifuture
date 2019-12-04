package ru.ashabalin.account.client;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import ru.ashabalin.account.client.configuration.ClientProperties;
import ru.ashabalin.account.client.configuration.ConfigParser;
import ru.ashabalin.account.client.remote.AccountClient;
import ru.ashabalin.account.client.workers.AmountReader;
import ru.ashabalin.account.client.workers.AmountWriter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // configuration
        ConfigParser configParser = new ConfigParser();
        ClientProperties clientProperties = configParser.parseParameters(args);

        // service client
        AccountClient accountClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(AccountClient.class, clientProperties.getUri());

        // start threads
        for (int i = 0; i < clientProperties.getrCount(); i++) {
            new Thread(new AmountReader(accountClient, clientProperties)).start();
        }
        for (int i = 0; i < clientProperties.getwCount(); i++) {
            new Thread(new AmountWriter(accountClient, clientProperties)).start();
        }

        System.out.println("Run configuration " + clientProperties);
        System.out.println("Press CTRL+C to exit");
    }

}
