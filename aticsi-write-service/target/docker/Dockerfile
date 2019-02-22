FROM openjdk:9-jdk
VOLUME /tmp
ARG JAR_FILE
COPY aticsi-write-service-0.0.1-SNAPSHOT.jar app.jar
COPY aticsi-pubsub.json gcp.json
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
ENV GOOGLE_APPLICATION_CREDENTIALS=gcp.json
