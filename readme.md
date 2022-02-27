# Using Kafka with a Java Producer and Consumer

- [Using Kafka with a Java Producer and Consumer](#using-kafka-with-a-java-producer-and-consumer)
- [Install Java](#install-java)
- [Install Maven](#install-maven)
- [Install Podman](#install-podman)
- [Install Kafka as a container](#install-kafka-as-a-container)
- [Open up the Kafka port](#open-up-the-kafka-port)
- [Creating the streaming producer](#creating-the-streaming-producer)
- [Creating a synchronous consumer](#creating-a-synchronous-consumer)
- [Creating an asynchronous consumer](#creating-an-asynchronous-consumer)

# Install Java

`sudo apt-get update -y`

`sudo apt-get install default-jdk -y`

Verify the installation

`java -version`

You'll get output similar to the following:

```text
openjdk version "11.0.9.1" 2020-11-04
OpenJDK Runtime Environment (build 11.0.9.1+1-Ubuntu-0ubuntu1.20.04)
OpenJDK 64-Bit Server VM (build 11.0.9.1+1-Ubuntu-0ubuntu1.20.04, mixed mode, sharing)
```

# Install Maven

`sudo apt-get install maven -y`

Verify Maven is running:

`mvn -version`

You'll get output similar to the following:

```text
Apache Maven 3.6.3
Maven home: /usr/share/maven
Java version: 11.0.9.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.4.0-29-generic", arch: "amd64", family: "unix"
```

# Install Podman

It's a cool daemonless container manager.

```text
. /etc/os-release
```
```text
echo "deb https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable/xUbuntu_${VERSION_ID}/ /" | sudo tee /etc/apt/sources.list.d/devel:kubic:libcontainers:stable.list
```

```text
curl -L "https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable/xUbuntu_${VERSION_ID}/Release.key" | sudo apt-key add -
```

```text
sudo apt update -y
```

```text
sudo apt -y install podman
```

Verify the installation:

`podman version`

You'll get out put similar to the following:

```text
Version:      3.4.2
API Version:  3.4.2
Go Version:   go1.16.6
Built:        Thu Jan  1 00:00:00 1970
OS/Arch:      linux/amd64
```


# Install Kafka as a container

```bash
podman run -it --name kafka-zkless -p 9092:9092 -e LOG_DIR=/tmp/logs quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64 /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'
```

# Open up the Kafka port 

`sudo ufw enable && sudo ufw allow 9092`

# Creating the streaming producer

`TO BE PROVIDED`

# Creating a synchronous consumer

`TO BE PROVIDED`

# Creating an asynchronous consumer

`TO BE PROVIDED`