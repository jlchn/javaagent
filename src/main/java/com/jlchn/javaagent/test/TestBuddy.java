package com.jlchn.javaagent.test;


import java.lang.reflect.InvocationTargetException;



/**
 * @author jiangli
 * @date 22/11/2018
 */
public class TestBuddy {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException {

        new Bar().test1();
        new Bar().test2();
        new Bar().test3();
        new Bar().test4();
        new Bar().test5();
    }

}
