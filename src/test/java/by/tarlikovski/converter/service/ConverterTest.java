package by.tarlikovski.converter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class ConverterTest {

    @Test
    public void testConvert_Equal() throws IOException {
        Converter converter = new ConverterImpl();
        String num = "111111111";
        String name = converter.convert(num);
        Assertions.assertEquals(name, "сто одиннадцать миллионов сто одиннадцать тысяч сто одиннадцать");
    }
}
