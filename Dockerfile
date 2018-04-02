FROM java:8

WORKDIR /app

ADD pom.xml /app/pom.xml
ADD repo /app/repo
ADD src /app/src

RUN apt-get -qq update \
  && apt-get -qq install -y maven \
  && mvn dependency:resolve \
  && mvn verify \
  && mvn package \
  && mv /app/target/clausieserver-jar-with-dependencies.jar /app/clausieserver.jar \
  && rm -rf /app/target /app/repo /app/src /app/pom.xml \
  && apt-get -qq remove -y maven

ENV JAVA_XMX="2G"

EXPOSE 4567
CMD /usr/lib/jvm/java-8-openjdk-amd64/bin/java -Xmx${JAVA_XMX} -jar /app/clausieserver.jar
