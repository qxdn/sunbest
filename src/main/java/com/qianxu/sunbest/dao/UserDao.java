package com.qianxu.sunbest.dao;

import com.qianxu.sunbest.model.Role;
import com.qianxu.sunbest.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    public User getUserByEmail(String email);

    public List<Role> getRoleByUid(Integer id);

    public int addUser(User user);

    public int addUserRef(Map<String,Object> map);
}
