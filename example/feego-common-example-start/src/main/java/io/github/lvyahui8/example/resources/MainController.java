package io.github.lvyahui8.example.resources;

import feego.common.io.github.lvyahui8.example.SystemLogger;
import io.github.lvyahui8.example.api.dto.UserDTO;
import io.github.lvyahui8.sdk.lock.DistributedLock;
import io.github.lvyahui8.sdk.lock.LockFactory;
import io.github.lvyahui8.sdk.logging.logger.LogSchema;
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
        SystemLogger.status.trace("trace log example");
        SystemLogger.status.debug("debug log example");
        SystemLogger.status.warn("warn log example");
        SystemLogger.status.error("error log example");
        // 出现error级别的日志， 之前的内容会被回朔
        for (int i = 0; i < 20; i++) {
            SystemLogger.status.trace("trace after error");
        }
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
