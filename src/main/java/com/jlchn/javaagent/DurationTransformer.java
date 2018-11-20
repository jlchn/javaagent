package com.jlchn.javaagent;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class DurationTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        byte[] byteCodes = classfileBuffer;

        if (className.equals("com/jlchn/javaagent/AgentTest")){
            System.out.println("Instrumenting...");

            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] methods = ctClass.getDeclaredMethods();

                for (CtMethod method: methods){
                    method.addLocalVariable("startTime", CtClass.longType);
                    method.insertBefore("startTime = System.nanoTime();");
                    method.insertAfter("System.out.println(\"Execution Duration "
                            + "(nano sec): \"+ (System.nanoTime() - startTime) );");
                }

                byteCodes = ctClass.toBytecode();
                ctClass.detach();
                System.out.println("Instrumentation complete.");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        }

        return byteCodes;

    }
}
