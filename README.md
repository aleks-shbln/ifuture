# account-parent
Contains server and client modules. 

## Build all
```
mvn package
```

# account-service
Account service with Balance and Statistic functionality

## Run tests
```
mvn test -pl account-service
```

## Build
```
mvn package -pl account-service
```

## Run
```
mvn spring-boot:run -Dspring-boot.run.profiles=local -pl account-service
```

# account-client
Load test tool for `account-service`

## Build
```
mvn package -pl account-client
```

## Usage
With parameters in command line:
```
java -jar account-client-1.0-SNAPSHOT-jar-with-dependencies.jar rCount=1 wCount=1 idList=1,2,3
```

With parameters in config file:
```
java -jar account-client-1.0-SNAPSHOT-jar-with-dependencies.jar configFile=C:\tmp\app.properties
```

Command line arguments take precedence and can be used in conjunction 
with `configFile` argument to override properties from file.