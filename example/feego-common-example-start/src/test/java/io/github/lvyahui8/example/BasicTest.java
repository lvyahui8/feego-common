package io.github.lvyahui8.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/28 23:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BasicTest {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
