package org.example.dao.db;

import org.example.core.dto.CurrencyDTO;
import org.example.dao.api.ICurrencyDao;

import javax.sql.DataSource;
import java.math.BigDecimal;
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
        if (currencyDTOsToAd.size() == 0) {
            return res;
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     ("WITH temp_id_table(id) AS (SELECT id FROM app.currency_types WHERE name = ? LIMIT 1) " +
                             " INSERT INTO app.currency_rates (id, name, date, rate) VALUES " +
                             " ((SELECT id FROM temp_id_table),unnest(?),unnest(?),unnest(?)) " +
                             " ON CONFLICT DO NOTHING" +
                             " RETURNING id,name,date,rate; "
                     ))
        ) {
            int sizeOfCurrencyList = currencyDTOsToAd.size();

            String[] names = new String[sizeOfCurrencyList];
            LocalDate[] dates = new LocalDate[sizeOfCurrencyList];
            BigDecimal[] rates = new BigDecimal[sizeOfCurrencyList];

            ps.setString(1, currType);  //1 field

            for (int i = 0; i < sizeOfCurrencyList; i++) {

                CurrencyDTO dto = currencyDTOsToAd.get(i);
                LocalDate dateOfCurrentDto = dto.getDate();
                names[i] = currType;
                dates[i] = dateOfCurrentDto;
                rates[i] = dto.getRate();

            }
            ps.setArray(2, connection.createArrayOf("text", names));
            ps.setArray(3, connection.createArrayOf("date", dates));
            ps.setArray(4, connection.createArrayOf("numeric", rates));

            ResultSet set = ps.executeQuery();
            fillListFromResultSet(res, set);

            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies(String currType) {
        List<CurrencyDTO> res = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT name, date, rate FROM app.currency_rates WHERE name = ?;"
             )) {

            ps.setString(1, currType);

            try (ResultSet set = ps.executeQuery()) {

                if (set != null) {
                    fillListFromResultSet(res, set);
                }
            }

            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies(String currType, LocalDate start, LocalDate stop) {
        List<CurrencyDTO> res = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT name, date, rate FROM app.currency_rates " +
                             " WHERE name=? AND date >= ? AND date <= ?"
             )) {


            ps.setString(1, currType);
            ps.setDate(2, Date.valueOf(start));
            ps.setDate(3, Date.valueOf(stop));

            try (ResultSet set = ps.executeQuery()) {

                if (set != null) {
                    fillListFromResultSet(res, set);
                }
            }

            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CurrencyDTO> getAllCurrenciesOnWorkdaysOnly(String currType, LocalDate start, LocalDate stop) {
        List<CurrencyDTO> res = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT name, currency_rates.date, rate FROM app.currency_rates " +
                             "INNER JOIN app.weekend ON app.currency_rates.name=? AND NOT app.weekend.is_day_off AND currency_rates.date = weekend.date  " +
                             " WHERE app.currency_rates.date>=? AND app.currency_rates.date<=?"
             )) {


            ps.setString(1, currType);
            ps.setDate(2, Date.valueOf(start));
            ps.setDate(3, Date.valueOf(stop));

            try (ResultSet set = ps.executeQuery()) {

                if (set != null) {
                    fillListFromResultSet(res, set);
                }
            }

            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

}
