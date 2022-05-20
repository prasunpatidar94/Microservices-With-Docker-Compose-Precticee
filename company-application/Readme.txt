docker container run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=test -e MYSQL_USER=prasun -e MYSQL_PASSWORD=test --network=mysql_spring_boot -v=mysql <mysql iamge id>



=> zipkin run on docker container
docker run -d -p 9411:9411 openzipkin/zipkin