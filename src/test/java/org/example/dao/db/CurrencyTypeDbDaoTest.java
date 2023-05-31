package org.example.dao.db;

import org.example.core.dto.CurrencyTypeDTO;
import org.example.dao.db.ds.TestDataSourceConnector;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTypeDbDaoTest {

    private DataSource pool = TestDataSourceConnector.getInstance().getDataSource();


    @BeforeAll
    static void setUp() {
        TestDataSourceConnector.getInstance().initDbBase();

    }

    @BeforeEach
    void clearBase() throws SQLException {
        Connection connection = TestDataSourceConnector.getInstance().getDataSource().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM app.currency_types;");
        ps.execute();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        Connection connection = TestDataSourceConnector.getInstance().getDataSource().getConnection();
        PreparedStatement ps = connection.prepareStatement("DROP SCHEMA app CASCADE;");
        ps.execute();
    }


    @Test
    void saveAndGetCurrencyType() {
        CurrencyTypeDbDao dao = new CurrencyTypeDbDao(pool);
        CurrencyTypeDTO dto = new CurrencyTypeDTO(323, "USD");
        dao.saveCurrencyType(dto);
        CurrencyTypeDTO dbdto = dao.getCurrencyType("USD");

        Assertions.assertEquals(dto.getId(), dbdto.getId());
        Assertions.assertEquals(dto.getName(), dbdto.getName());
    }

    @Test
    void saveCurrencyTypes() {
        List<CurrencyTypeDTO> list = List.of(
                new CurrencyTypeDTO(1, "USD"),
                new CurrencyTypeDTO(2, "EUR"),
                new CurrencyTypeDTO(3, "RUB")

        );

        CurrencyTypeDbDao dao = new CurrencyTypeDbDao(pool);
        dao.saveCurrencyTypes(list);

        Assertions.assertEquals(
                1, dao.getCurrencyType("USD").getId()
        );
        Assertions.assertEquals(
                2, dao.getCurrencyType("EUR").getId()
        );
        Assertions.assertEquals(
                3, dao.getCurrencyType("RUB").getId()
        );

    }


    @Test
    void saveCurrencyTypesWithDuplicate() throws SQLException {
        List<CurrencyTypeDTO> list = List.of(
                new CurrencyTypeDTO(1, "USD"),
                new CurrencyTypeDTO(2, "EUR"),
                new CurrencyTypeDTO(3, "RUB"),
                new CurrencyTypeDTO(3, "RUB"),
                new CurrencyTypeDTO(3, "EUR"),
                new CurrencyTypeDTO(1, "RUB")

        );

        CurrencyTypeDbDao dao = new CurrencyTypeDbDao(pool);
        dao.saveCurrencyTypes(list);

        Connection connection = TestDataSourceConnector.getInstance().getDataSource().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM app.currency_types");
        ResultSet set = ps.executeQuery();
        int total = 0;
        while (set.next()) {
            total++;
        }

        Assertions.assertEquals(3, total);

    }

    @Test
    void currencyExists() {
        CurrencyTypeDbDao dao = new CurrencyTypeDbDao(pool);
        CurrencyTypeDTO dto = new CurrencyTypeDTO(323, "USD");
        dao.saveCurrencyType(dto);
        Assertions.assertTrue(dao.currencyExists(dto));
    }

    @Test
    void getCurrencyTypeByNameAndId() {
        CurrencyTypeDbDao dao = new CurrencyTypeDbDao(pool);
        CurrencyTypeDTO dto = new CurrencyTypeDTO(323, "USD");
        dao.saveCurrencyType(dto);
        CurrencyTypeDTO dbdto = dao.getCurrencyType(323, "USD");

        Assertions.assertEquals(dto.getId(), dbdto.getId());
        Assertions.assertEquals(dto.getName(), dbdto.getName());


    }

}