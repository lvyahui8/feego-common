package io.github.lvyahui8.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/14 23:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResourceStringsTest {
    @Test
    public void testBasic() throws Exception {
        System.out.println(MultipleLienStrings.homepage_json.getContent());
    }
}
