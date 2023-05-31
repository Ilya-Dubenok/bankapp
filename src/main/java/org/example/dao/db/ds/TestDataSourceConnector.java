package org.example.dao.db.ds;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import javax.sql.DataSource;
import java.util.Properties;

public class TestDataSourceConnector {

    private DataSource dataSource;

    private boolean isFirstConnection = true;


    private static class Holder {
        private static final TestDataSourceConnector instance = new TestDataSourceConnector();
    }

    private TestDataSourceConnector() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:postgresql://localhost:5432/postgres";
        Properties props = new Properties();

        props.setProperty("dataSource.user", "postgres");
        props.setProperty("dataSource.password", "postgres");
        props.setProperty("jdbcUrl", url);
        HikariConfig config = new HikariConfig(props);
        this.dataSource = new HikariDataSource(config);


    }

    ;


    public static TestDataSourceConnector getInstance() {
        return Holder.instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void initDbBase() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        try {

            populator.addScript(new ClassPathResource("createTestDatabase.sql"));
            populator.execute(this.dataSource);
        } catch (Exception e) {

        }


        String url = "jdbc:postgresql://localhost:5432/test_bank_app";
        Properties props = new Properties();

        props.setProperty("dataSource.user", "postgres");
        props.setProperty("dataSource.password", "postgres");
        props.setProperty("jdbcUrl", url);
        HikariConfig config = new HikariConfig(props);
        this.dataSource = new HikariDataSource(config);
        populator = new ResourceDatabasePopulator();

        populator.addScript(new ClassPathResource("createTestSchemaTableEtc.sql"));
        populator.execute(this.dataSource);
    }


}
