package me.ehlxr.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * Created by lixiangrong on 2017/7/21.
 */
@RunWith(PowerMockRunner.class)
public class TestClassUnderTest {
    @Test
    public void testCallArgumentInstance() {
        File file = PowerMockito.mock(File.class);
        ClassUnderTest underTest = new ClassUnderTest();
        PowerMockito.when(file.exists()).thenReturn(true);
        Assert.assertTrue(underTest.callArgumentInstance(file));
    }

    @Test
    @PrepareForTest(ClassUnderTest.class)
    public void testCallInternalInstance() throws Exception {
        File file = PowerMockito.mock(File.class);
        ClassUnderTest underTest = new ClassUnderTest();
        PowerMockito.whenNew(File.class).withArguments("bbb").thenReturn(file);
        PowerMockito.when(file.exists()).thenReturn(true);
        Assert.assertTrue(underTest.callInternalInstance("bbb"));
    }

    @Test
    @PrepareForTest(ClassDependency.class)
    public void testCallFinalMethod() {
        ClassDependency depencency = PowerMockito.mock(ClassDependency.class);
        ClassUnderTest underTest = new ClassUnderTest();
        PowerMockito.when(depencency.isAlive()).thenReturn(true);
        Assert.assertTrue(underTest.callFinalMethod(depencency));
    }

    @Test
    @PrepareForTest(ClassUnderTest.class)
    public void testCallSystemFinalMethod() {
        String str = PowerMockito.mock(String.class);
        ClassUnderTest underTest = new ClassUnderTest();
        PowerMockito.when(str.isEmpty()).thenReturn(false);
        Assert.assertFalse(underTest.callSystemFinalMethod(str));
    }

    @Test
    @PrepareForTest(ClassDependency.class)
    public void testCallStaticMethod() {
        ClassUnderTest underTest = new ClassUnderTest();
        PowerMockito.mockStatic(ClassDependency.class);
        PowerMockito.when(ClassDependency.isExist()).thenReturn(true);
        Assert.assertTrue(underTest.callStaticMethod());
    }

    @Test
    @PrepareForTest(ClassUnderTest.class)
    public void testCallSystemStaticMethod() {
        ClassUnderTest underTest = new ClassUnderTest();
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.getProperty("aaa")).thenReturn("bbb");
        Assert.assertEquals("bbb", underTest.callSystemStaticMethod("aaa"));
    }

    @Test
    @PrepareForTest(ClassUnderTest.class)
    public void testCallPrivateMethod() throws Exception {
        ClassUnderTest underTest = PowerMockito.mock(ClassUnderTest.class);
        PowerMockito.when(underTest.callPrivateMethod()).thenCallRealMethod();
        PowerMockito.when(underTest, "isExist", anyString()).thenReturn(true);
        Assert.assertTrue(underTest.callPrivateMethod());
    }

    @Test
    @PrepareForTest(ClassUnderTest.class)
    public void testCallVoidPrivateMethod() throws Exception {
        ClassUnderTest underTest = PowerMockito.mock(ClassUnderTest.class);
        PowerMockito.when(underTest.callVoidPrivateMethod()).thenCallRealMethod();
        PowerMockito.doNothing().when(underTest, "testVoid");
        Assert.assertTrue(underTest.callVoidPrivateMethod());
    }
}