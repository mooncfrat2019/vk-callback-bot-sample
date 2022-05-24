FROM gradle as builder
WORKDIR /root
COPY . .
RUN ./gradlew build
EXPOSE 5000
RUN ./gradlew run
