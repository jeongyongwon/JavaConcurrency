## MySQL Docker 설치 및 구동 (Window 기준)

```bash
docker pull mysql
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=<password> --name mysql mysql
docker ps 
```



## MySQL DB 생성

```BASH
docker exec -it mysql bash
mysql -u root -p
create database stock_example;
use stock_example;
```

