package by.tarlikovski.converter.control;

import by.tarlikovski.converter.service.Converter;
import by.tarlikovski.converter.service.ServiceException;
import by.tarlikovski.converter.service.ServiceFactory;
import by.tarlikovski.converter.service.Validator;
import by.tarlikovski.converter.view.View;
import by.tarlikovski.converter.view.ViewFactory;

public class Control {
    private View view;

    public Control() {
        view = ViewFactory.createView();
    }

    public static void main(String[] args) {
        Control control = new Control();
        Converter converter;
        Validator validator;
        try {
            converter = ServiceFactory.getConverter();
            validator = ServiceFactory.getValidator();
            String res;
            while (!(res = control.getView().dialog()).equals("q")) {
                if (validator.validate(res)) {
                    control.getView().dialog(converter.convert(res));
                } else {
                    control.getView().dialog("Число больше, чем позволяет справочник или неверный формат ввода.");
                }
            }
        } catch (ServiceException e) {
            control.getView().dialog(e.getMessage());
            control.getView().dialog();
        }
    }

    public View getView() {
        return view;
    }
}
