package com.kang.ignite;

import com.kang.ignite.config.BootTest;
import com.kang.ignite.entity.CharacterCountTask;
import com.kang.ignite.utils.GroovyUtils;
import groovy.lang.GroovyObject;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.ignite.*;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.log4j.Log4JLogger;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Logger
@SpringBootTest
public class IgniteApplicationTests {


    @Test
    void contextLoads() throws UnknownHostException {
      /*  Ignite start = BootTest.buildIgnite();

        // start.active(true);//必须阻塞等待激活

        if (!start.cluster().active()) {
            start.cluster().active(true);
        }

        ClusterGroup clusterGroup = start.cluster().forRemotes(); //不转移节点

        IgniteCompute compute = start.compute(clusterGroup).withNoFailover();

        compute.broadcast(() -> System.out.println("广播"));

        Integer sumNum = compute.execute(new CharacterCountTask(), "诸葛亮 军事 刘备");

        System.out.println(sumNum);

        start.close();*/
    }


    @Test
    public void test1() {
        String path = "./src/main/java/com/kang/ignite/utils/GMath.groovy";
        List<Integer> params = new ArrayList<>();
        params.add(1);
        params.add(2);
        GroovyObject groovyObject = GroovyUtils.getGroovyObject(path);
        Integer r = (Integer)groovyObject.invokeMethod("add", params);
    }

    @Test
    public void lowerFirstCase(){

    }


    public static void main(String[] args) {
        String fileName  = "controller";
        char[] chars = fileName.toCharArray();
        for(char c: chars){
            System.out.println(c);
        }
        chars[0] += 31;
        System.out.println(String.valueOf(chars));
    }

}
