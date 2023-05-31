package org.example.dao.api;

import java.time.LocalDate;
import java.util.List;

public interface IWeekendDao {
    List<LocalDate> getAllWeekends();
    List<LocalDate> getMonthlyWeekends(int month);

    /**
     * Записывает в базу указанную дату как выходной
     * @param date
     */
    void addWeekends(LocalDate date);
}
