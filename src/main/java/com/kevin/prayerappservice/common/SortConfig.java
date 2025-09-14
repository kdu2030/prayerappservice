package com.kevin.prayerappservice.common;

import jakarta.validation.constraints.NotNull;

public class SortConfig<T> {
    @NotNull
    private T sortField;

    @NotNull
    private SortDirection sortDirection;

    public SortConfig(){}

    public SortConfig(T sortField, SortDirection sortDirection) {
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }

    public T getSortField() {
        return sortField;
    }

    public void setSortField(T sortField) {
        this.sortField = sortField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }
}
