package by.tarlikovski.converter.dao;

import java.io.IOException;

public interface DaoFactory {
    VocabularyDao getVocabulary() throws IOException;
}
