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



## redis container 생성 및 구동

```bash
docker run --name myredis -d -p 6379:6379 redis
```



## redis container 접속 후 redis-cli 접속

```
docker exec -it <Container ID 또는 Names> redis-cli
```



## redis set 명령어

```bash
set lock 1 # 데이터 만들기
del lock # 데이터 지우기
subscribe ch1 # ch1 구독하기
publish ch1 hello # ch1에 hello 명령어 보내기
```



## lettuce와 Redisson 비교

> **Lettuce**구현이 간단하다. spring data redis 를 이용하면 lettuce 가 기본이기때문에 별도의 라이브러리를 사용하지 않아도 된다.spin lock 방식이기때문에 동시에 많은 스레드가 lock 획득 대기 상태라면 redis 에 부하가 갈 수 있다.
>
> **Redisson** 락 획득 재시도를 기본으로 제공한다.pub-sub 방식으로 구현이 되어있기 때문에 lettuce 와 비교했을 때 redis 에 부하가 덜 간다.별도의 라이브러리를 사용해야한다.lock 을 라이브러리 차원에서 제공해주기 떄문에 사용법을 공부해야 한다.
>
> 
>
> 재시도가 필요하지 않은 lock 은 lettuce 활용
>
> 재시도가 필요한 경우에는 redisson 를 활용

