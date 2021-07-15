package com.kang.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.log4j.Log4JLogger;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class BootTest implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("项目启动完毕！");
    }

    public static Ignite buildIgnite() throws UnknownHostException {
       /* IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(true);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setMetricsLogFrequency(0);
        String hostIp = InetAddress.getLocalHost().getHostAddress();
        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setLocalAddress(hostIp);
        cfg.setCommunicationSpi(communicationSpi);


        TcpDiscoveryMulticastIpFinder tcp = new TcpDiscoveryMulticastIpFinder();
        tcp.setAddresses(Arrays.asList(hostIp + ":47500", "192.168.20.23:47500..47509", "192.168.20.22:47500..47509")); //,"192.168.20.24:47500" "192.168.20.24:47500..47509","192.168.20.23:47500..47509","192.168.20.18:47500..47509",

        //设置集群的本地侦听端口。建议固定一个端口
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        spi.setLocalPort(47500);
        spi.setLocalPortRange(0);
        spi.setLocalAddress(hostIp);
        spi.setIpFinder(tcp);
        cfg.setDiscoverySpi(spi);

        //Remote SPI with the same name is not configured (fix configuration or set -DIGNITE_SKIP_CONFIGURATION_CONSISTENCY_CHECK=true system property) [name=UriDeploymentSpi, loc=org.apache.ignite.spi.deployment.uri.UriDeploymentSpi, locNode=TcpDiscoveryNode [id=2d7ab0ee-67ae-4c1a-824d-2302c5cecd65, consistentId=2d7ab0ee-67ae-4c1a-824d-2302c5cecd65, addrs=ArrayList [0:0:0:0:0:0:0:1, 127.0.0.1, 172.27.35.1, 192.168.20.19], sockAddrs=HashSet [/0:0:0:0:0:0:0:1:0, /127.0.0.1:0, LAPTOP-SM73C720/172.27.35.1:0, /192.168.20.19:0], discPort=0, order=3, intOrder=0, lastExchangeTime=1600344728278, loc=true, ver=2.8.1//20200521-sha1:86422096, isClient=true], rmt=null, rmtNode=TcpDiscoveryNode [id=9f295f86-06dd-4b52-b921-111692ed8135, consistentId=3dc47cdc-bce6-4675-8301-b19eb54cb3b3, addrs=ArrayList [192.168.20.18], sockAddrs=HashSet [/192.168.20.18:47101], discPort=47101, order=1, intOrder=1, lastExchangeTime=1600345231507, loc=false, ver=2.8.1//20200521-sha1:86422096, isClient=false]]
        IgniteLogger log = null;
        try {
            log = new Log4JLogger("C:\\Kang\\software\\apache-ignite-2.8.1-bin (1)\\apache-ignite-2.8.1-bin\\config\\ignite-log4j.xml");
        } catch (IgniteCheckedException e) {
            e.printStackTrace();
        }
        cfg.setGridLogger(log);

        // Failed to connect to cluster, connection failed and failed to reconnect.

        Ignite ignite = Ignition.start(cfg);
        log.error("ignite加载完毕！");
        return ignite;*/
        return null;
    }


}
