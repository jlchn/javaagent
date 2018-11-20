package com.jlchn.javaagent;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String args, Instrumentation instrumentation){
        ClassLogger transformer = new ClassLogger();
        instrumentation.addTransformer(transformer);
        instrumentation.addTransformer(new DurationTransformer());
    }
}