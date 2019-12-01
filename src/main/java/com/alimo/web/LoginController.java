/**
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
package com.alimo.web;

import com.alimo.web.exceptions.TenantNotFoundException;
import com.alimo.tenant.model.CustomUserDetails;
import com.alimo.tenant.model.User;
import com.alimo.tenant.service.UserService;
import com.alimo.web.dtos.UserDTO;
import com.alimo.web.exceptions.UserNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@Log
public class LoginController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(Model model) {
        getLoggedInEmail().ifPresent(f -> {
            model.addAttribute("userName", f);
        });
        getTenantName().ifPresent(d -> {
            model.addAttribute("tenantName", d);
        });

        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex(Model model) {
        getLoggedInEmail().ifPresent(f -> {
            model.addAttribute("userName", f);
        });
        getTenantName().ifPresent(d -> {
            model.addAttribute("tenantName", d);
        });
        return "user/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam UserDTO userDTO) {
        if(userService.tenantExists(userDTO.getTenant())){
            log.info("Tenant is known");
            User user = userService.findByEmailAndTenantname(userDTO.getUsername(),userDTO.getTenant());
            if( user != null ){
                getLoggedInEmail();
                getTenantName();
            }else {
                log.warning("User is not known");
                throw new UserNotFoundException("Utilisateur introuvable");
            }
        }else {
            log.warning("Tenant is not known");
            throw new TenantNotFoundException("Utilisateur introuvable");
        }
        return "login";
    }


    private Optional<String> getLoggedInEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = null;
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            // User user = (User) auth.getPrincipal();
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            userName = userDetails.getUsername();
        }

        return Optional.ofNullable(userName);
    }

    private Optional<String> getTenantName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String tenantName = null;
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            // User user = (User) auth.getPrincipal();
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            tenantName = userDetails.getTenant();
        }
        return Optional.ofNullable(tenantName);
    }
}
