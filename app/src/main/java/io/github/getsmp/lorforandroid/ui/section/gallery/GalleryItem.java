package io.github.getsmp.lorforandroid.ui.section.gallery;

public class GalleryItem {
    private final String image;
    private final String url;
    private final String title;
    private final String groupTitle;
    private final String postDate;
    private final String commentsCount;
    private final String tags;
    private final String author;

    public GalleryItem(String url, String title, String groupTitle, String postDate, String commentsCount, String tags, String author, String image) {
        this.url = url;
        this.title = title;
        this.groupTitle = groupTitle;
        this.postDate = postDate;
        this.commentsCount = commentsCount;
        this.tags = tags;
        this.author = author;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getCommentsCount() {
        return commentsCount;
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

    public String getImage() {
        return image;
    }
}
