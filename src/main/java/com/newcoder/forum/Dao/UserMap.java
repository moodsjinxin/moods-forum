package com.newcoder.forum.Dao;

import com.newcoder.forum.entity.User;

public interface UserMap {

    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updatePassword(int id, String password);
}
