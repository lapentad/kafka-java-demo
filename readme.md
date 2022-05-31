# Using Kafka with a Java Producer and Consumer
- [Using Kafka with a Java Producer and Consumer](#using-kafka-with-a-java-producer-and-consumer)
- [Install Java on RHEL 8](#install-java-on-rhel-8)
- [Install Maven on RHEL 8](#install-maven-on-rhel)
- [Install Podman on RHEL 8](#install-podman-on-rhel-8)
- [Install Kafka as a container](#install-kafka-as-a-container)
- [Open up the Kafka port using ufw](#open-up-the-kafka-port-using-the-ufw-utility)
- [Running the unit tests](#running-the-unit-tests)
- [Starting the streaming producer](#starting-the-streaming-producer)
- [Starting an asynchronous consumer](#starting-an-asynchronous-consumer)
# Install Java on RHEL 8 or Fedora
```shell
sudo yum install java-1.8.0-openjdk-devel
```
Verify the installation

```shell
java -version
```

```text
openjdk 11.0.13 2021-10-19
OpenJDK Runtime Environment (build 11.0.13+8-Ubuntu-0ubuntu1.20.04)
OpenJDK 64-Bit Server VM (build 11.0.13+8-Ubuntu-0ubuntu1.20.04, mixed mode, sharing)
```

# Install Maven on RHEL or Fedora

```shell
sudo dnf install maven -y
```
Verify the installation

```text
Apache Maven 3.6.3
Maven home: /usr/share/maven
Java version: 11.0.13, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.4.0-100-generic", arch: "amd64", family: "unix"
```

# Install Podman on RHEL 8 or Fedora

```shell
sudo dnf -y update
```

```shell
sudo dnf install -y podman
```
Verify the installation:

```shell
podman version
```
You'll see output similar to the following:

```shell
Version:      3.4.2
API Version:  3.4.2
Go Version:   go1.16.6
Built:        Thu Jan  1 00:00:00 1970
OS/Arch:      linux/amd64
```

# Install Kafka as a container

```shell
podman run -it --name kafka-zkless -p 9092:9092 -e LOG_DIR=/tmp/logs quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64 /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'
```

# Open up the Kafka port using the `ufw` utility

Run ...

`sudo dnf install ufw`

Then ...

`sudo ufw enable && sudo ufw allow 9092`

# Running the unit tests

Run the following command from the top level of the directory in which you installed the demonstration code.

```shell
mvn test
```

# Starting the streaming producer

In a new terminal window, go to the directory in which this code is installed and execute the following command:

```shell
sh ./runproducer.sh "mytopic"
```

You see a steady stream of screen output that is the log output of messages being sent to the topic named `mytopic`.

# Starting an asynchronous consumer

In another terminal window, go to the directory in which this code is installed and execute the following command:

```shell
sh ./runconsumer.sh "mytopic"
```

You see a steady stream of screen output that is the log output of messages being retrieved from the topic named `mytopic`.

A sample of the output is as follows:

```
2022-05-31 09:45:53 DEBUG NetworkClient:521 - [Consumer clientId=consumer-test-group-1, groupId=test-group] Sending FETCH request with header RequestHeader(apiKey=FETCH, apiVersion=12, clientId=consumer-test-group-1, correlationId=86) and timeout 30000 to node 1: FetchRequestData(clusterId=null, replicaId=-1, maxWaitMs=500, minBytes=1, maxBytes=52428800, isolationLevel=0, sessionId=376433637, sessionEpoch=62, topics=[FetchTopic(topic='mytopic', topicId=vCvEuX_0QHWdVP5mKeeX4w, partitions=[FetchPartition(partition=0, currentLeaderEpoch=0, fetchOffset=787, lastFetchedEpoch=-1, logStartOffset=-1, partitionMaxBytes=1048576)])], forgottenTopicsData=[], rackId='')
{"bootstrapServers":"localhost:9092","topic":"mytopic","source":"com.demo.kafka.KafkaMessageHandlerImpl","message":"4GPeV7Igy9","key":"84097ac3-f488-4595-86cc-dcb69bce2eda"}
```

