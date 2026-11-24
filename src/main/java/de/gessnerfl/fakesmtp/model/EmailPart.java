package de.gessnerfl.fakesmtp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class EmailPart {
    private Long id;

    private Email email;

    private String data;

    @JsonIgnore
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
