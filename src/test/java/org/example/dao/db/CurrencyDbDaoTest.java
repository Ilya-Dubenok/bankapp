package org.example.dao.db;

import org.example.core.dto.CurrencyDTO;
import org.example.dao.db.ds.TestDataSourceConnector;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class CurrencyDbDaoTest {

    private static final TestDataSourceConnector testDataSourceConnector = TestDataSourceConnector.getInstance();
    private static DataSource dataSource;
    private static final int ID_USD = 431;
    private static final int ID_EUR = 451;
    private static final String NAME_USD = "USD";
    private static final String NAME_EUR = "EUR";

    private final List<CurrencyDTO> simpleUsdList = new ArrayList<>(
            List.of(
                    new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.25), LocalDate.of(2022, 12, 12)),
                    new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.28), LocalDate.of(2022, 12, 13)),
                    new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.21), LocalDate.of(2022, 12, 14)),
                    new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.21), LocalDate.of(2022, 12, 15)),
                    new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.21), LocalDate.of(2022, 12, 16))
            )
    );

    private final List<CurrencyDTO> fakeListOfSimpleUsdList = List.of(
            new CurrencyDTO(NAME_USD, BigDecimal.valueOf(1), LocalDate.of(2022, 12, 12)),
            new CurrencyDTO(NAME_USD, BigDecimal.valueOf(1), LocalDate.of(2022, 12, 13)),
            new CurrencyDTO(NAME_USD, BigDecimal.valueOf(1), LocalDate.of(2022, 12, 14))

    );

    @BeforeAll
    static void setUp() throws SQLException {
        testDataSourceConnector.initDbBase();
        dataSource = testDataSourceConnector.getDataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO app.currency_types (id,name)" +
                             "VALUES (?,?),(?,?) ON CONFLICT DO NOTHING;"
             )
        ) {
            ps.setObject(1, ID_USD);
            ps.setObject(2, NAME_USD);
            ps.setObject(3, ID_EUR);
            ps.setObject(4, NAME_EUR);
            ps.executeUpdate();

        }

    }

    @BeforeEach
    void clearBaseFromRates() throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM app.currency_rates;")
        ) {
            ps.execute();

        }

    }

    @AfterAll
    static void tearDown() throws SQLException {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("DROP SCHEMA app CASCADE;")

        ) {
            ps.execute();
        }
    }

    @Test
    void saveCurrenciesNotThrowsException() {

        CurrencyDbDao dao = new CurrencyDbDao(dataSource);

        Assertions.assertDoesNotThrow(
                () -> dao.saveCurrencies(NAME_USD, simpleUsdList)

        );

    }

    @Test
    void saveFiveCurrencies() {
        CurrencyDbDao dao = new CurrencyDbDao(dataSource);

        List<CurrencyDTO> crossList = List.of(
                new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.28), LocalDate.of(2022, 12, 13)),
                new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.21), LocalDate.of(2022, 12, 15)),
                new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.21), LocalDate.of(2022, 12, 16))
        );

        dao.saveCurrencies(NAME_USD, crossList);

        List<CurrencyDTO> returnedCurrencies = dao.saveCurrencies(NAME_USD, simpleUsdList); // only dates 12,14 are expected to be returned

        Set<CurrencyDTO> res = returnedCurrencies.stream().filter(
                key -> crossList.stream().anyMatch
                        (x -> twoCurrencyDTOsAreEqual(key, x))
        ).collect(Collectors.toSet());

        Assertions.assertEquals(2, returnedCurrencies.size());
        Assertions.assertEquals(0, res.size()); // no dates

    }

    @Test
    void saveFiveCurrenciesWithCrossedDates() {
        CurrencyDbDao dao = new CurrencyDbDao(dataSource);

        dao.saveCurrencies("USD", List.of(
                new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.334), LocalDate.of(2022, 12, 17)),
                new CurrencyDTO(NAME_USD, BigDecimal.valueOf(3.2), LocalDate.of(2022, 12, 18))
        ));
        List<CurrencyDTO> returnedCurrencies = dao.saveCurrencies(NAME_USD, simpleUsdList);

        Assertions.assertEquals(simpleUsdList.size(), returnedCurrencies.size());

        Assertions.assertTrue(
                returnedCurrencies.stream().allMatch(
                        key -> simpleUsdList.stream().anyMatch(x -> twoCurrencyDTOsAreEqual(key, x))
                ));
        Assertions.assertFalse(
                returnedCurrencies.stream().allMatch(
                        key -> fakeListOfSimpleUsdList.stream().anyMatch(x -> twoCurrencyDTOsAreEqual(key, x))
                ));

    }


    private boolean twoCurrencyDTOsAreEqual(CurrencyDTO dto1, CurrencyDTO dto2) {
        return dto1.getName().equals(dto2.getName()) &&
                dto1.getRate().equals(dto2.getRate()) &&
                dto1.getDate().equals(dto2.getDate());

    }
}