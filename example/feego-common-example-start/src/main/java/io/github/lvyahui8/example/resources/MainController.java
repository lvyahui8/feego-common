package io.github.lvyahui8.example.resources;

import feego.common.io.github.lvyahui8.example.SystemLogger;
import io.github.lvyahui8.core.lock.DistributedLock;
import io.github.lvyahui8.core.lock.LockFactory;
import io.github.lvyahui8.core.logging.LogSchema;
import io.github.lvyahui8.example.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/16 22:04
 */
@RequestMapping("/")
@RestController
@Slf4j
public class MainController {
    private static int n = 0 ;

    @Autowired
    LockFactory lockFactory;

    @Autowired
    StringRedisTemplate redisTemplate;


    @RequestMapping("/update")
    public Object update() {
        DistributedLock lock = lockFactory.newDistributeLock("feego:common:update.key", UUID.randomUUID().toString());
        boolean locked = false;
        try {
            if (locked = lock.tryLock()) {
                SystemLogger.campaign.info("Got the lock");
                return n++;
            } else {
                SystemLogger.campaign.warn("Didn't get the lock");
                return 0;
            }
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    @RequestMapping("/status")
    public Object status() {
        SystemLogger.status.info(LogSchema.empty().of("status","ok"));
        return "ok";
    }

    @PostMapping("/save")
    public Object save(@RequestBody UserDTO userDTO) {
        log.info(userDTO.toString());
        return "success";
    }

    @GetMapping("/query")
    public Object query() {
        /// log.info("query");
        return redisTemplate.opsForValue().get("main");
    }
}
