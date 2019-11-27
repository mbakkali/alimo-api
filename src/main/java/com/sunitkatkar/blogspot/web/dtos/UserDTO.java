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

package com.sunitkatkar.blogspot.web.dtos;

import com.sunitkatkar.blogspot.tenant.model.Role;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * User entity to represent a {@link UserDTO} of the system.
 * 
 * The JPA definitions of {@link UserDTO} and {@link Role} will cause the following
 * 3 tables to be created:
 * <ul>
 * <li>user</li>
 * <li>role</li>
 * <li>user_roles</li>
 * </ul>
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 */
@Data
public class UserDTO {

    private int id;
    private String username;
    private String password;
    private boolean active;
    private String tenant;
    private Set<Role> roles;

    @Tolerate
    public UserDTO() {
    }
}
