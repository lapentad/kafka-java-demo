#!/usr/bin/env bash

echo compile...

mvn -q clean compile exec:java \
 -Dexec.mainClass="com.demo.kafka.consumer.ConsumerApplication" \
 -Dexec.args="$1"