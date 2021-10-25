package com.zf.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构
 *
 * @author lijianbin
 * @date 2021-09-17 10:21
 **/
public class TreeUtils {

    private TreeUtils() {
    }

    /**
     * 根据父节点的ID获取所有子节点
     */
    public static <T extends Tree> List<Tree> getChildPerms(List<T> list, int parentId) {
        List<Tree> returnList = new ArrayList<>();
        for (Tree t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private static <T extends Tree> void recursionFn(List<T> list, Tree t) {
        // 得到子节点列表
        List<Tree> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Tree tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private static <T extends Tree> List<Tree> getChildList(List<T> list, Tree t) {
        List<Tree> tList = new ArrayList<>();
        for (Tree n : list) {
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tList.add(n);
            }
        }
        return tList;
    }

    /**
     * 判断是否有子节点
     */
    private static <T extends Tree> boolean hasChild(List<T> list, Tree t) {
        return !getChildList(list, t).isEmpty();
    }
}
