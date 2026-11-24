package de.gessnerfl.fakesmtp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EmailAttachment implements IdHolder<Long> {
    private Long id;

    private Email email;

    private String filename;

    private byte[] data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
