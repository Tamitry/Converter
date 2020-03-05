package by.tarlikovski.converter.service;

import by.tarlikovski.converter.dao.DaoException;
import by.tarlikovski.converter.dao.DaoFactory;
import by.tarlikovski.converter.dao.VocabularyDao;

public class ServiceFactory {

    private ServiceFactory() {}

    public static Converter getConverter() throws ServiceException {
        try {
            VocabularyDao vocabularyDao = DaoFactory.getVocDao();
            return new ConverterImpl(vocabularyDao);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public static Validator getValidator() throws ServiceException {
        try {
            VocabularyDao vocabularyDao = DaoFactory.getVocDao();
            return new ValidatorImpl(vocabularyDao);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
