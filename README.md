# bankapp
Для работы приложения необходимо при старте задать следующие VM options для подключения к БД в System.properties
db_ip=*ip адрес БД*
db_login=*логин для подключения к БД*
db_password=*пароль для подключения к БД*

К примеру, из консоли
java -Ddb_id=localhost:5432 -Ddb_login=postgres -Ddb_password=postgres

