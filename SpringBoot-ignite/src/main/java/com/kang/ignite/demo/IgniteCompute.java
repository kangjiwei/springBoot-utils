package com.kang.ignite.demo;


import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.*;
import org.apache.ignite.lang.IgniteRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class IgniteCompute implements ComputeTask {


    @Override
    public @NotNull Map<? extends ComputeJob, ClusterNode> map(List subgrid, @Nullable Object arg) throws IgniteException {
        String[] strs = arg.toString().split(" ");
        for (Object grid:subgrid){

        }
        return null;
    }

    @Override
    public ComputeJobResultPolicy result(ComputeJobResult res, List rcvd) throws IgniteException {
        System.out.println(res.getException());
        return null;
    }

    @Nullable
    @Override
    public Object reduce(List list) throws IgniteException {
        return null;
    }
}
