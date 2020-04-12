package io.github.lvyahui8.example.configuration;

import io.github.lvyahui8.configuration.annotations.RuntimeConfiguration;
import io.github.lvyahui8.example.dto.UserDTO;
import lombok.Data;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/8 21:05
 */
@RuntimeConfiguration
@Data
public class RuntimeSettings {
    String homepageUrl = "https://www.cnblogs.com/lvyahui";
    Boolean entrySwitch = false;
    boolean open;
    UserDTO commonUserDTO = new UserDTO();
}
