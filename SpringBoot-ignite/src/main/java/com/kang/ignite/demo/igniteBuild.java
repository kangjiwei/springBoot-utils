package com.kang.ignite.demo;

import com.kang.ignite.entity.ComputTask;
import com.kang.ignite.entity.FieldEntity;
import com.kang.ignite.entity.MyLifecycleBean;
import com.kang.ignite.entity.Student;
import com.kang.ignite.groovy.GroovyUtil;
import groovy.lang.GroovyObject;
import org.apache.ignite.*;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cluster.BaselineNode;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobAdapter;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskSplitAdapter;
import org.apache.ignite.configuration.*;
import org.apache.ignite.internal.managers.GridManagerAdapter;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.logger.log4j.Log4JLogger;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.junit.Test;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.apache.ignite.cache.CacheMode.PARTITIONED;

public class igniteBuild {


    @Test
    public void startCache() {
        try (Ignite ignite = Ignition.start()) {
            CacheConfiguration cfg = new CacheConfiguration();
            cfg.setCacheMode(PARTITIONED);
            cfg.setName("che");
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cfg);

            // Store keys in cache (values will end up on different cache nodes).
            for (int i = 0; i < 10; i++)
                cache.put(i, Integer.toString(i));

            for (int i = 0; i < 10; i++)
                System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
        }

    }


    @Test
    public void startCache1() {

        Ignite ignite = Ignition.start();

        CacheConfiguration<String, String> cfg = new CacheConfiguration<String, String>();
        cfg.setName("cacheName");
        cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cfg.setIndexedTypes(String.class, String.class);
        IgniteCache<String, String> cache = ignite.getOrCreateCache(cfg);
        cache.put("name", "zhugliang");
        System.out.println("=====================" + cache.get("name"));

    }


    /**
     * org.h2.jdbc.JdbcSQLNonTransientConnectionException: Unsupported connection setting "MULTI_THREADED" [90113-200]
     * SQL查询 关联查询
     */
    @Test
    public void linkeIgnite() {
        Ignite ignite = Ignition.start();

        CacheConfiguration<Integer, Student> stuCfg = new CacheConfiguration<Integer, Student>();
        stuCfg.setName("STU");
        stuCfg.setCacheMode(CacheMode.PARTITIONED);
        stuCfg.setIndexedTypes(Integer.class, Student.class);
        IgniteCache<Integer, Student> stuCache = ignite.getOrCreateCache(stuCfg);
        stuCache.put(1, new Student(1, "张三", 10));
        stuCache.put(2, new Student(1, "李四", 11));
        stuCache.put(3, new Student(2, "王五", 11));
        stuCache.put(4, new Student(2, "胜七", 10));

        SqlFieldsQuery sql = new SqlFieldsQuery(
                "select concat(stu.classId, '----', stu.name) as stuinfo"
                        + " "
                        + "from Student as stu "
                        + "");

        QueryCursor<List<?>> cursor = stuCache.query(sql);
        for (List<?> row : cursor) {
            System.out.println("学生信息：" + row.get(0));
        }

        CacheConfiguration<Integer, FieldEntity> classCfg = new CacheConfiguration<Integer, FieldEntity>();
        classCfg.setName("CLA");
        classCfg.setCacheMode(PARTITIONED);
        classCfg.setIndexedTypes(Integer.class, FieldEntity.class);  

        IgniteCache<Integer, FieldEntity> classCache = ignite.getOrCreateCache(classCfg);
        classCache.put(1, new FieldEntity(1, "五年级一班"));
        classCache.put(2, new FieldEntity(2, "五年级二班"));
        String queryStuSql = "select concat(cla.id, '----', cla.name) as clainfo"
                + ", concat(stu.name, '----', stu.age) as stuinfo "
                + "from FieldEntity as cla, STU.Student as stu "
                + "where cla.id = stu.classId";
        System.out.println(queryStuSql);
        SqlFieldsQuery sql1 = new SqlFieldsQuery(queryStuSql);
        QueryCursor<List<?>> cursor1 = classCache.query(sql1);
        for (List<?> row : cursor1) {
            System.out.println("班级信息：" + row.get(0) + ", 学生信息：" + row.get(1));
        }

    }


    /**
     * 异步执行
     */
    @Test
    public void igniteAsync() {

        IgniteConfiguration cfg = new IgniteConfiguration();
        //生命周期事件
        cfg.setLifecycleBeans(new MyLifecycleBean());
        cfg.setClientMode(true);

        CacheConfiguration ccfg = new CacheConfiguration();
        ccfg.setName("firstCache");
        ccfg.setCacheMode(PARTITIONED);
        ccfg.setBackups(1);
        ccfg.setIndexedTypes(Integer.class, String.class);

        cfg.setCacheConfiguration(ccfg);

        Ignite ignite = Ignition.start(cfg);
        IgniteCompute compute = ignite.compute();
        IgniteFuture<String> fut = compute.callAsync(() -> {
            return "Hello Word!";
        });

        fut.listen(fu -> System.out.println(fu.get()));

    }


    /**
     * IgniteCompute接口提供了在集群节点或者一个集群组中运行很多种类型计算的方法，这些方法可以以一个分布式的形式执行任务或者闭包
     */
    @Test
    public void buildIgnites() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
//        ipFinder.setAddresses(Arrays.asList("192.168.128.138:47500..47509", "192.168.128.136:47500..47509", "192.168.128.137:47500..47509"));
        ipFinder.setAddresses(Collections.singletonList("192.168.20.21:47101"));

        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        tcpDiscoverySpi.setIpFinder(ipFinder);
        tcpDiscoverySpi.setReconnectDelay(3000);
        //设置为客户端
        cfg.setClientMode(true);
        cfg.setDiscoverySpi(tcpDiscoverySpi);

        Ignite ignite = Ignition.start(cfg);

        CacheConfiguration ccfg = new CacheConfiguration();
        ccfg.setName("c1");
        ccfg.setBackups(1);
        ccfg.setCacheMode(PARTITIONED);
        IgniteCache<Integer, Object> cache = ignite.getOrCreateCache(ccfg);
        cache.clear();

        int keyCnt = 10;

        System.out.println("Synchronously put records ...");

        for (int i = 0; i < keyCnt; i++) {
            cache.put(i, Integer.toString(i));
        }

        IgniteCluster cluster = ignite.cluster();
        Collection<String> strings = cluster.hostNames();
        for (String string : strings) {
            System.out.println("nodeName ： " + string);
        }

        // Remote job threw user exception (override or implement ComputeTask.result(..) method if you would like to have automatic failover for this exception)
        // 也可以通过集群组来限制执行的范围，这时，计算只会在集群组内的节点上执行。

        ClusterGroup clusterGroup = cluster.forRemotes();

        IgniteCompute compute = ignite.compute(clusterGroup);
        compute.broadcast(() -> {
            for (int i = 0; i < keyCnt; i++) {
                System.out.println("Got [key=" + i + ", val=" + cache.get(i) + "] ");
            }
        });
    }


    public void clusters() {
        IgniteConfiguration cfg = new IgniteConfiguration();

// Explicitly configure TCP discovery SPI to provide list of initial nodes
// from the first cluster.
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();

// Initial local port to listen to.
        discoverySpi.setLocalPort(47500);

// Changing local port range. This is an optional action.
        discoverySpi.setLocalPortRange(9);

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

// Addresses and port range of the nodes from the first cluster.
// 127.0.0.1 can be replaced with actual IP addresses or host names.
// The port range is optional.
        ipFinder.setAddresses(Arrays.asList("192.168.20.23:47500..47520"));

// Overriding IP finder.
        discoverySpi.setIpFinder(ipFinder);

// Explicitly configure TCP communication SPI by changing local port number for
// the nodes from the first cluster.
        TcpCommunicationSpi commSpi = new TcpCommunicationSpi();

        commSpi.setLocalPort(47100);

// Overriding discovery SPI.
        cfg.setDiscoverySpi(discoverySpi);

// Overriding communication SPI.
        cfg.setCommunicationSpi(commSpi);

// Starting a node.
        Ignite ignite = Ignition.start(cfg);
        IgniteCluster cluster = ignite.cluster();
        Collection<String> strings = cluster.hostNames();
        for (String string : strings) {
            System.out.println("nodeName ： " + string);
        }
    }


    /**
     * Remote job threw user exception (override or implement ComputeTask.result(..) method if you would like to have automatic failover for this exception)
     */

    @Test
    public void buildsCluster() {

        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setClientMode(true);

        cfg.setLifecycleBeans(new MyLifecycleBean());

        cfg.setPeerClassLoadingEnabled(true);

        cfg.setMetricsLogFrequency(5L * 6 * 1000);

        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Arrays.asList("192.168.20.23:47500..47509")); //,"192.168.20.24:47500..47509","192.168.20.18:47500..47509","192.168.20.21:47500..47509",

        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        tcpDiscoverySpi.setIpFinder(ipFinder);

        cfg.setDiscoverySpi(tcpDiscoverySpi);
        try (Ignite ignite = Ignition.start(cfg)) {

            //TODO start 与 Ignite的关系。
            IgniteCluster cluster = ignite.cluster();
            Collection<String> strings = cluster.hostNames();
            strings.forEach(string -> {
                System.out.println("》》》》》 name " + string);
            });
            ClusterGroup clusterGroup = cluster.forRemotes();
            IgniteCompute compute = ignite.compute(clusterGroup);
            // Remote job threw user exception
            compute.broadcast(() -> System.out.println("君主"));
    /*        compute.execute(new com.kang.ignite.demo.IgniteCompute(),"qw we er tt yy");
            Collection<Integer> apply = compute.apply(String::length,
                    Arrays.asList("How many characters".split(" "))
            );*/
            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();
            // Iterate through all the words in the sentence and create Callable jobs.
            for (final String word : "Count characters using callable".split(" "))
                calls.add(new IgniteCallable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        System.out.println("长度 " + word.length());
                        return word.length();
                    }
                });
            // Execute collection of Callables on the grid.
            Collection<Integer> res = ignite.compute().call(calls);
            res.forEach(integer -> {
                System.out.println(integer);
            });
            // Add up all the results.
            int sum = res.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Total number of characters is '" + sum + "'.");
            ignite.close();
        }


    }

    @Test
    public void testCompute() {

        IgniteConfiguration configuration = new IgniteConfiguration();
        configuration.setIgniteInstanceName("sssssssss");
        configuration.setClientMode(false);
        configuration.setIgniteInstanceName("diet");
        configuration.setIgniteHome("c:\\Kang");
        Ignite sss = Ignition.start(configuration);
        IgniteCompute compute = sss.compute();
        int cnt = compute.execute(CharacterCountTask.class, "Hello Grid Enabled World!");
        System.out.println(">>> Total number of characters in the phrase is '" + cnt + "'.");
    }

    private static class CharacterCountTask extends ComputeTaskSplitAdapter<String, Integer> {
        @Override
        public Collection<? extends ComputeJob> split(int gridSize, String arg) {
            String[] words = arg.split(" ");
            List<ComputeJob> jobs = new ArrayList<>(words.length);
            for (final String word : arg.split(" ")) {
                jobs.add(new ComputeJobAdapter() {
                    @Override
                    public Object execute() {
                        System.out.println(">>> Printing '" + word + "' on from compute job.");
                        return word.length();
                    }
                });
            }
            return jobs;
        }

        @Override
        public Integer reduce(List<ComputeJobResult> results) {
            int sum = 0;
            for (ComputeJobResult res : results)
                sum += res.<Integer>getData();
            return sum;
        }
    }

    @Test
    public void execute() throws IgniteCheckedException {
        IgniteConfiguration configuration = new IgniteConfiguration();
        /* configuration.setClientMode(true);*/

        configuration.setPeerClassLoadingEnabled(true);
        configuration.setMetricsLogFrequency(2L * 100);

        configuration.setIgniteInstanceName("democluster1");
        configuration.setClientMode(true);
        configuration.setPeerClassLoadingEnabled(true);
        configuration.setMetricsLogFrequency(0);


        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();

        //TODO 访问24，24就是stopped||offline ！
        ipFinder.setAddresses(Arrays.asList("192.168.20.21:47500", "192.168.20.18:47500", "192.168.20.21:47500", "192.168.20.23:47500", "192.168.20.24:47500"));    //
        spi.setIpFinder(ipFinder);
        configuration.setDiscoverySpi(spi);
        //Remote SPI with the same name is not configured (fix configuration or set -DIGNITE_SKIP_CONFIGURATION_CONSISTENCY_CHECK=true system property) [name=UriDeploymentSpi, loc=org.apache.ignite.spi.deployment.uri.UriDeploymentSpi, locNode=TcpDiscoveryNode [id=2d7ab0ee-67ae-4c1a-824d-2302c5cecd65, consistentId=2d7ab0ee-67ae-4c1a-824d-2302c5cecd65, addrs=ArrayList [0:0:0:0:0:0:0:1, 127.0.0.1, 172.27.35.1, 192.168.20.19], sockAddrs=HashSet [/0:0:0:0:0:0:0:1:0, /127.0.0.1:0, LAPTOP-SM73C720/172.27.35.1:0, /192.168.20.19:0], discPort=0, order=3, intOrder=0, lastExchangeTime=1600344728278, loc=true, ver=2.8.1//20200521-sha1:86422096, isClient=true], rmt=null, rmtNode=TcpDiscoveryNode [id=9f295f86-06dd-4b52-b921-111692ed8135, consistentId=3dc47cdc-bce6-4675-8301-b19eb54cb3b3, addrs=ArrayList [192.168.20.18], sockAddrs=HashSet [/192.168.20.18:47101], discPort=47101, order=1, intOrder=1, lastExchangeTime=1600345231507, loc=false, ver=2.8.1//20200521-sha1:86422096, isClient=false]]
        IgniteLogger log = new Log4JLogger("C:\\Kang\\software\\apache-ignite-2.8.1-bin (1)\\apache-ignite-2.8.1-bin\\config\\ignite-log4j.xml");
        configuration.setGridLogger(log);

        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setLocalPort(47101);
        communicationSpi.setLocalAddress("192.168.20.19");
        configuration.setCommunicationSpi(communicationSpi);

      /*  UriDeploymentSpi deploymentSpi = new UriDeploymentSpi();
        //deploymentSpi.setUriList(Arrays.asList("file:///d://data"));
        deploymentSpi.setUriList(Arrays.asList("file:///c://home"));
        configuration.setDeploymentSpi(deploymentSpi);*/

        DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
        DataRegionConfiguration dataRegionConfiguration = new DataRegionConfiguration();
        dataRegionConfiguration.setMaxSize(4L * 1024 * 1024 * 1024);
        dataRegionConfiguration.setInitialSize(2L * 1024 * 1024 * 1024);
        dataRegionConfiguration.setName("computegrid_Region");
        dataRegionConfiguration.setPersistenceEnabled(true);
        dataStorageConfiguration.setDefaultDataRegionConfiguration(dataRegionConfiguration);
        configuration.setDataStorageConfiguration(dataStorageConfiguration);

        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("default");
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setEncryptionEnabled(false);
        configuration.setCacheConfiguration(cacheConfiguration);


        Ignite start = Ignition.start(configuration);

        IgniteCache<Object, Object> cache = start.getOrCreateCache("cache");
        cache.put("pu", "sha");
        System.out.println(cache.get("pu"));

        IgniteCompute compute = start.compute();
        compute.broadcast(() -> System.out.println(1));
        Collection<Integer> res = compute.apply(
                String::length,
                Arrays.asList("How many characters".split(" "))
        );
        res.forEach(ii -> {
            System.out.println(" 机会  " + ii);
        });
      /*  int total = res.stream().mapToInt(Integer::intValue).sum();
        System.out.println(total);*/
    }

    @Test
    public void againIgnite() throws UnknownHostException {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(true);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setMetricsLogFrequency(0);

        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setLocalAddress(InetAddress.getLocalHost().getHostAddress());
        cfg.setCommunicationSpi(communicationSpi);


        TcpDiscoveryMulticastIpFinder tcp = new TcpDiscoveryMulticastIpFinder();
        tcp.setAddresses(Arrays.asList("192.168.20.28:47500", "192.168.20.29:47500..47509")); //,"192.168.20.24:47500" "192.168.20.24:47500..47509","192.168.20.23:47500..47509","192.168.20.18:47500..47509",

        //设置集群的本地侦听端口。建议固定一个端口
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        spi.setLocalPort(47500);
        spi.setLocalPortRange(9);
        spi.setLocalAddress(InetAddress.getLocalHost().getHostAddress());
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

        Ignite start = Ignition.start(cfg);
        // start.active(true);//必须阻塞等待激活

        if (!start.cluster().active()) {
            start.cluster().active(true);
        }

        ClusterGroup clusterGroup = start.cluster().forRemotes(); //不转移节点

        IgniteCompute compute = start.compute(clusterGroup).withNoFailover();

        compute.broadcast(() -> System.out.println(" 1 "));

        Collection<IgniteCallable<Integer>> calls = new ArrayList<>();
        // Iterate through all the words in the sentence and create Callable jobs.
        for (final String word : "Count characters using callable".split(" "))
            calls.add(new IgniteCallable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("长度 " + word.length());
                    return word.length();
                }
            });
        // Execute collection of Callables on the grid.
        Collection<Integer> res = start.compute().call(calls);
        start.close();
    }


    /**
     * 组播IP探测器
     */
    @Test
    public void multiCastIgnite() throws UnknownHostException {

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(true);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setMetricsLogFrequency(0);
        String hostIp = InetAddress.getLocalHost().getHostAddress();
        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setLocalAddress(hostIp);
        cfg.setCommunicationSpi(communicationSpi);

        TcpDiscoveryMulticastIpFinder tcp = new TcpDiscoveryMulticastIpFinder();
        tcp.setAddresses(Arrays.asList("192.168.20.28:47500..47509", "192.168.20.29:47500..47509"));

        //设置集群的本地侦听端口。建议固定一个端口
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        spi.setLocalPort(47500);
        spi.setLocalPortRange(9);
        spi.setLocalAddress(hostIp);
        spi.setIpFinder(tcp);
        cfg.setDiscoverySpi(spi);

        //Remote SPI with the same name is not configured (fix configuration or set -DIGNITE_SKIP_CONFIGURATION_CONSISTENCY_CHECK=true system property) [name=UriDeploymentSpi, loc=org.apache.ignite.spi.deployment.uri.UriDeploymentSpi, locNode=TcpDiscoveryNode [id=2d7ab0ee-67ae-4c1a-824d-2302c5cecd65, consistentId=2d7ab0ee-67ae-4c1a-824d-2302c5cecd65, addrs=ArrayList [0:0:0:0:0:0:0:1, 127.0.0.1, 172.27.35.1, 192.168.20.19], sockAddrs=HashSet [/0:0:0:0:0:0:0:1:0, /127.0.0.1:0, LAPTOP-SM73C720/172.27.35.1:0, /192.168.20.19:0], discPort=0, order=3, intOrder=0, lastExchangeTime=1600344728278, loc=true, ver=2.8.1//20200521-sha1:86422096, isClient=true], rmt=null, rmtNode=TcpDiscoveryNode [id=9f295f86-06dd-4b52-b921-111692ed8135, consistentId=3dc47cdc-bce6-4675-8301-b19eb54cb3b3, addrs=ArrayList [192.168.20.18], sockAddrs=HashSet [/192.168.20.18:47101], discPort=47101, order=1, intOrder=1, lastExchangeTime=1600345231507, loc=false, ver=2.8.1//20200521-sha1:86422096, isClient=false]]
        IgniteLogger log = null;
        try {
            log = new Log4JLogger("./src/xml/ignite-log4j.xml");
        } catch (IgniteCheckedException e) {
            e.printStackTrace();
        }
        cfg.setGridLogger(log);

        // Failed to connect to cluster, connection failed and failed to reconnect.
        Ignite start = Ignition.start(cfg);
        // start.active(true);//必须阻塞等待激活
        if (!start.cluster().active()) {
            start.cluster().active(true);
        }

        ClusterGroup clusterGroup = start.cluster().forRemotes(); //不转移节点

        IgniteCompute compute = start.compute(clusterGroup).withNoFailover();

        compute.broadcast(() -> System.out.println("小喇叭开始广播了！ "));

        compute.execute(new ComputeLengthTask(), "天气预 报 代 价 爱 丽");

        start.close();
    }


    /**
     * 瘦客户端
     */
    @Test
    public void thinClient() {
        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        // == 与 equal的区别
        // == 比较变量的内存地址
        System.out.println(e);
        System.out.println((a == c));
        System.out.println((a.equals(e)));
    }


    @Test
    public void compileGroovy() {
        String path = "./src/groovy/GMath.groovy";
        List<Integer> params = new ArrayList<>();
        params.add(1);
        params.add(2);
        Optional<GroovyObject> groovyObject = GroovyUtil.getGroovyObject(path);
        if (groovyObject.isPresent()) {
            Integer add = (Integer) GroovyUtil.invokeMethod(groovyObject.get(), "add", params);
            System.out.println(add); // 输出 3
        } else {
            System.out.println("是空的！");
        }
    }


    @Test
    public void test(){

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(true);
        cfg.setPeerClassLoadingEnabled(true);
        cfg.setMetricsLogFrequency(0);

        TcpCommunicationSpi communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setLocalAddress("192.168.20.28");
        cfg.setCommunicationSpi(communicationSpi);

        TcpDiscoveryMulticastIpFinder tcp = new TcpDiscoveryMulticastIpFinder();
        tcp.setAddresses(Arrays.asList("192.168.20.28:47500", "192.168.20.29:47500..47509"));

        //设置集群的本地侦听端口。建议固定一个端口
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        spi.setLocalPort(47500);
        spi.setLocalPortRange(9);
        spi.setLocalAddress("192.168.20.28");
        spi.setIpFinder(tcp);
        cfg.setDiscoverySpi(spi);

        Ignite start = Ignition.start(cfg);
        IgniteCompute compute = start.compute();
        IgniteCluster cluster = start.cluster();
        ClusterGroup clusterGroup = cluster.forRemotes();
        ClusterGroup clusterGroup1 = clusterGroup.forCacheNodes("");

        compute.broadcast(() -> System.out.println("小喇叭开始广播了！ "));

        compute.execute(new ComputeLengthTask(), "重庆 工程 学院， 天天下雨啊！");

        start.close();

    }

}


