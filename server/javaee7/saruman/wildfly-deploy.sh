# Import the correct variables
source wildfly.sh
${WILDFLY_LOCATION}/bin/jboss-cli.sh --connect --command="deploy target/saruman-1.0-SNAPSHOT.war --name=Saruman --runtime-name=saruman-1.0-SNAPSHOT.war"
