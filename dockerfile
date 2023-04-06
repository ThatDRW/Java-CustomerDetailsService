#######################################
#            CONFIGURATION            #
#   Only required to set ARGs below   #
#######################################
ARG C_WORKING_DIR=/workdir/server
ARG C_ENTRY_POINT="com.thatdrw.customerdetailsservice.CustomerdetailsserviceApplication"
ARG C_EXPOSE_PORT=8080


#######################################
#               BUILDER               #
#######################################
FROM --platform=$BUILDPLATFORM maven:3.9-eclipse-temurin-17 as builder
WORKDIR ${C_WORKING_DIR}
COPY pom.xml ${C_WORKING_DIR}/pom.xml

RUN mvn dependency:go-offline
COPY src ${C_WORKING_DIR}/src

RUN mvn install


#######################################
#          PREPARE-PRODUCTION         #
#######################################
FROM builder as prepare-production
RUN mkdir -p target/dependency
WORKDIR ${C_WORKING_DIR}/target/dependency
RUN jar -xf ../*.jar


#######################################
#             PRODUCTION              #
#######################################
FROM eclipse-temurin:17-jre-focal as production
EXPOSE ${C_EXPOSE_PORT}
VOLUME /tmp
ARG DEPENDENCY=${C_WORKING_DIR}/target/dependency
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*",${C_ENTRY_POINT}]
