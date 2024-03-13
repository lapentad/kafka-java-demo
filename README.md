# Using Kafka with a Java Producer and Consumer


# Install Java Maven and Docker

```shell
sh ./prerun.sh
```

# Install Kafka as a container

```shell
sh ./runzlkafka.sh
```
Or use docker-compose
```shell
docker-compose up -d
```

# Starting the REST application

In a new terminal window:

```shell
mvn spring-boot:run
```

# Starting a producer 

In another window do a GET /startProducer/{topicName}

```shell
http://localhost:8080/startProducer/mytopic
```

# Starting a Consumer 

In another window do a GET /startConsumer/{topicName}

```shell
http://localhost:8080/startConsumer/mytopic
```

# Reading the logs

The logs will be saved in the log/ folder
- app.log
- Consumer.log
- Producer.log 

A sample of the output is as follows:

```
{"bootstrapServers":"localhost:9092","topic":"mytopic","source":"com.demo.kafka.KafkaMessageHandlerImpl","message":"4GPeV7Igy9","key":"84097ac3-f488-4595-86cc-dcb69bce2eda"}
```
