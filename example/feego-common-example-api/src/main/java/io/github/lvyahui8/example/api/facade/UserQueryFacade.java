package io.github.lvyahui8.example.api.facade;

import io.github.lvyahui8.example.api.dto.UserDTO;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 11:17
 */
public interface UserQueryFacade {
    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    UserDTO getUserById(Long userId);
}
