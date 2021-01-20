package io.github.lvyahui8.example.task;

import io.github.lvyahui8.example.api.dto.UserDTO;
import io.github.lvyahui8.example.service.UserCacheObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/21
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserProfileReloadTask {

    final UserCacheObject userCacheObject;

    @Scheduled(cron = "*/5 * * * * ?")
    public void reload() {
        for (long i = 0; i < 10; i++) {
            userCacheObject.load(i);
            UserDTO userDTO = userCacheObject.get(i);
            System.out.println(userDTO);
        }
    }
}
