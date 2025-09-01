package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.group.constants.VisibilityLevel;

public class PutPrayerGroupRequest {
    private int prayerGroupId;
    private String groupName;
    private String description;
    private String rules;
    private VisibilityLevel visibilityLevel;

    public PutPrayerGroupRequest() {}

    public PutPrayerGroupRequest(int prayerGroupId, String groupName, String description, String rules, VisibilityLevel visibilityLevel) {
        this.prayerGroupId = prayerGroupId;
        this.groupName = groupName;
        this.description = description;
        this.rules = rules;
        this.visibilityLevel = visibilityLevel;
    }

    public int getPrayerGroupId() {
        return prayerGroupId;
    }

    public void setPrayerGroupId(int prayerGroupId) {
        this.prayerGroupId = prayerGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
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

    public VisibilityLevel getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(VisibilityLevel visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }
}

