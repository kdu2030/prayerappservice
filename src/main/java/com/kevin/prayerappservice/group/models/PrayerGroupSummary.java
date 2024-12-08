package com.kevin.prayerappservice.group.models;

import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.utils.ColorUtils;

import java.util.Optional;

public record PrayerGroupSummary(int prayerGroupId, String groupName, String description, String rules, String color,
                                 String imageUrl) {


    public static PrayerGroupSummary createPrayerGroupSummary(PrayerGroup prayerGroup) {
        Optional<File> imageFile = Optional.ofNullable(prayerGroup.getImageFile());
        String imageUrl = imageFile.map(File::getFileUrl).orElse(null);

        return new PrayerGroupSummary(prayerGroup.getPrayerGroupId(), prayerGroup.getGroupName(),
                prayerGroup.getDescription(), prayerGroup.getRules(),
                ColorUtils.colorIntToHexString(prayerGroup.getColor()), imageUrl);
    }
}
