package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.utils.ColorUtils;

import java.util.Optional;

public class PrayerGroupSummary {
    private final int prayerGroupId;
    private final String groupName;
    private final String description;
    private final String rules;
    private final String color;
    private final String imageUrl;

    public PrayerGroupSummary(int prayerGroupId, String groupName, String description, String rules, String color, String imageUrl) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.color = color;
        this.imageUrl = imageUrl;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    public String getRules() {
        return rules;
    }

    public String getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public static PrayerGroupSummary createPrayerGroupSummary(PrayerGroup prayerGroup) {
        Optional<File> imageFile = Optional.ofNullable(prayerGroup.getImageFile());
        String imageUrl = imageFile.map(File::getFileUrl).orElse(null);
        return new PrayerGroupSummary(prayerGroup.getPrayerGroupId(), prayerGroup.getGroupName(),
                prayerGroup.getDescription(), prayerGroup.getRules(),
                ColorUtils.colorIntToHexString(prayerGroup.getColor()), imageUrl);
    }
}
