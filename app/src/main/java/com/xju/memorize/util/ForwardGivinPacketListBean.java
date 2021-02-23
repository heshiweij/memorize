package com.xju.memorize.util;

import java.util.List;

public class ForwardGivinPacketListBean {


    /**
     * total
     */
    private Integer total;
    /**
     * pageSize
     */
    private Integer pageSize;
    /**
     * pageNum
     */
    private Integer pageNum;
    /**
     * totalPage
     */
    private Integer totalPage;
    /**
     * list
     */
    private List<ListBean> list;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id
         */
        private Integer id;
        /**
         * reward
         */
        private String reward;
        /**
         * isReceived
         */
        private Boolean isReceived;
        /**
         * isExpired
         */
        private Boolean isExpired;
        /**
         * createTime
         */
        private String createTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public Boolean isIsReceived() {
            return isReceived;
        }

        public void setIsReceived(Boolean isReceived) {
            this.isReceived = isReceived;
        }

        public Boolean isIsExpired() {
            return isExpired;
        }

        public void setIsExpired(Boolean isExpired) {
            this.isExpired = isExpired;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
