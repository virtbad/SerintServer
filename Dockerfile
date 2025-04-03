FROM gradle:6.7.0-jdk15 AS builder
WORKDIR /app

COPY . .

RUN gradle jar

RUN mv $(find build/libs/ -type f -regex '.*/SerintServer-[0-9X]+\.[0-9X]+\.jar') server.jar

FROM eclipse-temurin:16-alpine AS runner
WORKDIR /app

COPY --from=builder /app/server.jar ./server.jar

EXPOSE 17371

CMD ["java", "-jar", "server.jar"]

