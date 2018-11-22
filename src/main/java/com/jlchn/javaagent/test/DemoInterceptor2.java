package com.jlchn.javaagent.test;

import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jiangli
 * @date 22/11/2018
 */
public class DemoInterceptor2 {



    public static String getString(@Origin Method method){
        try {
            return "intercepted2 + " + method.invoke(method.getDeclaringClass().newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return "null";
    }
}
