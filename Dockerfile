FROM openjdk:11
VOLUME /tmp
EXPOSE 8011
ADD ./target/yankiaccount-0.0.1-SNAPSHOT.jar yankiaccount.jar
ENTRYPOINT ["java","-jar","/customer.yankiaccount"]