package com.jlchn.javaagent.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author jiangli
 * @date 22/11/2018
 */


public class Bar {

    public String method1(){
        return "method 1 in bar";
    }

    public String field1 = "field 1";

    /**
     * create a subclass, and intercept an existing methods(from parent class)
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void test1() throws IllegalAccessException, InstantiationException {
        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println(classLoader);
        DynamicType.Unloaded unloadedType = new ByteBuddy()
                .subclass(Foo.class)
                .method(named("getString"))
                .intercept(FixedValue.value("Hello World ByteBuddy!"))
                .make();// triggers new

        Class<?> dynamicType = unloadedType.load(classLoader).getLoaded();// load the generated class into the JVM
        System.out.println(dynamicType.newInstance().toString()); // instantiate the dynamicType
        System.out.println(((Foo)dynamicType.newInstance()).getString());
    }


    /**
     * create a subclass, create a method and delegate the implementation to an existing method in another Class
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    public void test2() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        Class<?> type = new ByteBuddy()
                .subclass(Object.class)
                .name("com.jlchn.javaagent.test.SubClassOfObject")
                .defineMethod("getString", String.class, Modifier.PUBLIC)
                .intercept(MethodDelegation.to(new Foo()))
                .defineField("field1", String.class, Modifier.PUBLIC)
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        System.out.println(type.getName());

        Method m = type.getDeclaredMethod("getString", null);
        System.out.println(m.invoke(type.newInstance()));
        Field f = type.getDeclaredField("field1");
        System.out.println(f.toString());
    }


    /**
     * create new class
     */
    public void test3() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .defineMethod("intercept",String.class, Modifier.PUBLIC)
               // .method(ElementMatchers.named("intercept"))// failed because there is no intercept method in Object class
                .intercept(MethodDelegation.to(DemoInterceptor.class))
                .make()
                .load(getClass().getClassLoader(),
                        ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Method m = dynamicType.getDeclaredMethod("intercept", null);
        System.out.println(m.invoke(dynamicType.newInstance()));
    }




    public void test4() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Foo.class)
                .method(ElementMatchers.named("getString"))
                .intercept(MethodDelegation.to(DemoInterceptor2.class))
                .make()
                .load(getClass().getClassLoader(),
                        ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Method m = dynamicType.getDeclaredMethod("getString", null);
        System.out.println(m.invoke(dynamicType.newInstance()));
    }

    public void test5() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

            new ByteBuddy()
              .subclass(Service.class)
              .method(ElementMatchers.isAnnotatedWith(Secured.class))
              .intercept(MethodDelegation.to(SecurityInterceptor.class)
                                         )
              .make()
             .load(getClass().getClassLoader(),
                    ClassLoadingStrategy.Default.WRAPPER)
              .getLoaded()
             .newInstance()
              .doSensitiveAction();

    }}