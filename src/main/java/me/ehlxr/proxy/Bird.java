package me.ehlxr.proxy;

import java.util.Random;

/**
 * @author ehlxr
 * @since 2019-06-28.
 */
public class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Bird is flying...");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // @Override
    // public void run() {
    //     System.out.println("Bird is running...");
    // }
}
