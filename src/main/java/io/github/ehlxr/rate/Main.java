package io.github.ehlxr.rate;

/**
 * @author ehlxr
 * @since 2019-07-18.
 */
public class Main {
    public static void main(String[] args) {
        RateBarrier rateBarrier = new RateBarrier(10, 3);

        final Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                if (rateBarrier.allow()) {
                    System.out.println("this is on 3");
                } else {
                    System.out.println("this is on 7");
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

// Output:

/*
this is on 7
this is on 7
this is on 7
this is on 7
this is on 3
this is on 7
this is on 3
this is on 7
this is on 7
this is on 3
this is on 7
this is on 7
this is on 7
this is on 7
this is on 3
this is on 7
this is on 3
this is on 7
this is on 7
this is on 3
*/
