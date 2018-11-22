package com.jlchn.javaagent.test;

import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

/**
 * @author jiangli
 * @date 23/11/2018
 */
public class SecurityInterceptor {
    static String user = "ANONYMOUS";

    public static void intercept(@Origin Method method) {
        if (!method.getAnnotation(Secured.class).user().equals(user)) {
            throw new IllegalStateException("Wrong user");
        }
    }
}
