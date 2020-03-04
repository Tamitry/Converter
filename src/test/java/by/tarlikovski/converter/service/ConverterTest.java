package by.tarlikovski.converter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;

public class ConverterTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/TestParam.csv", delimiter = ':')
    public void testConvertEqual(String num, String expected) throws IOException {
        Converter converter = new ConverterImpl();
        String name = converter.convert(num);
        Assertions.assertEquals(expected, name);
    }
}
