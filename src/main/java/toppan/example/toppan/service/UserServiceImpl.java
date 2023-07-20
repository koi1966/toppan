package toppan.example.toppan.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import toppan.example.toppan.models.Role;
import toppan.example.toppan.models.User;
import toppan.example.toppan.models.repo.RoleDao;
import toppan.example.toppan.models.repo.UserDao;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * sdadsad
     * @param user - kjbhkjkjn
     */
    @Override
    public void save(User user) {

        /**
         * 27.40
          */

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(1L));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {

        return userDao.findByUsername(username);
    }
}

