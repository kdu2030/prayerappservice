package com.kevin.prayerappservice.group.models;

import jakarta.validation.constraints.NotBlank;

public class NewPrayerGroup {
    @NotBlank
    private String name;
    private String description;
    private String rules;
    private String color;
    private int imageFileId;

    public NewPrayerGroup(String name, String description, String rules, String color, int imageFileId) {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getImageFileId() {
        return imageFileId;
    }

    public void setImageFileId(int imageFileId) {
        this.imageFileId = imageFileId;
    }
}
