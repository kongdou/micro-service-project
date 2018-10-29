docker stop redis
docker rm redis
docker run -p 6379:6379 --name redis -d redis:3.2 --requirepass "123456"