package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    List<User> getAllUsers();
    User showUser(int id);
    void updateUser(int id, User user);

    void deleteUser(int id);
}
