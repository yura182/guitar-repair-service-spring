FROM maven AS build
WORKDIR /guitar-repair-service-spring
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM tomcat
RUN rm -fr /usr/local/tomcat/webapps/ROOT
COPY --from=build /guitar-repair-service-spring/target/repairservice-1.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
