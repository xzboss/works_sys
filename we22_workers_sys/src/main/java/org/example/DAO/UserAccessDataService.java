package org.example.DAO;

import org.example.model.User;
import org.example.model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository("userMySQL")
public class UserAccessDataService implements UserDAO{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserAccessDataService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Optional<User> selectUserByName(String userName) {
        final String sql = "select * from user where userName = ?";
        List<User> userList = jdbcTemplate.query(sql, new UserRowMapper(),userName);
        return userList.stream().findFirst();
    }
    @Override
    public boolean insertUser(User user) {
        final String sql = "insert into user value(?,?,?)";
        int result = jdbcTemplate.update(
                sql,
                user.getUserId(),
                user.getUserName(),
                user.getPassword()
        );
        return result>0?true:false;
    }

    @Override
    public boolean verify(User user) {
        final String sql = "select * from user where userName = ? and password=?";
        List<User> employeeList = jdbcTemplate.query(sql, new UserRowMapper(),user.getUserName(),user.getPassword());
        return employeeList.isEmpty()?false:true;
    }
    //----------
    //
    //
    //
    @Override
    public Optional<User> selectUserByNameBack(String userName) {
        final String sql = "select * from user where userName = ?";
        List<User> userList = jdbcTemplate.query(sql, new UserRowMapper(),userName);
        return userList.stream().findFirst();
    }

}
