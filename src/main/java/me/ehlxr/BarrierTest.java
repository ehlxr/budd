package me.ehlxr;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 按比例控制流量
 *
 * @author lixiangrong
 * @since 2019-07-18.
 */
public class BarrierTest {
    public static void main(String[] args) {
        Barrier barrier = new Barrier(10, 3);

        final Thread[] threads = new Thread[20];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                if (barrier.allow()) {
                    System.out.println("yes");
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

class Barrier {
    private AtomicInteger op = new AtomicInteger(0);
    private List<Integer> sources = Lists.newArrayList();
    private int base;
    private int rate;

    public boolean allow() {
        return sources.get(op.incrementAndGet() % base) < rate;
    }

    private Barrier() {
    }

    public Barrier(int base, int rate) {
        this.base = base;
        this.rate = rate;

        for (int i = 0; i < 10; i++) {
            sources.add(i);
        }
        Collections.shuffle(sources);
    }

}

