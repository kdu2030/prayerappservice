package com.kevin.prayerappservice.common;

import jakarta.validation.constraints.NotNull;

public class SortConfig {
    @NotNull
    private String sortField;

    @NotNull
    private SortDirection sortDirection;

    public SortConfig(){}

    public SortConfig(String sortField, SortDirection sortDirection) {
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }
}
