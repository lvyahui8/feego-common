package io.github.lvyahui8.example.service;


import io.github.lvyahui8.example.api.dto.UserDTO;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/22 10:43
 */
public interface UserService {
    UserDTO getUser(Long id) ;
}
