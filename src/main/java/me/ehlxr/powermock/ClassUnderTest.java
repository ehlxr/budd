package me.ehlxr.powermock;

/**
 * Created by lixiangrong on 2017/11/3.
 */

import java.io.File;

/**
 * Created by lixiangrong on 2017/7/21.
 */
public class ClassUnderTest {
    private String f1 = "test";

    public boolean callArgumentInstance(File file) {
        return file.exists();
    }

    public boolean callInternalInstance(String path) {
        File file = new File(path);
        return file.exists();
    }

    public boolean callFinalMethod(ClassDependency refer) {
        return refer.isAlive();
    }

    public boolean callSystemFinalMethod(String str) {
        return str.isEmpty();
    }

    public boolean callStaticMethod() {
        return ClassDependency.isExist();
    }

    public String callSystemStaticMethod(String str) {
        return System.getProperty(str);
    }

    public boolean callPrivateMethod() {
        return isExist();
    }

    public boolean callVoidPrivateMethod() {
        testVoid();
        return true;
    }

    private boolean isExist() {
        // do something
        return false;
    }

    private void testVoid() {
        System.out.println("do nothing");
    }

    public boolean callDependency() {
        ClassDependency classDependency = new ClassDependency();
        return classDependency.isGod("hh");
    }
}