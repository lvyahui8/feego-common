package io.github.lvyahui8.example.client.service;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 12:31
 */
public interface OrderService {
    /**
     * 创建订单
     * @param buyerId 买家id
     */
    void createOrder(Long buyerId);
}
