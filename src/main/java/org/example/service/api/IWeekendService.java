package org.example.service.api;

import java.time.LocalDate;
import java.util.List;

public interface IWeekendService {
    List<LocalDate> getAllWeekends();
    List<LocalDate> getMonthlyWeekends(int month);

    void addWeekends(LocalDate date);
}
