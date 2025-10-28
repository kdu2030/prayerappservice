package com.kevin.prayerappservice.request.dtos;

import java.util.List;

public class PrayerRequestGetQuery {
    private int targetUserId;
    private List<Integer> prayerGroupIds;
    private List<Integer> creatorUserIds;
}
