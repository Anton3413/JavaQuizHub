FROM alpine:latest


RUN apk add git openjdk17
RUN git clone https://github.com/Anton3413/JavaQuizHub.git

WORKDIR JavaQuizHub

RUN  chmod +x mvnw && ./mvnw install -DskipTests && cp target/JavaQuizHub-*.jar ./service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "service.jar"]

