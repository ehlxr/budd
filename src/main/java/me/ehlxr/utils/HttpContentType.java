package me.ehlxr.utils;

/**
 * @author ehlxr
 * @since 2020/5/19.
 */
public enum HttpContentType {
    /**
     * key
     */
    KEY("Content-Type"),
    /**
     * JSON
     */
    JSON("application/json; charset=utf-8"),
    /**
     * form data
     */
    FORMBODY("application/x-www-form-urlencoded");

    HttpContentType(String v) {
        this.v = v;
    }

    private String v;

    public String value() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
