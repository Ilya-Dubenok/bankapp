package org.example.dao.db.ds;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceConnector {

    private final DataSource dataSource;

    private boolean isFirstConnection = true;



    private static class Holder{
        private static final DataSourceConnector instance = new DataSourceConnector();
    }

    private DataSourceConnector(){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String db_host_address = System.getProperty("db_ip");
        String db_login = System.getProperty("db_login");
        String db_password = System.getProperty("db_password");

        String url = "jdbc:postgresql://"+db_host_address+"/bank_app";
        Properties props = new Properties();

        props.setProperty("dataSource.user", db_login);
        props.setProperty("dataSource.password", db_password);
        props.setProperty("jdbcUrl", url);
        HikariConfig config = new HikariConfig(props);
        this.dataSource = new HikariDataSource(config);



    };


    public static DataSourceConnector getInstance(){
        return Holder.instance;
    }

    public DataSource getDataSource() {
        if (isFirstConnection) {
            initDbBase();
            isFirstConnection = false;
        }
        return dataSource;
    }

    private void initDbBase() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.addScript(new ClassPathResource("createSchemaTableEtc.sql"));
        populator.execute(this.dataSource);
    }


}
