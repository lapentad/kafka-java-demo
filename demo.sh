
echo "Starting 1 Producer on mytopic"
curl -X GET http://localhost:8080/startProducer/mytopic

echo "Starting 1 Consumer on mytopic"
curl -X GET http://localhost:8080/startConsumer/mytopic

