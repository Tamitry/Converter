package by.tarlikovski.converter.dao;

public class DaoFactory {

    private DaoFactory() {}

    public static VocabularyDao getVocDao() throws DaoException {
        return new VocabularyDaoImpl();
    }
}
