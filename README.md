# Using Kafka with a Java Producer and Consumer


# Install Java Maven and Docker

```shell
sh ./prerun.sh
```

# Install Kafka as a container

```shell
docker run -it -d --name kafka-zkless -p 9092:9092 -e LOG_DIR=/tmp/logs quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64 /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'
```
Or use docker-compose
```shell
docker-compose up -d
```

# Starting the streaming producer

In a new terminal window:

```shell
sh ./runproducer.sh "mytopic"
```

# Starting an asynchronous consumer

In another terminal window:

```shell
sh ./runconsumer.sh "mytopic"
```

You see a steady stream of screen output that is the log output of messages being retrieved from the topic named `mytopic`.

A sample of the output is as follows:

```
{"bootstrapServers":"localhost:9092","topic":"mytopic","source":"com.demo.kafka.KafkaMessageHandlerImpl","message":"4GPeV7Igy9","key":"84097ac3-f488-4595-86cc-dcb69bce2eda"}
```
