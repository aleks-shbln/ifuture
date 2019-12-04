package ru.ashabalin.account.client.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigParser {

    private final String R_COUNT = "rCount";
    private final String W_COUNT = "wCount";
    private final String ID_LIST = "idList";
    private final String URI = "uri";

    public ClientProperties parseParameters(String[] args) throws IOException {
        Properties argumentProps = new Properties();
        Properties fileProps = new Properties();

        for (String arg : args) {
            argumentProps.setProperty(getKey(arg), getValue(arg));
        }
        if (argumentProps.getProperty("configFile") != null) {
            fileProps.load(new FileInputStream(argumentProps.getProperty("configFile")));
        }

        String rCountStr = getPropertyValue(argumentProps, fileProps, R_COUNT);
        String wCountStr = getPropertyValue(argumentProps, fileProps, W_COUNT);
        String idListStr = getPropertyValue(argumentProps, fileProps, ID_LIST);
        String uriStr = getPropertyValue(argumentProps, fileProps, URI);

        ClientProperties clientProperties = new ClientProperties();
        if (!isNullOrEmpty(rCountStr)) {
            clientProperties.setrCount(Integer.parseInt(rCountStr));
        }
        if (!isNullOrEmpty(wCountStr)) {
            clientProperties.setwCount(Integer.parseInt(wCountStr));
        }
        if (!isNullOrEmpty(idListStr)) {
            clientProperties.setIdList(Stream.of(idListStr.split(",")).map(Long::parseLong).collect(Collectors.toList()));
        }
        if (!isNullOrEmpty(uriStr)) {
            clientProperties.setUri(uriStr);
        }
        return clientProperties;
    }

    private String getPropertyValue(Properties argProps, Properties fileProps, String propName) {
        return argProps.getProperty(propName, fileProps.getProperty(propName));
    }

    private String getKey(String arg) {
        return arg.substring(0, arg.indexOf('='));
    }

    private String getValue(String arg) {
        return arg.substring(arg.indexOf('=') + 1);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
