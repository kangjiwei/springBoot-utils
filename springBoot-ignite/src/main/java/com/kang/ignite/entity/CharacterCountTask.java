package com.kang.ignite.entity;

import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobAdapter;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CharacterCountTask extends ComputeTaskAdapter<String, Integer> {

    // 1. Splits the received string into to words
    // 2. Creates a child job for each word
    // 3. Sends created jobs to other nodes for processing.

    @Override
    public Map<? extends ComputeJob, ClusterNode> map(List<ClusterNode> subgrid, String arg) {
        String[] words = arg.split(" ");

        Map<ComputeJob, ClusterNode> map = new HashMap<>(words.length);

        Iterator<ClusterNode> it = subgrid.iterator();

        for (final String word : arg.split(" ")) {
            // If we used all nodes, restart the iterator.
            if (!it.hasNext())
                it = subgrid.iterator();

            ClusterNode node = it.next();
            map.put(new ComputeJobAdapter() {
                @Override public Object execute() {
                    System.out.println(">>> Printing '" + word + "' on this node from grid job. "+ word.length());
                    // Return number of letters in the word.
                    return word.length();
                }
            }, node);
        }
        return map;
    }

    @Override
    public Integer reduce(List<ComputeJobResult> results) {
        int sum = 0;

        for (ComputeJobResult res : results)
            sum += res.<Integer>getData();

        return sum;
    }

}
