package com.kang.ignite.demo;

import com.kang.ignite.entity.MyLifecycleBean;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.cache.annotation.CacheConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HelloWord {


    public static void main(String[] args) {

        // Preparing IgniteConfiguration using Java APIs
        IgniteConfiguration cfg = new IgniteConfiguration();

        // The node will be started as a client node.
        cfg.setClientMode(true);

        cfg.setLifecycleBeans(new MyLifecycleBean());

        // Classes of custom Java logic will be transferred over the wire from this app.
        cfg.setPeerClassLoadingEnabled(false);

        // Setting up an IP Finder to ensure the client can locate the servers.
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        List<String> ads = new ArrayList<>();
        ads.add("192.168.128.138:47500..47509");
        ads.add("192.168.128.136:47500..47509");
        ads.add("192.168.128.137:47500..47509");
        ipFinder.setAddresses(ads);

        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        tcpDiscoverySpi.setIpFinder(ipFinder);
        tcpDiscoverySpi.setReconnectDelay(3000);
        cfg.setDiscoverySpi(tcpDiscoverySpi);
        // Starting the node
        Ignite ignite = Ignition.start(cfg);

        IgniteCompute compute = ignite.compute(ignite.cluster().forRemotes());
        compute.broadcast(()-> System.out.println("Hello Node: " + Ignition.localIgnite().cluster().localNode().id()));

        ignite.close();
    }

    //线程池c
    private static class RemoteTask implements IgniteRunnable{

        @IgniteInstanceResource
        Ignite ignite;

        @Override
        public void run() {

            System.out.println(
                    "Node ID： "+ ignite.cluster().localNode().id()+" \n" +
                    "OS: "+ System.getProperty("os.name") +"\n" +
                            "JRE: "+ System.getProperty("java.runtime.name")
            );
            IgniteCache<Object, Object> myCache = ignite.cache("myCache");

            System.out.println(">> "+ myCache.get(1) +" "+ myCache.get(2));

        }

    }


}
