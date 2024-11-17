package com.kevin.prayerappservice.group.models;

public class NewPrayerGroup {
    private String name;
    private String description;
    private String rules;
    private int color;
    private int imageFileId;

    public NewPrayerGroup(String name, String description, String rules, int color, int imageFileId) {
        this.name = name;
        this.description = description;
        this.rules = rules;
        this.color = color;
        this.imageFileId = imageFileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getImageFileId() {
        return imageFileId;
    }

    public void setImageFileId(int imageFileId) {
        this.imageFileId = imageFileId;
    }
}
