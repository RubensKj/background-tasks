package com.rubenskj.core.util;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.rubenskj.core.util.ValidationUtils.validateString;
import static org.junit.Assert.*;

public class ValidationUtilsTest {

    @Test
    public void constructorTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<ValidationUtils> clazz = ValidationUtils.class;

        Constructor<ValidationUtils> constructor = clazz.getDeclaredConstructor();

        assertThrows(IllegalAccessException.class, () -> {
            ValidationUtils validationUtils = constructor.newInstance();
        });
        assertFalse(constructor.isAccessible());
    }

    @Test
    public void validateStringIsEmpty() {
        String text = "";
        String messageError = "Error message";


        Exception exception = assertThrows(Exception.class, () -> {
            validateString(text, messageError);
        });

        assertNotEquals(null, exception);
        assertEquals(messageError, exception.getMessage());
    }

    @Test
    public void validateStringIsNull() {
        String text = null;
        String messageError = "Error message";


        Exception exception = assertThrows(Exception.class, () -> {
            validateString(text, messageError);
        });

        assertNotEquals(null, exception);
        assertEquals(messageError, exception.getMessage());
    }
}