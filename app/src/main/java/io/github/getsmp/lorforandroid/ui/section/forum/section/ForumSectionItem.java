package io.github.getsmp.lorforandroid.ui.section.forum.section;

public class ForumSectionItem {
    private String url;
    private String title;
    private String tags;
    private String replyFrom;
    private String replyDate;
    private String commentsCount;

    public ForumSectionItem(String url, String title, String tags, String replyFrom, String replyDate, String commentsCount) {
        this.url = url;
        this.title = title;
        this.tags = tags;
        this.replyFrom = replyFrom;
        this.replyDate = replyDate;
        this.commentsCount = commentsCount;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getTags() {
        return tags;
    }

    public String getReplyFrom() {
        return replyFrom;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public String getCommentsCount() {
        return commentsCount;
    }
}
