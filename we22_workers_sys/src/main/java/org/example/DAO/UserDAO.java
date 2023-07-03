package org.example.DAO;
import org.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;


public interface UserDAO {
    Optional<User> selectUserByName(String userName);
    boolean insertUser(User user);
    boolean verify(User user);
    Optional<User> selectUserByNameBack(String userName);
}
