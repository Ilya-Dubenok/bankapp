package org.example.dao.db.factory;

import org.example.dao.db.CurrencyTypeDbDao;
import org.example.dao.db.ds.DataSourceConnector;

public class CurrencyTypeDbDaoFactory {

        private final static class Holder{
            private static final CurrencyTypeDbDao instance = new CurrencyTypeDbDao(
                    DataSourceConnector.getInstance().getDataSource()
            );

        }

        private CurrencyTypeDbDaoFactory(){};

        public static CurrencyTypeDbDao getInstance() {
            return Holder.instance;
        }

}
