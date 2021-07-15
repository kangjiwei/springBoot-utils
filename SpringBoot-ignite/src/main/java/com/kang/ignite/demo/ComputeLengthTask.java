package com.kang.ignite.demo;

import com.kang.ignite.groovy.GroovyUtil;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ComputeLengthTask extends ComputeTaskAdapter<String,Integer> {


    @Override
    public Map<? extends ComputeJob, ClusterNode> map(List<ClusterNode> subgrid, String arg) {

        String path = "./src/groovy/GMath.groovy";
        Optional<GroovyObject> groovyObject = GroovyUtil.getGroovyObject(path);

        String[] words = arg.split(" ");

        Map<ComputeJob, ClusterNode> map = new HashMap<>(words.length);

        Iterator<ClusterNode> it = subgrid.iterator();

        for (final String word : words) {
            // If we used all nodes, restart the iterator.
            if (!it.hasNext())
                it = subgrid.iterator();

            ClusterNode node = it.next();
            map.put(new ComputeJobAdapter() {
                @Override public Object execute() {
                    GroovyShell groovyShell = new GroovyShell();
                    groovyShell.evaluate("println 'println the word:  "+word+"  and length: "+word.length()+" '");
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
