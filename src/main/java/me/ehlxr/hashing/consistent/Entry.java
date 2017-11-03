package me.ehlxr.hashing.consistent;

public class Entry {
    private String key;

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
