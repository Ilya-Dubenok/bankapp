package org.example.dao.db;

import org.example.core.dto.CurrencyTypeDTO;
import org.example.dao.api.ICurrencyTypeDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyTypeDbDao implements ICurrencyTypeDao {

    private final DataSource dataSource;

    public CurrencyTypeDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public CurrencyTypeDTO saveCurrencyType(CurrencyTypeDTO curType) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO app.currency_types (id, name) " +
                             "VALUES(?, ?) " +
                             "ON CONFLICT DO NOTHING;"
             )) {


            ps.setLong(1, curType.getId());
            ps.setString(2, curType.getName());

            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return curType;
    }

    @Override
    public List<CurrencyTypeDTO> saveCurrencyTypes(List<CurrencyTypeDTO> curTypesList) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO app.currency_types (id,name)" +
                             "VALUES(?,?) " +
                             "ON CONFLICT DO NOTHING;"

             )) {


            for (CurrencyTypeDTO dto : curTypesList) {
                ps.setLong(1, dto.getId());
                ps.setString(2, dto.getName());
                ps.execute();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return curTypesList;
    }

    @Override
    public boolean currencyExists(CurrencyTypeDTO curType) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT id,name FROM app.currency_types " +
                             " WHERE " +
                             " (id,name) = (?,?) ;"

             )) {


            ps.setString(2, curType.getName());
            ps.setLong(1, curType.getId());

            ResultSet set = ps.executeQuery();

            return set != null && set.next();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CurrencyTypeDTO getCurrencyType(String name) {
        CurrencyTypeDTO res = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT id,name " +
                             " FROM app.currency_types " +
                             " WHERE " +
                             " name = ? ;"
             )) {

            ps.setString(1, name);

            ResultSet set = ps.executeQuery();
            if (null != set && set.next()) {
                res = new CurrencyTypeDTO(
                        set.getLong("id"),
                        set.getString("name")
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public CurrencyTypeDTO getCurrencyType(long id) {
        CurrencyTypeDTO res = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT id,name " +
                             " FROM app.currency_types " +
                             " WHERE " +
                             " id = ? ;"
             )) {

            ps.setLong(1, id);

            ResultSet set = ps.executeQuery();
            if (null != set && set.next()) {
                res = new CurrencyTypeDTO(
                        set.getLong("id"),
                        set.getString("name")
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public List<CurrencyTypeDTO> get() {
        List<CurrencyTypeDTO> currencyTypeDTOS = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT id, name FROM app.currency_types")) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    currencyTypeDTOS.add(new CurrencyTypeDTO(id, name));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return currencyTypeDTOS;
    }

    @Override
    public CurrencyTypeDTO getCurrencyType(long id, String name) {
        CurrencyTypeDTO res = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT id,name " +
                             " FROM app.currency_types " +
                             " WHERE " +
                             " (id,name) = (?,?) ;"
             )) {

            ps.setLong(1, id);
            ps.setString(2, name);

            ResultSet set = ps.executeQuery();
            if (null != set && set.next()) {
                res = new CurrencyTypeDTO(
                        set.getLong("id"),
                        set.getString("name")
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
