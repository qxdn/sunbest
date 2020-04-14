package com.qianxu.sunbest.service.api;

import com.qianxu.sunbest.model.User;

public interface UserService {
    public Integer addUser(User user);
    public Boolean isUserExits(String email);
    public Boolean androidLogin(String email,String password);
}
