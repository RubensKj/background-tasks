package com.rubenskj.core.util;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateString(String id, String messageError) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException(messageError);
        }
    }
}
