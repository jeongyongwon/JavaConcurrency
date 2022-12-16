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



## Intelli J 테스트코드 파일 생성 단축키

```
클래스 명에 마우스 올리고 alt + enter
```

