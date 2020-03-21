package io.github.lvyahui8.example.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/3/21 11:18
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 2386235266784577053L;
    Long id;
    String username;
    String email;
}
