package com.kang.ignite.demo;

import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MyComputeTask implements ComputeTask<ClusterNode,Object> {


    @Override
    public @NotNull Map<? extends ComputeJob, ClusterNode> map(List<ClusterNode> subgrid, @Nullable ClusterNode arg) throws IgniteException {

        return null;
    }

    @Override
    public ComputeJobResultPolicy result(ComputeJobResult res, List rcvd) throws IgniteException {
        return null;
    }

    @Nullable
    @Override
    public Object reduce(List list) throws IgniteException {
        return null;
    }


}
