package org.example.dao.db;

import org.example.dao.db.ds.TestDataSourceConnector;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

class WeekendDbDaoTest {
    private DataSource pool = TestDataSourceConnector.getInstance().getDataSource();


    @BeforeAll
    static void setUp() {
        TestDataSourceConnector.getInstance().initDbBase();

    }

    @AfterAll
    static void tearDown() throws SQLException {
        Connection connection = TestDataSourceConnector.getInstance().getDataSource().getConnection();
        PreparedStatement ps = connection.prepareStatement("DROP SCHEMA app CASCADE;");
        ps.execute();

    }

    @Test
    void getAllWeekends() {
        WeekendDbDao dao = new WeekendDbDao(pool);
        List<LocalDate> rs = dao.getAllWeekends();
        Assertions.assertNotEquals(0,rs.size());
    }

    @Test
    void allMethods() {
        WeekendDbDao dao = new WeekendDbDao(pool);
        Assertions.assertDoesNotThrow(
                () -> {
                    dao.getAllWeekends();
                    dao.getMonthlyWeekends(1);
                    dao.addWeekends(LocalDate.of(2024, 12, 3));
                    dao.addWeekends(LocalDate.of(2024, 12, 3));
                }
        );
    }

    @Test
    void getMonthlyWeekends() {
        WeekendDbDao dao = new WeekendDbDao(pool);
        Assertions.assertEquals(
                3,dao.getMonthlyWeekends(3).get(0).getMonth().getValue()
        );
    }

    @Test
    void addWeekends() {
        WeekendDbDao dao = new WeekendDbDao(pool);
        dao.addWeekends(LocalDate.of(2024, 10, 3));
        dao.addWeekends(LocalDate.of(2024, 10, 4));
        dao.addWeekends(LocalDate.of(2024, 10, 5));
        List<LocalDate> rs = dao.getMonthlyWeekends(10);
        LocalDate dt =null;
        for (LocalDate r : rs) {
            if (r.isEqual(LocalDate.of(2024, 10, 3)))
                dt = r;
        }
        Assertions.assertNotNull(dt);

    }


}