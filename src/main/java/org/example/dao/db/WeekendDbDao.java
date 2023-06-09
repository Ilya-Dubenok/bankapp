package org.example.dao.db;

import org.example.dao.api.IWeekendDao;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekendDbDao implements IWeekendDao {


    private final DataSource dataSource;

    public WeekendDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<LocalDate> getAllWeekends() {
        List<LocalDate> res = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT date " +
                             " FROM app.weekend " +
                             " WHERE is_day_off;"
             );) {


            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null)
                    while (rs.next()) {
                        res.add(
                                rs.getDate(1).toLocalDate()
                        );

                    }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public List<LocalDate> getMonthlyWeekends(int month) {
        List<LocalDate> res = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT date " +
                             " FROM app.weekend " +
                             " WHERE " +
                             "DATE_PART('month',date) = ? " +
                             "AND is_day_off;"
             )) {


            ps.setInt(1, month);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null)
                    while (rs.next()) {
                        res.add(
                                rs.getDate(1).toLocalDate()
                        );

                    }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public void addWeekends(LocalDate date) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO app.weekend (date, is_day_off) " +
                             "VALUES(?, true) " +
                             "ON CONFLICT DO NOTHING;"
             )) {


            ps.setDate(1, Date.valueOf(date));

            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
