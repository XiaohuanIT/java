package com.job;

public class Model {
    private String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Model{" +
                "property='" + property + '\'' +
                '}';
    }
}
