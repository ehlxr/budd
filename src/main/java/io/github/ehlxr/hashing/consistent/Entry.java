package io.github.ehlxr.hashing.consistent;

public class Entry {
    private final String key;

    Entry(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    public int hashCode() {
        return key.hashCode();
    }
}
