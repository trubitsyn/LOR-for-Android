package io.github.getsmp.lorforandroid.ui.section;

public class ItemCommon {
    private final String url;
    private final String title;
    private final String groupTitle;
    private final String tags;
    private final String date;
    private final String author;
    private final String comments;

    public ItemCommon(String url, String title, String groupTitle, String tags, String date, String author, String comments) {
        this.url = url;
        this.title = title;
        this.groupTitle = groupTitle;
        this.date = date;
        this.comments = comments;
        this.tags = tags;
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }

    public String getTags() {
        return tags;
    }

    public String getAuthor() {
        return author;
    }

    public String getGroupTitle() {
        return groupTitle;
    }
}
