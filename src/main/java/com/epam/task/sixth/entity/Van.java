package com.epam.task.sixth.entity;

public class Van implements Runnable {

    private int id;
    private boolean loaded;
    private boolean priority;

    public Van() {
    }

    public int getId() {
        return id;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isPriority() {
        return priority;
    }

    @Override
    public void run() {
        Base base = Base.getInstance();
        base.process(this);
    }

    @Override
    public String toString() {
        return "Van{" +
                "id=" + id +
                ", loaded=" + loaded +
                ", priority=" + priority +
                '}';
    }
}
