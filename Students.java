package org.example;

import java.util.Random;

public class Students {
    private int id;
    private String title;
    private int duration;

    private static final String[] titles = new String[] {
            "Bob", "Nic", "Leo"};
    private static final Random random = new Random();

    public Students(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public Students(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }
    public Students() {
    }
    public static Students create() {
        return new Students(titles[random.nextInt(titles.length)], random.nextInt(20, 26));
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void updateDuration() {
        duration = random.nextInt(20, 26);
    }

    public void updateTitle(){
        title = titles[random.nextInt(titles.length)];
    }
    @Override
    public String toString() {
        return "Students{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
