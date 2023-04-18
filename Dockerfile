FROM gradle:jdk17-alpine

WORKDIR /app/userservice

COPY . /app/userservice/

ENTRYPOINT gradle bootRun