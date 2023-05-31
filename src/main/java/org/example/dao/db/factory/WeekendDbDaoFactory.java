package org.example.dao.db.factory;

import org.example.dao.db.WeekendDbDao;
import org.example.dao.db.ds.DataSourceConnector;

public class WeekendDbDaoFactory {

    private static class Holder{
        private final static WeekendDbDao instance = new WeekendDbDao(DataSourceConnector.getInstance().getDataSource());

    }

    private WeekendDbDaoFactory() {

    }

    public static WeekendDbDao getWeekendDaoInstance() {
        return Holder.instance;

    }

}
