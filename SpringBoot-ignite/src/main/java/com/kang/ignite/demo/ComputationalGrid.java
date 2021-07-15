package com.kang.ignite.demo;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.Test;

import java.util.function.Function;

/**
 * 计算网格
 */
public class ComputationalGrid {

    final Ignite ignite = Ignition.ignite();

    @Test
    public void broadCast() {

        IgniteConfiguration  cfg = new IgniteConfiguration();
        //节点
        IgniteCluster cluster = ignite.cluster();
        //所有集群组
        ClusterGroup clusterGroup =cluster.forRemotes();

        IgniteCompute compute = ignite.compute(clusterGroup);

        compute.broadcast(() -> System.out.println("Hello Node: " + Ignition.localIgnite().cluster().localNode().id()));

    }

}
