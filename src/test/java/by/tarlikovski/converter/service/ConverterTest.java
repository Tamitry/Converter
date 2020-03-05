package by.tarlikovski.converter.service;

import by.tarlikovski.converter.dao.DaoException;
import by.tarlikovski.converter.dao.DaoFactory;
import com.ibm.icu.text.RuleBasedNumberFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class ConverterTest {

    /**
     * Метод необходимый для генерации рандомного множества величиной в 10000 элементов.
     * В качестве эталонных значений используется результаты полученные из внешней библиотеки icu4j.
     */
    @BeforeAll
    public static void generate() throws Exception {
        FileOutputStream output = new FileOutputStream("src/test/resources/numbers.csv");
        RuleBasedNumberFormat f = new RuleBasedNumberFormat(Locale.forLanguageTag("ru"), RuleBasedNumberFormat.SPELLOUT);
        String str;
        Set<Integer> randNumbers = new HashSet<>();
        Random random = new Random();
        while (randNumbers.size() < 10001) {
            randNumbers.add(random.nextInt(1000000000));
        }
        for (Integer i : randNumbers) {
            str = "" + i + ":" + f.format(i) + "\n";
            output.write(str.getBytes());
        }
        output.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/numbers.csv", delimiter = ':')
    public void testConvertEqual(String num, String expected) throws DaoException {
        Converter converter = new ConverterImpl(DaoFactory.getVocDao());
        String actual = converter.convert(num);
        Assertions.assertEquals(expected, actual);
    }
}
