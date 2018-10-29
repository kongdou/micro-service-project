docker stop zookeeper
docker rm zookeeper
docker run -p 2181:2181 --name zookeeper --restart always -d zookeeper:3.5
