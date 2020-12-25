package com.kang.kafka.producer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

/**
 * Tree生成工具类
 * @Author jiwei.kang
 * @Date 2020/12/24
 */

@Setter
@Getter
@ToString
public class TreeUtils implements TreeNode {

    private String id;
    private String name;
    private String parent;
    private String children;

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        return null;
    }
}
