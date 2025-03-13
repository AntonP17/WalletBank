FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY wallet/target/wallet-0.0.1-SNAPSHOT.jar /app/wallet.jar
ENTRYPOINT ["java", "-jar", "wallet.jar"]
