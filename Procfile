web: java $JAVA_OPTS -Xmx256m --spring.profiles.active=prod,heroku,no-liquibase --server.port=$PORT
release: cp -R src/main/resources/config config && ./mvnw liquibase:update -Pprod,heroku
