#------------------------------
# BUILD
#------------------------------
FROM maven:3.8.7-eclipse-temurin-17 as build
WORKDIR /app
COPY pom.xml .
COPY src/ src/
RUN mvn -f pom.xml clean package


#------------------------------
# RUN
#------------------------------
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/*.jar /db-demo.jar
ENTRYPOINT ["java","-jar","/db-demo.jar"]