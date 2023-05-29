package org.example;

import org.example.dao.db.ds.DataSourceConnector;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class Main {

    public static void main(String[] args) {
        DataSource ds = DataSourceConnector.getInstance().getDataSource();


    }
}
