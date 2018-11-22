package com.jlchn.javaagent.test;

/**
 * @author jiangli
 * @date 23/11/2018
 */
class Service {
    @Secured(user = "admin")
    public void doSensitiveAction() {
        // run sensitive code...
    }
}
