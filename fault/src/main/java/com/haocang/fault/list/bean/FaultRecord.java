package com.haocang.fault.list.bean;

import com.haocang.base.bean.PictureEntity;

import java.util.List;

public class FaultRecord {

    /**
     * id : 424
     * processingPersonName : 芒果管理员
     * processingResult : 1
     * processingResultName : 处理中
     * faultReason : 进攻
     * faultCloseRenson : null
     * faultCloseRensonName : null
     * faultHangupRenson : null
     * faultHangupRensonName : null
     * imgUrl : ["http://kaifa.hc-yun.com:31018/group1/M00/16/2B/wKgCfl0RwDeAAYjbAADNtprwFv099.jpeg"]
     * imgThumbnailUrl : ["http://kaifa.hc-yun.com:31018/group1/M00/16/2B/wKgCfl0RwDeAQOZhAACSELOfgbQ318.png"]
     * pictureVideos : [{"type":0,"imgUrl":"http://kaifa.hc-yun.com:31018/group1/M00/16/2B/wKgCfl0RwDeAAYjbAADNtprwFv099.jpeg","thumbnailUrl":"http://kaifa.hc-yun.com:31018/group1/M00/16/2B/wKgCfl0RwDeAQOZhAACSELOfgbQ318.png","videoUrl":null}]
     * remark : 婆婆我泼猴
     * createDate : 2019-06-25T06:29:01Z
     * finishTime : null
     */

    private int id;
    private String processingPersonName;
    private int processingResult;
    private String processingResultName;
    private String faultReason;
    private String faultCloseRenson;
    private String faultCloseRensonName;
    private String faultHangupRenson;
    private String faultHangupRensonName;
    private String remark;
    private String createDate;
    private String finishTime;
    private List<String> imgUrl;
    private List<String> imgThumbnailUrl;
    private List<PictureEntity> pictureVideos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcessingPersonName() {
        return processingPersonName;
    }

    public void setProcessingPersonName(String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }

    public int getProcessingResult() {
        return processingResult;
    }

    public void setProcessingResult(int processingResult) {
        this.processingResult = processingResult;
    }

    public String getProcessingResultName() {
        return processingResultName;
    }

    public void setProcessingResultName(String processingResultName) {
        this.processingResultName = processingResultName;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }






    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<String> getImgThumbnailUrl() {
        return imgThumbnailUrl;
    }

    public void setImgThumbnailUrl(List<String> imgThumbnailUrl) {
        this.imgThumbnailUrl = imgThumbnailUrl;
    }


}
