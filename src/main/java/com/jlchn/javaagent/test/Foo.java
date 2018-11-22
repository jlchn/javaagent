package com.jlchn.javaagent.test;

/**
 * @author jiangli
 * @date 22/11/2018
 */
public class Foo {

    public String getString(){
        return "this is getString in Foo.class";
    }

    @Secured(user = "admin")
    public String getStringWithAnnotation(){
        return "this is getStringWithAnnotation in Foo.class";
    }

    public void getVoid(){
        return;
    }


    public String getException(){
        try {
            throw new Exception();
        }catch (Exception e){

        }finally {
            return "finally";
        }

    }
}
