package by.tarlikovski.converter.service;

import by.tarlikovski.converter.dao.VocabularyDao;

import java.math.BigInteger;

public class ValidatorImpl implements Validator {

    private static final BigInteger THOUSAND = BigInteger.valueOf(1000);

    private VocabularyDao vocabularyDao;

    public ValidatorImpl(VocabularyDao vocDao) {
        vocabularyDao = vocDao;
    }

    @Override
    public boolean validate(final String q) {
        if (!q.matches("\\d+")) {
            return false;
        }
        BigInteger val = new BigInteger(q);
        int i = 0;
        int j = 3;
        val = val.abs();
        while (!val.equals(BigInteger.ZERO)) {
            val = val.divide(BigInteger.TEN);
            i++;
        }
        BigInteger available = THOUSAND;
        while (vocabularyDao.getExp(available.toString() + ".1") != null) {
            available = available.multiply(THOUSAND);
            j += 3;
        }
        return i <= j;
    }
}
