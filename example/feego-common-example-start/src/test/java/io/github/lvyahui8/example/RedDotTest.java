package io.github.lvyahui8.example;

import io.github.lvyahui8.example.reddot.AppRedDot;
import io.github.lvyahui8.sdk.reddot.RedDotManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.cluster.router.condition.config.AppRouter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedDotTest {

    @Autowired
    RedDotManager redDotManager;

    @Autowired
    public RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void testBasic() throws Exception {
        Object userId = 123L;
        redisTemplate.delete(userId.toString());

        redDotManager.enable(userId, AppRedDot.asset);
        Map<String, Boolean> activeMap = redDotManager.isActiveMap(userId,
                AppRedDot.asset, AppRedDot.profile, AppRedDot.my_account, AppRedDot.root);
        for (Map.Entry<String, Boolean> entry : activeMap.entrySet()) {
            Assert.assertTrue(entry.getValue());
        }

        redDotManager.enable(userId,AppRedDot.channels);
        activeMap = redDotManager.isActiveMap(userId, AppRedDot.channels, AppRedDot.hp, AppRedDot.root);
        for (Map.Entry<String, Boolean> entry : activeMap.entrySet()) {
            Assert.assertTrue(entry.getValue());
        }

        redDotManager.disable(userId,AppRedDot.channels);
        activeMap = redDotManager.isActiveMap(userId,AppRedDot.values());
        Assert.assertTrue(activeMap.get(AppRedDot.root.id()));
        Assert.assertTrue(activeMap.get(AppRedDot.my_account.id()));
        Assert.assertTrue(activeMap.get(AppRedDot.profile.id()));
        Assert.assertTrue(activeMap.get(AppRedDot.asset.id()));
        Assert.assertFalse(activeMap.get(AppRedDot.hp.id()));
        Assert.assertFalse(activeMap.get(AppRedDot.channels.id()));
    }
}
