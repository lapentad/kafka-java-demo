#!/usr/bin/env bash

echo compile...

mvn -q clean compile exec:java \
 -Dexec.mainClass="com.demo.kafka.producer.ProducerApplication" \
 -Dexec.args="$1"