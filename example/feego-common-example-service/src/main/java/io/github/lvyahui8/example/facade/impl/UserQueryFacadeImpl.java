package io.github.lvyahui8.example.facade.impl;

import io.github.lvyahui8.example.api.dto.UserDTO;
import io.github.lvyahui8.example.api.facade.UserQueryFacade;
import io.github.lvyahui8.example.service.UserCacheObject;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 11:21
 */
@Service
public class UserQueryFacadeImpl implements UserQueryFacade {

    UserCacheObject userCacheObject;

    public void setUserCacheObject(UserCacheObject userCacheObject) {
        this.userCacheObject = userCacheObject;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userCacheObject.get(userId);
    }
}
