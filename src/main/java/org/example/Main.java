package org.example;

import org.example.dao.db.WeekendDbDao;
import org.example.dao.db.ds.DataSourceConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        //TODO DELETE
        //Метод для быстрого теста, есть ли коннект с базой


        try (
                Connection connection = DataSourceConnector.getInstance().getDataSource().getConnection();
                PreparedStatement ps = connection.prepareStatement(
                        "SHOW search_path;"

                )



        ) {

            ResultSet set = ps.executeQuery();
            if (set.next()) {
                System.out.println(set.getString(1));
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        WeekendDbDao dao = new WeekendDbDao(DataSourceConnector.getInstance().getDataSource());
        for (LocalDate allWeekend : dao.getMonthlyWeekends(1)) {
            System.out.println(allWeekend);

        }


    }
}
