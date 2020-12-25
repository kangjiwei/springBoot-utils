package com.util.tree;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * 模板化Tree实体类。
 * 适应TreeUtils class。
 * </p>
 *
 * @author jiwei.kang
 * @Date 2020/12/25
 */
public class TreeNode<T> {

    Object getRoot;
    String id;
    String pid;
    List<T> children;

    public Object getGetRoot() {
        return getRoot;
    }

    public void setGetRoot(Object getRoot) {
        this.getRoot = getRoot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
