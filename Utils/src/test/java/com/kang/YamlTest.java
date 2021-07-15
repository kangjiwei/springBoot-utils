package com.kang;

import com.util.UtilsApplication;
import lombok.Cleanup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XiongDa
 * @Date: 2021/06/11 10:25
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtilsApplication.class)
public class YamlTest {

    /**
     * yaml.dumpAsMap 会将map中的最后一个对象不按照
     *
     * @throws IOException
     */
    @Test
    public void parseMapToYaml() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "kang");
        map.put("lession", new ArrayList<Map<String, Object>>() {{
            this.add(new HashMap<String, Object>() {{
                this.put("sssss", "mmmmm");
            }});
            this.add(new HashMap<String, Object>() {{
                this.put("sssss", "mmmmm");
            }});
            this.add(new HashMap<String, Object>() {{
                this.put("sssss", "mmmmm");
            }});
            this.add(new HashMap<String, Object>() {{
                this.put("sssss", "mmmmm");
            }});
        }});
        StringWriter writer = new StringWriter();

        Yaml yaml = new Yaml();
        @Cleanup FileWriter fileWriter = new FileWriter("D:\\demo.yaml");
        String dumpMapStr = yaml.dumpAsMap(map);
        fileWriter.flush();
        System.out.println(dumpMapStr);
    }

}
