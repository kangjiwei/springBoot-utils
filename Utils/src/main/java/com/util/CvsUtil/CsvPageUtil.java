package com.util.CvsUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jiwei.kang
 * @date 2021.3.10
 */

@Slf4j
public class CsvPageUtil<T> {

    private List<T> allDatas;

    public CsvPageUtil(List<T> allDatas) {
        this.allDatas = allDatas;
    }

    /**
     * 获取数据总数
     */
    public Integer getTotalNum() {
        return allDatas.size();
    }

    /**
     * 分页查询
     *
     * @param curPage
     * @param pageSize
     * @return
     */
    public List<T> limit(Integer curPage, Integer pageSize) {
        Integer totalPage = this.getTotalPage(pageSize);
        log.debug("一共有[{}]页", totalPage);
        if (curPage >= totalPage) {
            curPage = totalPage;
        }
        Integer startCord = this.getStartCord(curPage, pageSize);
        Integer endCord = this.getEndCord(startCord, pageSize);
        log.debug("开始条数:[{}],结束条数:[{}]", startCord, endCord);
        return allDatas.subList(startCord, endCord);
    }


    /**
     * 获取总页数
     * 如果只有7条数据，pageSize为10条，则只有一页.
     */
    public Integer getTotalPage(Integer pageSize) {
        Integer totalNum = this.getTotalNum();
        Integer totalPage = totalNum / pageSize;
        Integer isMorePage = totalNum % pageSize;
        if (isMorePage > 0) {
            totalPage++;
        }
        return totalPage;
    }


    /**
     * 获取开始条数
     *
     * @return
     */
    private Integer getStartCord(Integer curPage, Integer pageSize) {
        Integer totalNum = this.getTotalNum();
        return (curPage - 1) * pageSize;
    }

    /**
     * 获取结束条数
     *
     * @return
     */
    private Integer getEndCord(Integer startCord, Integer pageSize) {
        Integer totalNum = this.getTotalNum();
        log.debug("数据一共有 {} 条", totalNum);
        Integer endCord = startCord + pageSize;
        if (endCord >= totalNum) {
            return totalNum;
        }
        return endCord;
    }
}
