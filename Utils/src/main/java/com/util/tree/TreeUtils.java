package com.util.tree;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TreeUtils<T> {


    /**
     * <p>如何闭环？</p>
     * <r>
     *   闭环实现as停止循环，此处的闭环没有children列表就会停止循环。
     *   return 实现闭环；上
     * </r>
     *
     * @param trees
     * @param demo
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> List<T > findChildren(List<T> trees, TreeNode demo) {
        List<T> list = null;
        for (T tree : trees) {
            if (tree.getPid().equals(demo.getId())) {
                list = tree.getChildren();
                if (CollectionUtils.isEmpty(list)) {
                    list = new ArrayList<>();
                }
                tree.setChildren(findChildren(trees, tree));
                list.add(tree);
            }
        }
        return list;
    }

}
