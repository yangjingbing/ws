package com.cp.util;

public class ExceptionUtils {
    public static <T> T searchCause(Throwable t, Class<T> cause) {
        if (cause.isAssignableFrom(t.getClass())) {
            return (T)t;
        }

        if (t.getCause() == null) {
            return null;
        }

        return searchCause(t.getCause(), cause);
    }
}
