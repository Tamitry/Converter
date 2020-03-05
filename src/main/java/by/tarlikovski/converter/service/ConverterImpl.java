package by.tarlikovski.converter.service;

import by.tarlikovski.converter.dao.VocabularyDao;

import java.math.BigInteger;

public class ConverterImpl implements Converter {
    private VocabularyDao vocDao;
    private static final BigInteger THD = BigInteger.valueOf(1000);

    public ConverterImpl(VocabularyDao vocabularyDao) {
        vocDao = vocabularyDao;
    }

    @Override
    public String convert(final String number) {
        int temp;
        BigInteger i = THD;
        String builder = "";
        BigInteger integer = new BigInteger(number);
        if (integer.equals(BigInteger.ZERO)) {
            return "ноль";
        }
        String minus = integer.compareTo(BigInteger.ZERO) < 0 ? "минус " : "";
        integer = integer.abs();
        while (!integer.equals(BigInteger.ZERO)) {
            temp = integer.mod(THD).intValue();
            integer = integer.divide(THD);
            builder = getClass(integer.mod(THD).intValue(), i) + getThreeDigits(temp, i) + builder;
            i = i.multiply(THD);
        }
        return minus + builder.substring(1);
    }

    private String getThreeDigits(final int num, final BigInteger part) {   //работает с тремя числами класса
        StringBuilder builder = new StringBuilder();
        int hundreds = (num / 100) * 100;
        if (hundreds != 0) {                                            //сотни
            builder.append(" ").append(vocDao.getNumber(hundreds));
        }
        int dozens = num % 100 / 10 * 10;                               //Десятки
        int units = num % 10;                                           //Единицы
        String gender = part.equals(THD.multiply(THD)) ? ".1" : "";     //Мужской или женский род
        if ((dozens + units < 20) && (dozens + units > 0)) {            //Числа от 01 до 19
            builder.append(" ").append(vocDao.getNumber(dozens + units + gender));
        } else if (dozens + units != 0) {                               //Числа не равные 0-19
            builder.append(" ").append(vocDao.getNumber(dozens));
            if (units != 0) {                                           //Оставшиеся единицы
                builder.append(" ").append(vocDao.getNumber(units + gender));
            }
        }
        return builder.toString();
    }

    private String getClass(final int num, final BigInteger part) {           //работает с именем класса числа
        StringBuilder builder = new StringBuilder();
        int dozen = num % 100 / 10 * 10;
        int unit = num % 10;
        if (((dozen + unit) > 4 && ((dozen + unit) < 21)) || unit > 5) {      //5 - 20 тысяч
            builder.append(" ").append(vocDao.getExp(part.toString() + ".5"));
        } else if (unit > 1 && unit < 5) {                                              //все числа оканчивающиеся на 2-4, кроме 12, 13, 14, тысячи
            builder.append(" ").append(vocDao.getExp(part.toString() + ".2"));
        } else if (unit == 1) {                                                         //Все числа оканчивающиеся на 1, кроме 11, тысяча
            builder.append(" ").append(vocDao.getExp(part.toString() + ".1"));
        } else if (num != 0) {                                                          //Оставшиеся числа, имеющие нули в десятках и единицах и не ноль в сотнях, тысяч
            builder.append(" ").append(vocDao.getExp(part.toString() + ".5"));
        }
        return builder.toString();
    }
}
