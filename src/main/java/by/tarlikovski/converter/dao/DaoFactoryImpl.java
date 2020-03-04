package by.tarlikovski.converter.dao;

import java.io.IOException;

public class DaoFactoryImpl implements DaoFactory {

    private static DaoFactory instance;

    private DaoFactoryImpl() {
    }

    @Override
    public VocabularyDao getVocabulary() throws IOException {
        return new VocabularyDaoImpl();
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactoryImpl();
        }
        return instance;
    }
}
