package com.amberlight.cloud.gateway.auth.security.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActiveUserStore implements Serializable {

    private static final long serialVersionUID = 1L;

    public List<String> users;

    public ActiveUserStore() {
        users = new ArrayList<>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
