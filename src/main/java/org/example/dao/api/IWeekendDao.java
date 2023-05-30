package org.example.dao.api;

import java.time.LocalDate;
import java.util.List;

public interface IWeekendDao {
    List<LocalDate> getAllWeekends();
    List<LocalDate> getMonthlyWeekends(int month);

    void addWeekends(LocalDate date);
}
