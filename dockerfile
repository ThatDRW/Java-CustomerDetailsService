#######################################
#               GITHUB                #
#######################################
FROM --platform=$BUILDPLATFORM maven:3.9-eclipse-temurin-17 AS github
RUN apt-get update \
 && apt-get install -y --no-install-recommends git

RUN useradd -s /bin/bash -m vscode \
 && groupadd docker \
 && usermod -aG docker vscode

WORKDIR /workdir/server
RUN git clone https://github.com/ThatDRW/Java-CustomerDetailsService.git
WORKDIR /workdir/server/Java-CustomerDetailsService
RUN find . -maxdepth 1 -exec mv {} .. \;


#######################################
#               BUILDER               #
#######################################
FROM github as builder
WORKDIR /workdir/server
# COPY pom.xml /workdir/server/pom.xml
RUN mvn dependency:go-offline

COPY src /workdir/server/src
RUN mvn install


#######################################
#           GITHUB DEV-ENV            #
#######################################
FROM builder AS dev-envs
CMD ["mvn", "spring-boot:run"]


#######################################
#       PREPARING FOR PRODUCTION      #
#######################################
FROM builder as prepare-production
RUN mkdir -p target/dependency
WORKDIR /workdir/server/target/dependency
RUN jar -xf ../*.jar


#######################################
#             DEPLOYMENT              #
#######################################
FROM eclipse-temurin:17-jre-focal
EXPOSE 8080
VOLUME /tmp
ARG DEPENDENCY=/workdir/server/target/dependency
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.thatdrw.customerdetailsservice.CustomerdetailsserviceApplication"]
