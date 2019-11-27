/*
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunitkatkar.blogspot.tenant.service;

import com.sunitkatkar.blogspot.master.repository.MasterTenantRepository;
import com.sunitkatkar.blogspot.tenant.model.User;
import com.sunitkatkar.blogspot.tenant.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService} which accesses the {@link User}
 * entity. This is the recommended way to access the entities through an
 * interface rather than using the corresponding repository. This allows for
 * separation into repository code and the service layer.
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 */
@Service
@Log
public class UserService {

    private final UserRepository userRepository;
    private final MasterTenantRepository masterTenantRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, MasterTenantRepository masterTenantRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.masterTenantRepository = masterTenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User justSavedUser = userRepository.save(user);
        log.info("User:" + justSavedUser.getEmail() + " saved.");
        return justSavedUser;
    }

    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext()
                .getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            String username = ((UserDetails) userDetails).getUsername();
            log.info("Logged in username:" + username);
            return username;
        }
        return null;
    }

    public User findByEmailAndTenantname(String username, String tenant) {
        User user = userRepository.findByUsernameAndTenantname(username,
                tenant);
        log.info("Found user with username:" + user.getEmail()
                + " from tenant:" + user.getTenant());
        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean tenantExists(String tenant) {
        return masterTenantRepository.existsByTenantId(tenant);
    }
}
