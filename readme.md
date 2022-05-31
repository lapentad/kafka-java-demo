# Using Kafka with a Java Producer and Consumer
- [Using Kafka with a Java Producer and Consumer](#using-kafka-with-a-java-producer-and-consumer)
- [Install Java on RHEL 8](#install-java-on-rhel-8)
- [Install Maven on RHEL 8](#install-maven-on-rhel)
- [Install Podman on RHEL 8](#install-podman-on-rhel-8)
- [Install Kafka as a container](#install-kafka-as-a-container)
- [Open up the Kafka port](#open-up-the-kafka-port)
- [Running the unit tests](#running-the-unit-tests)
- [Starting the streaming producer](#starting-the-streaming-producer)
- [Starting an asynchronous consumer](#starting-an-asynchronous-consumer)
# Install Java on RHEL 8 or Fedora
```shell
sudo yum install java-1.8.0-openjdk-devel
```
Verify the installation

```shell
java --version
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
sudo dnf module list | grep container-tools
```

```shell
sudo dnf install -y @container-tools
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

# Open up the Kafka port 

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

