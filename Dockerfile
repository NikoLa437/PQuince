FROM node:14.11.0 as front

WORKDIR /usr/src/pquince
COPY PQuince.API/clientapp .
RUN ["npm","install"]
RUN ["npm", "run","build"]

FROM maven:3.6.3-ibmjava-8-alpine AS appServer

WORKDIR /usr/src/pquince
COPY . .
COPY --from=front /usr/src/pquince/build ./src/main/resources/static
RUN ["mvn", "package", "-DskipTests"]

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=appServer /usr/src/pquince/target/pquince-0.0.1-SNAPSHOT.jar ./

EXPOSE 8081

CMD ["java", "-jar","pquince-0.0.1-SNAPSHOT.jar"]