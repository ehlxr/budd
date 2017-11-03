package me.ehlxr.hashing.consistent;

import java.util.SortedMap;
import java.util.TreeMap;

public class Cluster {
    private static final int SERVER_SIZE_MAX = 1024;

    private SortedMap<Integer, Server> servers = new TreeMap<>();
    private int size = 0;

    public void put(Entry e) {
        routeServer(e.hashCode()).put(e);
    }

    public Entry get(Entry e) {
        return routeServer(e.hashCode()).get(e);
    }

    public Server routeServer(int hash) {
        if (servers.isEmpty())
            return null;

        if (!servers.containsKey(hash)) {
            SortedMap<Integer, Server> tailMap = servers.tailMap(hash);
            hash = tailMap.isEmpty() ? servers.firstKey() : tailMap.firstKey();
            // hash = servers.firstKey();
        }
        return servers.get(hash);
    }

    public boolean addServer(Server s) {
        if (size >= SERVER_SIZE_MAX)
            return false;

        servers.put(s.hashCode(), s);

        size++;
        return true;
    }
}
