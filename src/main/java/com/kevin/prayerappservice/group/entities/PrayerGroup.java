package com.kevin.prayerappservice.group.entities;

import com.kevin.prayerappservice.file.entities.File;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class PrayerGroup {
    @Id
    @GeneratedValue
    private Integer prayerGroupId;

    @NotBlank
    @Column(unique = true)
    private String groupName;

    private String description;

    private String rules;

    private Integer color;

    @ManyToOne
    @JoinColumn(name = "image_file_id")
    private File imageFile;

    @OneToMany(mappedBy = "prayerGroup")
    private List<PrayerGroupUser> prayerGroupUsers;

    public PrayerGroup(String groupName, String description, String rules, Integer color, File imageFile) {
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.color = color;
        this.imageFile = imageFile;
    }

    public Integer getPrayerGroupId() {
        return prayerGroupId;
    }

    public @NotBlank String getGroupName() {
        return groupName;
    }

    public void setGroupName(@NotBlank String groupName) {
        this.groupName = groupName;
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public List<PrayerGroupUser> getPrayerGroupUsers() {
        return prayerGroupUsers;
    }

    public void setPrayerGroupUsers(List<PrayerGroupUser> prayerGroupUsers) {
        this.prayerGroupUsers = prayerGroupUsers;
    }
}
