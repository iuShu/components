package org.iushu.validation;

import org.iushu.validation.beans.Address;
import org.iushu.validation.validators.AddressValidator;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.Arrays;

/**
 * @author iuShu
 * @since 1/25/21
 */
public class Application {

    /**
     * @see org.springframework.validation.DataBinder
     * @see org.springframework.validation.Errors
     * @see org.springframework.validation.BeanPropertyBindingResult
     * @see org.springframework.context.support.DefaultMessageSourceResolvable
     */
    public static void dataBinder() {
        Address address = new Address();
        Validator addressValidator = new AddressValidator();
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("country", "China");
        values.add("province", "Gansu");
//        values.add("city", "Tianshui");

        DataBinder dataBinder = new DataBinder(address);
        dataBinder.setValidator(addressValidator);
        dataBinder.bind(values);    // populate values to fields
        dataBinder.validate();      // validate fields
        BindingResult result = dataBinder.getBindingResult();

        System.out.println(result);
        System.out.println("target: " + result.getTarget());
        System.out.println("model: " + result.getModel());
        System.out.println("field: " + result.getRawFieldValue("province"));
        System.out.println("editor: " + result.findEditor("province", String.class));
        System.out.println("registry: " + result.getPropertyEditorRegistry());
        System.out.println("error: " + result.getAllErrors());
        System.out.println("message: " + Arrays.toString(result.resolveMessageCodes("city.empty")));
    }

    public static void methodInterception() {
        // to be continued
    }

    public static void main(String[] args) {
//        dataBinder();
        methodInterception();
    }

}
