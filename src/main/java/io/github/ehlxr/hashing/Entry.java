package io.github.ehlxr.hashing;

public class Entry {
    private final String key;

    Entry(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}