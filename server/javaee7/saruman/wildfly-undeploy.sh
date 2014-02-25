# Import the correct variables
source wildfly.sh
${WILDFLY_LOCATION}/bin/jboss-cli.sh --connect --command="undeploy Saruman"
