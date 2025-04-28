package SpringbootProject.entity.notSaving;

public class ArticleObject {
	private String title;
    private String link;
    private String pubDate;
    private String description;
    private String image;
    private String guid;

    // Constructor
    public ArticleObject(String title, String link, String pubDate, String description, String image, String guid) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
        this.image = image;
        this.guid = guid;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
