FROM java:8

RUN apt-get update \
  && apt-get install -y maven

WORKDIR /app

ADD repo /app/repo
ADD pom.xml /app/pom.xml
RUN mvn dependency:resolve && mvn verify

ADD src /app/src
RUN mvn package \
  && mv /app/target/clausieserver-jar-with-dependencies.jar /app/clausieserver.jar \
  && rm -rf /app/target

ENV JAVA_XMX="2G"

EXPOSE 4567
CMD /usr/lib/jvm/java-8-openjdk-amd64/bin/java -Xmx${JAVA_XMX} -jar /app/clausieserver.jar
