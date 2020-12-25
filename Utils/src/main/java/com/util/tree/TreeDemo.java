package com.util.tree;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class TreeDemo extends TreeNode<TreeDemo> {

    private String name;
    private String root;

    public TreeDemo(String id, String pid, List<TreeDemo> children, String name,String root) {
        super.id = id;
        super.pid = pid;
        super.children = children;
        this.name = name;
        this.root = root;
    }



}
