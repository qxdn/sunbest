package com.qianxu.sunbest.service.impl;

import com.qianxu.sunbest.dao.UserDao;
import com.qianxu.sunbest.model.Role;
import com.qianxu.sunbest.model.User;
import com.qianxu.sunbest.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Integer addUser(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userDao.addUser(user);
        Map<String, Object> maps = new HashMap<>();
        maps.put("userId", user.getId());
        maps.put("roleIds", user.getRoles());
        return userDao.addUserRef(maps);
    }

    @Override
    public Boolean isUserExits(String email) {
        User user = userDao.getUserByEmail(email);
        if (null == user) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("获取user");
        User user = userDao.getUserByEmail(username);
        if (user == null) {
            log.debug("用户不存在");
            throw new UsernameNotFoundException("用户不存在");
        }
        log.debug("获取角色");
        List<Role> roles = userDao.getRoleByUid(user.getId());
        user.setRoles(roles);
        return user;
    }

    @Override
    public Boolean androidLogin(String email, String password) {
        User user = userDao.getUserByEmail(email);
        log.info(password);
        log.info(user.toString());
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean changePassword(String email, String newPass) {
        Boolean status=false;
        if(newPass.length()>12||newPass.length()<6){
            status=false;
            return status;
        }

        User user=userDao.getUserByEmail(email);
        user.setPassword(passwordEncoder.encode(newPass));
        
        int lines= userDao.changePassword(user);
        if(lines>0){
            status=true;
        }

        return status;
    }
}
