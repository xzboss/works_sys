package org.example.services;

import org.example.DAO.UserDAO;
import org.example.model.User;
import org.example.model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component("UserService")
@Service
public class UserService {
    private UserDAO userDAO;
    @Autowired
    public UserService(@Qualifier("userMySQL") UserDAO userDAO){
        this.userDAO = userDAO;
    }
    public Optional<User> selectUserByName(String userName) {
        return userDAO.selectUserByName(userName);
    }
    public boolean insertUser(User user) {
        if(selectUserByName(user.getUserName()).isEmpty()) return userDAO.insertUser(user);
        else return false;
    }
    public boolean verify(User user){
        return userDAO.verify(user);
    }
    //-------
    //
    //
    //


}
