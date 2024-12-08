package com.kevin.prayerappservice.lang;

public enum CultureCode {
    EN_US("english"),
    ZH_CN("chinese");

    private final String postgresLang;

    CultureCode(String postgresLang){
        this.postgresLang = postgresLang;
    }

    public String getPostgresLang() {
        return postgresLang;
    }
}
