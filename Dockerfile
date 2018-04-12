FROM java:8-alpine

RUN mkdir -p /app

COPY target/scala-2.12/*.jar /app/

WORKDIR /app

CMD java -jar /app/KeyVaultConfigMap.jar

