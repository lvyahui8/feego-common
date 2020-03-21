package io.github.lvyahui8.example.client.service.impl;

import feego.common.io.github.lvyahui8.example.client.SystemLogger;
import io.github.lvyahui8.example.api.dto.UserDTO;
import io.github.lvyahui8.example.api.facade.UserQueryFacade;
import io.github.lvyahui8.example.client.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 12:32
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Reference
    private UserQueryFacade userQueryFacade;

    @Override
    public void createOrder(Long buyerId) {
        UserDTO user = userQueryFacade.getUserById(buyerId);
        SystemLogger.order.info("create order for user: {}",user);
    }
}
