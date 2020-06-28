FROM oracle/graalvm-ce:20.1.0-java8 as graalvm
RUN gu install native-image

COPY . /home/app/demo-cli
WORKDIR /home/app/demo-cli

RUN native-image --no-server -cp build/libs/demo-cli-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/demo-cli/demo-cli /app/demo-cli
ENTRYPOINT ["/app/demo-cli"]
