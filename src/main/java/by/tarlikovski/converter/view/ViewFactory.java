package by.tarlikovski.converter.view;

public class ViewFactory {

    private ViewFactory() {}

    public static View createView() {
        return new ViewImpl();
    }
}
