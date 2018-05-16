package org.simple.news.service;

public enum Category {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public String category() {
        return category;
    }
}
