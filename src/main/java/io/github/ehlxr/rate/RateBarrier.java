package io.github.ehlxr.rate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 按比例控制流量
 *
 * @author ehlxr
 * @since 2019-07-19.
 */
public class RateBarrier {
    private final AtomicInteger op = new AtomicInteger(0);
    private List<Integer> source;
    private int base;
    private int rate;

    public boolean allow() {
        return source.get(op.incrementAndGet() % base) < rate;
    }

    private RateBarrier() {
    }

    public RateBarrier(int base, int rate) {
        this.base = base;
        this.rate = rate;

        source = new ArrayList<>(base);
        for (int i = 0; i < base; i++) {
            source.add(i);
        }

        // 打乱集合顺序
        Collections.shuffle(source);
    }

}
