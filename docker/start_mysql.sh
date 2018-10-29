cur_dir=`pwd`
docker stop deepsky-mysql
docker rm deepsky-mysql
docker run --name deepsky-mysql -v ${cur_dir}/mysql/data:/var/lib/mysql -v ${cur_dir}/mysql/conf:/etc/mysql/conf.d -p 3309:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7.19
