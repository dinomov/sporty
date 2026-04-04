FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

RUN cp target/sports-betting-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]