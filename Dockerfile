FROM gradle as builder
WORKDIR /root
COPY . .
RUN ./gradlew shadowJar

FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
WORKDIR /root
COPY --from=builder /root/build/libs/Ser-1.1-all.jar ./app.jar
COPY --from=builder /root/.env ./.env
ENTRYPOINT ["java","-jar","./app.jar"]