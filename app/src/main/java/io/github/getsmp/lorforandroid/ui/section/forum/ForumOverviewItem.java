package io.github.getsmp.lorforandroid.ui.section.forum;

public class ForumOverviewItem {
    private String url;
    private String name;

    public ForumOverviewItem(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
