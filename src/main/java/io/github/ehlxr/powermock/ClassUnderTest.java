/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.powermock;

/**
 * Created by ehlxr on 2017/11/3.
 */

import java.io.File;

/**
 * Created by ehlxr on 2017/7/21.
 */
public class ClassUnderTest {
    private final String f1 = "test";

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