package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.utils.ColorUtils;

public class PrayerGroupSummary {
    private final int prayerGroupId;
    private final String groupName;
    private final String description;
    private final String rules;
    private final String color;

    public PrayerGroupSummary(int prayerGroupId, String groupName, String description, String rules, String color) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.color = color;
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

    public static PrayerGroupSummary createPrayerGroupSummary(PrayerGroup prayerGroup) {
        return new PrayerGroupSummary(prayerGroup.getPrayerGroupId(), prayerGroup.getGroupName(),
                prayerGroup.getDescription(), prayerGroup.getRules(),
                ColorUtils.colorIntToHexString(prayerGroup.getColor()));
    }
}
