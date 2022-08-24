package com.bit.userz;

public class PlatformSuggestion {
    private String platformName;
    private int platformImg;

    public PlatformSuggestion(String platformName, int platformImg) {
        this.platformName = platformName;
        this.platformImg = platformImg;
    }

    public String getPlatformName() {
        return platformName;
    }

    public int getPlatformImg() {
        return platformImg;
    }
}
