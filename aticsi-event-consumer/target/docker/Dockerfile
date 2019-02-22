FROM openjdk:9-jdk
VOLUME /tmp
ARG JAR_FILE
COPY aticsi-event-consumer-0.0.1-SNAPSHOT.jar app.jar
COPY aticsi-pubsub.json gcp.json
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8090
ENV GOOGLE_APPLICATION_CREDENTIALS=gcp.json
