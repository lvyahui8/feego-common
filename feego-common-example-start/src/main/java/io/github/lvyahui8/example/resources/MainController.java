package io.github.lvyahui8.example.resources;

import feego.common.io.github.lvyahui8.example.SystemLogger;
import io.github.lvyahui8.core.logging.LogSchema;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/16 22:04
 */
@RequestMapping("/")
@RestController
public class MainController {
    @RequestMapping("/status")
    public Object status() {
        SystemLogger.status.info(LogSchema.empty().of("status","ok"));
        return "ok";
    }
}
