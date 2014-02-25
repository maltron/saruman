# Run the first script, so the variables get loaded into memory
source glassfish.sh
# Creating Database and inserting some data in it
mysql --user=${MYSQL_USERNAME} --password=${MYSQL_PASSWORD} < create-database.sql
# Symbolic link for MySQL Driver into GlassFish
ln -sv ${MYSQL_JDBC_FILE} ${GLASSFISH_LOCATION}/glassfish/domains/domain1/lib/
# Creating JDBC Connection Pool
asadmin create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=${MYSQL_USERNAME}:password=${MYSQL_PASSWORD}:DatabaseName=PERSON_DATA:ServerName=localhost:port=3306 PersonPool
# Creating JNDI regarding the Connection Pool
asadmin create-jdbc-resource --connectionpoolid PersonPool "jdbc/Person"
# Creating JMS for Notification
#asadmin create-jms-resource --restype javax.jms.Queue jms/QueueNotification
#asadmin create-jms-resource --restype javax.jms.QueueConnectionFactory jms/QueueConnectionNotification 
