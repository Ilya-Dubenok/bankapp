package org.example.dao.db;

import org.example.core.dto.CurrencyDTO;
import org.example.dao.api.ICurrencyDao;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDbDao implements ICurrencyDao {

    private final DataSource dataSource;

    public CurrencyDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<CurrencyDTO> saveCurrencies(String currType, List<CurrencyDTO> currencyDTOsToAd) {
        List<CurrencyDTO> res = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO app.currency_rates (id, name, date, rate) VALUES".concat(getStringForList(currencyDTOsToAd))
                            +
                            " ON CONFLICT DO NOTHING " +
                            " RETURNING id, name, date, rate;"
            );


            for (int i = 0, j = 1; i < currencyDTOsToAd.size(); i++, j += 4) {

                CurrencyDTO dto = currencyDTOsToAd.get(i);
                ps.setString(j, currType);  //1 field
                ps.setString(j + 1, dto.getName());  //2 field
                ps.setDate(j + 2, Date.valueOf(dto.getDate()));  //3 field
                ps.setBigDecimal(j + 3, dto.getRate());  //4 field


            }


            ResultSet set = ps.executeQuery();

            if (set != null) {
                fillListFromResultSet(res, set);
            }
            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies(String currType) {
        List<CurrencyDTO> res = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT name, date, rate FROM app.currency_rates;"
            );

            ResultSet set = ps.executeQuery();

            if (set != null) {
                fillListFromResultSet(res, set);
            }

            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies(String currType, LocalDate start, LocalDate stop) {
        return null;
    }

    @Override
    public List<CurrencyDTO> getAllCurrenciesOnWorkdaysOnly(String currType, LocalDate start, LocalDate stop) {
        return null;
    }

    private void fillListFromResultSet(List<CurrencyDTO> res, ResultSet set) throws SQLException {
        while (set.next()) {
            CurrencyDTO currencyDTO =
                    new CurrencyDTO(
                            set.getString("name"),
                            set.getBigDecimal("rate"),
                            set.getDate("date").toLocalDate()
                    );
            res.add(currencyDTO);
        }
    }

    private String getStringForList(List<CurrencyDTO> currencyDTOsToAd) {
        StringBuilder sb = new StringBuilder();
        boolean needComma = false;

        for (CurrencyDTO currencyDTO : currencyDTOsToAd) {
            if (needComma) {
                sb.append(",");
            } else {
                needComma = true;
            }

            sb.append(" ((SELECT id FROM app.currency_types WHERE app.currency_types.name = ?),?,?,?)");
        }

        return sb.toString();


    }
}
