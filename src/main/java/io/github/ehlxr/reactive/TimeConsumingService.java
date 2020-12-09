package io.github.ehlxr.reactive;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by ehlxr on 2018/1/16.
 */
public class TimeConsumingService implements Callable<String> {

    private final String service_name;
    private final int wait_ms;
    private final Object[] depandencies;

    TimeConsumingService(String name, Integer waiting, Object[] depandencies) {
        this.service_name = name;
        this.wait_ms = waiting;
        this.depandencies = depandencies;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(wait_ms);
        return String.format("service %s exec time is: %d, depandencies: %s", service_name, wait_ms, Arrays.toString(depandencies));
    }
}