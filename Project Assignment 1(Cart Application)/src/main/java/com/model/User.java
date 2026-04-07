package com.model;

import com.enums.membership;

public class User {
    private int id;
    private String name;
    private membership membership;

    public User() {
    }

    public User(int id, String name, membership membership) {
        this.id = id;
        this.name = name;
        this.membership = membership;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public membership getMembership() {
        return membership;
    }

    public void setMembership(membership membership) {
        this.membership = membership;
    }
}
