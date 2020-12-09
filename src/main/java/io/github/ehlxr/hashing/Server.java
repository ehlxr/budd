package io.github.ehlxr.hashing;

import java.util.HashMap;
import java.util.Map;

public class Server {
    private final String name;
    private final Map<Entry, Entry> entries;

    Server(String name) {
        this.name = name;
        entries = new HashMap<>();
    }

    public void put(Entry e) {
        entries.put(e, e);
    }

    public Entry get(Entry e) {
        return entries.get(e);
    }
}