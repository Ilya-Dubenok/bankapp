package org.example.service;

import org.example.service.api.IWeekendService;

import java.time.LocalDate;
import java.util.List;

public class WeekendService implements IWeekendService {
    @Override
    public List<LocalDate> getAllWeekends() {
        return null;
    }

    @Override
    public List<LocalDate> getMonthlyWeekends(int month) {
        return null;
    }

    @Override
    public void addWeekends(LocalDate date) {

    }
}
