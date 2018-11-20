package com.jlchn.javaagent;


import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassLogger implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        if (className.endsWith("sun/net/www/protocol/http/HttpURLConnection")) {
            try {
                final ClassPool classPool = ClassPool.getDefault();
                final CtClass clazz =
                        classPool.get("sun.net.www.protocol.http.HttpURLConnection");

                for (final CtConstructor constructor: clazz.getConstructors()) {
                    constructor.insertAfter("System.out.println(this.getURL());");
                }

                byte[] byteCode = clazz.toBytecode();
                clazz.detach();

                return byteCode;
            } catch (final NotFoundException | CannotCompileException | IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}