package io.github.lvyahui8.example.service.impl;

import io.github.lvyahui8.example.api.dto.UserDTO;
import io.github.lvyahui8.example.service.UserService;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 10:43
 */
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO getUser(Long id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("lvyahui8@gmai.com");
        userDTO.setUsername("feego" + System.currentTimeMillis());
        userDTO.setId(id);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ignored) { }
        return userDTO;
    }
}
