package com.rubenskj.core.util;

import com.rubenskj.core.handler.Subscribers;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * {@link ValidationUtils} is a class to validate if the params that was passed is correct.
 * </p>
 * <p>
 * In general is used to validate params.
 *
 * @author Rubens K. Junior
 * @see Subscribers
 * @since 0.1
 */
public class ValidationUtils {

    /**
     * private constructor to not permit any instance of this class. Just use static methods.
     */
    private ValidationUtils() {
    }

    /**
     * {@link ValidationUtils#validateString(String, String)} is used to validate string if is not null
     * and empty
     *
     * @param text         is the String that will be validate
     * @param messageError is used if the text is null or empty
     * @throws IllegalArgumentException if the text is not correct.
     */
    public static void validateString(String text, String messageError) {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException(messageError);
        }
    }

    /**
     * {@link ValidationUtils#validateIsMinus(int, String)} is used to validate if a number is lower
     * than zero.
     *
     * @param number       is the number that will be validate
     * @param messageError is used if the number is null or empty
     * @throws IllegalArgumentException if the number is lower than zero.
     */
    public static void validateIsMinus(int number, String messageError) {
        if (number < 0) {
            throw new IllegalArgumentException(messageError);
        }
    }
}
