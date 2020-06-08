FROM maven AS build
WORKDIR /guitar-repair-service-spring
COPY . .
RUN mvn package -Dmaven.test.skip=true

FROM tomcat
COPY --from=build /guitar-repair-service-spring/target/repairservice-1.0-SNAPSHOT.war //usr/local/tomcat/webapps/ROOT.war
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
