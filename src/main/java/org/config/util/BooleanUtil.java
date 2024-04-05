package org.config.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public final class BooleanUtil {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final Set<String> trueValues = new HashSet(Arrays.asList("true", "t", "yes", "y", "1"));
    private static final Set<String> falseValues = new HashSet(Arrays.asList("false", "f", "no", "n", "0"));

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static boolean parseBoolean(String booleanStr) {
        if (booleanStr == null) {
            return false;
        } else {
            String value = booleanStr.trim().toLowerCase();
            return trueValues.contains(value) || (!falseValues.contains(value) && isTrue(value));
        }
    }

    private static boolean isTrue(String str) {
        try {
            return Integer.parseInt(str) > 0;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

}
