package com.kang.ignite.entity;

import org.apache.catalina.Lifecycle;
import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;

import static org.apache.ignite.lifecycle.LifecycleEventType.AFTER_NODE_START;
import static org.apache.ignite.lifecycle.LifecycleEventType.BEFORE_NODE_START;

public class MyLifecycleBean implements LifecycleBean {

    /**
     * 生命周期:
     *   BEFORE_NODE_START：Ignite节点的启动程序初始化之前调用
     *   AFTER_NODE_START：Ignite节点启动之后调用
     *   BEFORE_NODE_STOP：Ignite节点的停止程序初始化之前调用
     *   AFTER_NODE_STOP：Ignite节点停止之后调用
     *
     * @param lifecycleEventType
     * @throws IgniteException
     */
    @Override
    public void onLifecycleEvent(LifecycleEventType lifecycleEventType) throws IgniteException {
        if(lifecycleEventType.equals(BEFORE_NODE_START)){

        }
        if(lifecycleEventType.equals(AFTER_NODE_START)){

            System.out.println("\n 启动Ignite节点完成....... \n ");

        }

    }
}
