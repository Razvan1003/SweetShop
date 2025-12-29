FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY backend/pom.xml ./backend/pom.xml
COPY backend/src ./backend/src
RUN mvn -f backend/pom.xml -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/backend/target/sweet-shop-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
