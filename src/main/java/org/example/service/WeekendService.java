package org.example.service;

import org.example.dao.db.factory.WeekendDbDaoFactory;
import org.example.service.api.IWeekendService;

import java.time.LocalDate;
import java.util.List;

public class WeekendService implements IWeekendService {
    @Override
    public List<LocalDate> getAllWeekends() {
        return WeekendDbDaoFactory.getWeekendDaoInstance().getAllWeekends();
    }

    @Override
    public List<LocalDate> getMonthlyWeekends(int month) {
        return WeekendDbDaoFactory.getWeekendDaoInstance().getMonthlyWeekends(month);
    }

    @Override
    public void addWeekends(LocalDate date) {
        WeekendDbDaoFactory.getWeekendDaoInstance().addWeekends(date);
    }
}
