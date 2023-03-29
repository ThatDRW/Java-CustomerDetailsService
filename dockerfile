FROM azul/zulu-openjdk-alpine:17-jre-headless

WORKDIR /app

COPY ./target/customerdetailsservice-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "customerdetailsservice-0.0.1-SNAPSHOT.jar"]