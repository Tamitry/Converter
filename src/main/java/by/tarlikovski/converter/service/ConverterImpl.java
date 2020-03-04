package by.tarlikovski.converter.service;

import by.tarlikovski.converter.dao.DaoFactoryImpl;
import by.tarlikovski.converter.dao.VocabularyDao;

import java.io.IOException;
import java.math.BigInteger;
import static java.lang.Math.pow;

public class ConverterImpl implements Converter {
    private VocabularyDao vocDao;
    private static final BigInteger THD = BigInteger.valueOf(1000);
    private static final int HNDR = 100;

    public ConverterImpl() throws IOException {
        vocDao = DaoFactoryImpl.getInstance().getVocabulary();
    }

    @Override
    public String convert(final String number) {
        int temp;
        BigInteger i = THD;
        String builder = new String();
        BigInteger integer = new BigInteger(number);
        while (integer.compareTo(BigInteger.ZERO) > 0) {
            temp = integer.mod(THD).intValue();
            integer = integer.divide(THD);
            builder = partT(integer.mod(THD).intValue(), i) + part(temp, i) + builder;
            i = i.multiply(THD);
        }
        return builder.substring(1,builder.length());
    }

    private String part(final int num, final BigInteger part) {
        StringBuilder builder = new StringBuilder();
        int mod = (num / HNDR) * HNDR;
        if (mod != 0) {
            builder.append(" ").append(vocDao.getNumber(mod));
        }
        mod = num % HNDR;
        if (!part.equals(BigInteger.ONE)) {
            if (mod < 20 && mod > 0) {
                builder.append(" ").append(vocDao.getNumber(mod));
            } else {
                builder.append(" ").append(vocDao.getNumber((mod / 10) * 10));
                builder.append(" ").append(vocDao.getNumber(mod % 10));
            }
        } else {
            if (mod < 20 && mod > 2) {
                builder.append(" ").append(vocDao.getNumber(mod));
            } else if (mod > 20) {
                builder.append(" ").append(vocDao.getNumber((mod / 10) * 10));
                builder.append(vocDao.getNumber(" " + (mod % 10) + ".1"));
            } else {
                builder.append(vocDao.getNumber(" " + (mod % 10) + ".1"));
            }
        }
        return builder.toString();
    }

    private String partT(final int num, final BigInteger part) {
        StringBuilder builder = new StringBuilder();
        if (num > 4) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".5"));
        } else if (num == 1) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".2"));
        } else if (num != 0) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".1"));
        } else {
            builder.append("");
        }
        return builder.toString();
    }
}
