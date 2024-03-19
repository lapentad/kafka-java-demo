#!/bin/bash

# Source the utils script
source utils.sh

# Check Prerequisites
checkJava
checkMaven
checkDocker
checkDockerCompose

# Make build the demo docker image
make jar
make build

# Start the Docker containers in detached mode
docker-compose up -d

# Wait for the Kafka container to be running
wait_for_container "kafka-zkless" "Kafka Server started"

# Once Kafka container is running, start the producers and consumers
echo "Kafka container is up and running. Starting producer and consumer..."

echo "Start 1 Producer on mytopic"
echo "sh runproducer.sh mytopic"
#curl -X GET http://localhost:8080/startProducer/mytopic
echo ""

echo "Start 1 Consumer on mytopic"
echo "sh runconsumer.sh mytopic"
#curl -X GET http://localhost:8080/startConsumer/mytopic
echo ""

sleep 10

echo "Sending type1 to ICS"
curl -X 'PUT' \
  'http://localhost:8083/data-producer/v1/info-types/type1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "info_job_data_schema": {
    "$schema":"http://json-schema.org/draft-07/schema#",
    "title":"STD_Type1_1.0.0",
    "description":"Type 1",
    "type":"object"
  }
}'

echo "Getting types from ICS"
curl -X 'GET' 'http://localhost:8083/data-producer/v1/info-types/type1'
echo "++++++++++++++++++++++++++++++++++++++++++++++++++++"
echo ""

echo "Sending Producer infos to ICS"
curl -X 'PUT' \
  'http://localhost:8083/data-producer/v1/info-producers/1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "info_producer_supervision_callback_url": "http://kafka-java-demo-kafka-client-1:8080/producer/supervision",
  "supported_info_types": [
    "type1"
  ],
  "info_job_callback_url": "http://kafka-java-demo-kafka-client-1:8080/producer/job"
}'

echo "Getting Producers Infos from ICS"
curl -H 'Content-Type: application/json' 'http://localhost:8083/data-producer/v1/info-producers/1'
echo "++++++++++++++++++++++++++++++++++++++++++++++++++++"
echo ""

echo "Sending Consumer Job infos to ICS"
curl -X 'PUT' \
  'http://localhost:8083/data-consumer/v1/info-jobs/1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "info_type_id": "type1",
  "job_owner": "demo",
  "job_definition": {},
  "job_result_uri": "http://kafka-java-demo-kafka-client-1:8080/producer/job",
  "status_notification_uri": "http://kafka-java-demo-kafka-client-1:8080/producer/supervision"
}'

echo "Getting Consumer Job Infos from ICS"
curl -H 'Content-Type: application/json' 'http://localhost:8083/data-consumer/v1/info-jobs/1'
echo "++++++++++++++++++++++++++++++++++++++++++++++++++++"
echo ""

sleep 5
echo "ICS Producer Docker logs "
docker logs kafka-java-demo-informationcoordinator-1 | grep o.o.i.c.r1producer.ProducerCallbacks
echo ""
echo "Demo Producer Docker logs "
docker logs kafka-java-demo-kafka-client-1 | grep com.demo.kafka.controllers.ProducerController
echo ""

echo "Done."
