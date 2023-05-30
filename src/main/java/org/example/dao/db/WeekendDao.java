package org.example.dao.db;

import org.example.dao.api.IWeekendDao;

import java.time.LocalDate;
import java.util.List;

public class WeekendDao implements IWeekendDao {
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
