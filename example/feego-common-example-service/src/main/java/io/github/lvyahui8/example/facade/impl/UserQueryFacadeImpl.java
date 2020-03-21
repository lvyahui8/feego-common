package io.github.lvyahui8.example.facade.impl;

import io.github.lvyahui8.example.api.dto.UserDTO;
import io.github.lvyahui8.example.api.facade.UserQueryFacade;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 11:21
 */
@Service
public class UserQueryFacadeImpl implements UserQueryFacade {
    @Override
    public UserDTO getUserById(Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("lvyahui8@gmai.com");
        userDTO.setUsername("feego");
        userDTO.setId(userId);
        return userDTO;
    }
}
