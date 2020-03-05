package by.tarlikovski.converter.view;

import java.util.Scanner;

public class ViewImpl implements View {

    @Override
    public String dialog() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число для конвертирования или [q] для прекращения работы:");
        return scanner.next();
    }

    @Override
    public void dialog(String line) {
        System.out.println(line);
    }
}
