package io.github.getsmp.lorforandroid.ui.section.news;


public class MiniNewsItem {
    private String title;
    private String url;
    private String commentsCount;

    public MiniNewsItem(String url, String title, String commentsCount) {
        this.url = url;
        this.title = title;
        this.commentsCount = commentsCount;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getCommentsCount() {
        return commentsCount;
    }
}
