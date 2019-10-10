package com.shawn.blog.service;

import com.shawn.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
