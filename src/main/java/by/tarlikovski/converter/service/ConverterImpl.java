package by.tarlikovski.converter.service;

import by.tarlikovski.converter.dao.DaoFactoryImpl;
import by.tarlikovski.converter.dao.VocabularyDao;

import java.io.IOException;
import java.math.BigInteger;

public class ConverterImpl implements Converter {
    private VocabularyDao vocDao;
    private static final BigInteger THD = BigInteger.valueOf(1000);

    public ConverterImpl() throws IOException {
        vocDao = DaoFactoryImpl.getInstance().getVocabulary();
    }

    @Override
    public String convert(final String number) {
        int temp;
        BigInteger i = THD;
        String builder = "";
        BigInteger integer = new BigInteger(number);
        if (integer.equals(BigInteger.ZERO)) {
            return "нуль";
        }
        String minus = integer.compareTo(BigInteger.ZERO) < 0 ? "минус " : "";
        integer = integer.abs();
        while (!integer.equals(BigInteger.ZERO)) {
            temp = integer.mod(THD).intValue();
            integer = integer.divide(THD);
            builder = partT(integer.mod(THD).intValue(), i) + part(temp, i) + builder;
            i = i.multiply(THD);
        }
        return minus + builder.substring(1, builder.length());
    }

    private String part(final int num, final BigInteger part) {
        StringBuilder builder = new StringBuilder();
        int mod = (num / 100) * 100;
        if (mod != 0) {
            builder.append(" ").append(vocDao.getNumber(mod));
        }
        mod = num % 100;
        if (!part.equals(THD.multiply(THD))) {
            if (mod < 20 && mod > 0) {
                builder.append(" ").append(vocDao.getNumber(mod));
            } else if (mod != 0) {
                builder.append(" ").append(vocDao.getNumber((mod / 10) * 10));
                if (mod % 10 != 0) {
                    builder.append(" ").append(vocDao.getNumber(mod % 10));
                }
            }
        } else {
            if (mod < 20 && mod > 2) {
                builder.append(" ").append(vocDao.getNumber(mod));
            } else if (mod > 20) {
                builder.append(" ").append(vocDao.getNumber((mod / 10) * 10));
                if (mod % 10 != 0) {
                    builder.append(" ").append(vocDao.getNumber((mod % 10) + ".1"));
                }
            } else if (mod != 0) {
                builder.append(" ").append(vocDao.getNumber((mod % 10) + ".1"));
            }
        }
        return builder.toString();
    }

    private String partT(final int num, final BigInteger part) {
        StringBuilder builder = new StringBuilder();
        int n = num % 100;
        /*if (n > 4 && n < 21) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".5"));
        } else if (n > 1) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".2"));
        } else if (n != 0) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".1"));
        } else {
            builder.append("");
        }*/
        if (n > 4 && n < 21 || n % 10 > 5) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".5"));
        } else if (n % 10 > 1 && n % 10 < 5) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".2"));
        } else if (n == 1) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".1"));
        } else if (num != 0) {
            builder.append(" ").append(vocDao.getExp(part.toString() + ".5"));
        }
        return builder.toString();
    }
}
