package com.newcoder.forum.util;

import com.newcoder.forum.entity.User;

public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUsers(User user) {
        users.set(user);
    }

    public User getUser() {return users.get();}

    public void clear() { users.remove();}
}
