package com.util.tree;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.tree.Tree;

import javax.swing.tree.TreeNode;
import java.util.*;

public class Test {
    public static void main(String[] args) {

        List<TreeDemo> allTree = Arrays.asList(
                new TreeDemo("1", "0", null, "one","0"),
                new TreeDemo("6", "0", null, "two","0"),
                new TreeDemo("2", "1", null, "three","0"),
                new TreeDemo("3", "2", null, "four","0"),
                new TreeDemo("4", "1", null, "five","0"),
                new TreeDemo("5", "4", null, "six","0")
        );
        List<TreeDemo> treeUtils = new ArrayList<>();
        for (TreeDemo utils : allTree) {
            if (utils.getPid().equals(utils.getRoot())) {
                utils.setChildren(TreeUtils.findChildren(allTree, utils));
                treeUtils.add(utils);
            }
        }
        String s = JSON.toJSONString(treeUtils);
        System.out.println(s);
    }
}
