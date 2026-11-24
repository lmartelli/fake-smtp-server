package de.gessnerfl.fakesmtp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.ZonedDateTime;
import java.util.*;

import static java.util.Comparator.comparing;

public class Email implements IdHolder<Long> {
    private Long id;

    private String fromAddress;

    private String toAddress;

    private String subject;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone="UTC")
    private ZonedDateTime receivedOn;

    private String rawData;

    private String messageId;

    private List<EmailContent> contents = new ArrayList<>();

    private List<EmailAttachment> attachments = new ArrayList<>();

    private List<InlineImage> inlineImages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ZonedDateTime getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(ZonedDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getRawData() {
        return rawData;
    }

    public void addContent(EmailContent content) {
        content.setEmail(this);
        contents.add(content);
    }

    public List<EmailContent> getContents() {
        return contents.stream().sorted(comparing(EmailContent::getContentType)).toList();
    }

    @JsonIgnore
    public Optional<EmailContent> getPlainContent() {
        return getContentOfType(ContentType.PLAIN);
    }

    @JsonIgnore
    public Optional<EmailContent> getHtmlContent() {
        return getContentOfType(ContentType.HTML);
    }

    private Optional<EmailContent> getContentOfType(ContentType contentType) {
        return contents.stream().filter(c -> contentType.equals(c.getContentType())).findFirst();
    }

    public void addAttachment(EmailAttachment attachment) {
        attachment.setEmail(this);
        attachments.add(attachment);
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }


    public void addInlineImage(InlineImage inlineImage) {
        inlineImage.setEmail(this);
        this.inlineImages.add(inlineImage);
    }

    @JsonIgnore
    public Optional<InlineImage> getInlineImageByContentId(String cid) {
        return inlineImages.stream().filter(i -> i.getContentId().equals(cid)).findFirst();
    }

    public List<InlineImage> getInlineImages() {
        return inlineImages;
    }

    public Optional<String> getMessageId() {
        return Optional.ofNullable(messageId);
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        var email = (Email) o;

        return id.equals(email.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
