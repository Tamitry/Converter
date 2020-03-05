package by.tarlikovski.converter.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VocabularyDaoImpl implements VocabularyDao {
    private static final String VOCABULAR_1 = "src/main/resources/voc1.properties";
    private static final String VOCABULAR_2 = "src/main/resources/voc2.properties";
    private Properties voc1;
    private Properties voc2;

    public VocabularyDaoImpl() throws DaoException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(VOCABULAR_1);
            voc1 = new Properties();
            voc1.load(inputStream);
            inputStream = new FileInputStream(VOCABULAR_2);
            voc2 = new Properties();
            voc2.load(inputStream);
        } catch (IOException e) {
            throw new DaoException("Vocabulary files not available.");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public String getNumber(final int num) {
        return voc1.getProperty("" + num);
    }

    @Override
    public String getNumber(final String num) {
        return voc1.getProperty(num);
    }

    @Override
    public String getExp(final String exp) {
        return voc2.getProperty(exp);
    }
}
