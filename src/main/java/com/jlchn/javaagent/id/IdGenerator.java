package com.jlchn.javaagent.id;

import java.util.UUID;

/**
 * @author jiangli
 * @date 21/11/2018
 */
public class IdGenerator {


    public static String nextTranceId(){
        return "trace-" + generate();
    }

    public static String nextSpanId(){
        return "span-"+ generate();
    }

    public static String nextParentId(){
        return "parent-" + generate();
    }


    private static String generate(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
