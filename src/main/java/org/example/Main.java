package org.example;

import org.example.core.dto.CurrencyDTO;
import org.example.dao.db.CurrencyDbDao;
import org.example.dao.db.WeekendDbDao;
import org.example.dao.db.ds.DataSourceConnector;
import org.example.dao.db.factory.CurrencyDbDaoFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        CurrencyDbDao db = CurrencyDbDaoFactory.getInstance();

        List<CurrencyDTO> currencyDTOS = db.saveCurrencies("USD",
                new ArrayList<>(
                        List.of(
                                new CurrencyDTO("USD", BigDecimal.valueOf(2.34), LocalDate.of(2020,3,14)),
                                new CurrencyDTO("USD", BigDecimal.valueOf(2.35), LocalDate.of(2021, 3, 14)),
                                new CurrencyDTO("USD", BigDecimal.valueOf(2.32), LocalDate.of(2022, 3, 14)),
                                new CurrencyDTO("EUR", BigDecimal.valueOf(2.32), LocalDate.of(2022, 3, 14))
                        )
                )
        );

        for (CurrencyDTO currencyDTO : currencyDTOS) {
            System.out.println(currencyDTO.getName());
            System.out.println(currencyDTO.getDate());
            System.out.println(currencyDTO.getRate());
        }

    }
}
