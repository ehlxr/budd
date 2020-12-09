package me.ehlxr.powermock;

/**
 * Created by ehlxr on 2017/11/3.
 */
public class ClassDependency {
    public static boolean isExist() {
        // do something
        return false;
    }

    public final boolean isAlive() {
        // do something
        return false;
    }

    public boolean isGod(String oh){
        System.out.println(oh);
        return false;
    }
}