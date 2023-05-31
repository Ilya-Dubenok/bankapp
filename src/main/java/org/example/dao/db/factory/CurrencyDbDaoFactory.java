package org.example.dao.db.factory;

import org.example.dao.db.CurrencyDbDao;
import org.example.dao.db.CurrencyTypeDbDao;
import org.example.dao.db.ds.DataSourceConnector;

public class CurrencyDbDaoFactory {


    private static final class Holder{
        private final static CurrencyDbDao instance = new CurrencyDbDao(
                DataSourceConnector.getInstance().getDataSource()
        );
    }


    private CurrencyDbDaoFactory() {

    }


    public static CurrencyDbDao getInstance() {
        return Holder.instance;
    }
}
