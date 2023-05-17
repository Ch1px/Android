package com.example.dfashjk;

public class NewsItem {
    private final String title;
    private final String summary;

    public NewsItem(String title, String summary) {
        this.title = title;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }
}
