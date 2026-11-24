package de.gessnerfl.fakesmtp.model;

public class EmailContent extends EmailPart {
    private ContentType contentType;

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
