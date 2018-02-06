# 质数的计算

基本的方法

## Spring Cloud Stream Apps属性文件转换


将sink.file=http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/file-sink-kafka-10/1.3.1.RELEASE/file-sink-kafka-10-1.3.1.RELEASE.jar

转换为 maven:org.springframework.cloud.stream.app:file-sink-kafka-10:1.3.1.RELEASE

文件来源 http://cloud.spring.io/spring-cloud-stream-app-starters/


### Spring Cloud Data Flow的基本使用

```bash
cd /opt/kafka

bin/zookeeper-server-start.sh config/zookeeper.properties

bin/kafka-server-start.sh config/server.properties

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic output --from-beginning

cd /opt

java -jar spring-cloud-dataflow-server-local-1.3.0.RELEASE.jar --maven.remote-repositories.nexus.url=http://repo.taiji.com.cn:8081/repository/maven-public/
```
or create folder config under which add application.properties with 

```properties
maven.remote-repositories.nexus.url=http://repo.taiji.com.cn:8081/repository/maven-public/
```
and run
```bash
java -jar spring-cloud-dataflow-server-local-1.3.0.RELEASE.jar

cd /opt

java -jar spring-cloud-dataflow-shell-1.3.0.RELEASE.jar
```
```
app register --name time --type source --uri maven:org.springframework.cloud.stream.app:time-source-kafka-10:1.3.1.RELEASE

app register --name log  --type sink   --uri maven:org.springframework.cloud.stream.app:log-sink-kafka-10:1.3.1.RELEASE


app import file:kafka-10.properties
```

## 2018/02/06
Upgrade version to 1.3.0