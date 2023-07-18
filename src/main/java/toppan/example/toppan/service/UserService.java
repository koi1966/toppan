package toppan.example.toppan.service;

import toppan.example.toppan.models.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
