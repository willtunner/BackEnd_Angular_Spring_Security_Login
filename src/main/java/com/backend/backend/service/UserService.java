package com.backend.backend.service;

import com.backend.backend.models.Role;
import com.backend.backend.models.Server;
import com.backend.backend.models.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    Collection<User> list(int limit);
    List<User>getUsers();
}
