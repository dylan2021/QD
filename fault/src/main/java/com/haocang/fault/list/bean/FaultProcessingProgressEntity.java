package com.haocang.fault.list.bean;

import com.haocang.fault.R;
import com.haocang.fault.list.presenter.FaultProcessingProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/5/318:34
 * 修 改 者：
 * 修改时间：
 */
public class FaultProcessingProgressEntity {

    /**
     * id : 1
     * currState : 2
     * currStateName : 挂起
     * processingProgressList :
     */

    private int id;
    private int currState;
    private String currStateName;
    private List<FaultProcessingProgressEntity> mList;

    public List<FaultProcessingProgressEntity> getmList() {
        return mList;
    }

    public void setmList(List<FaultProcessingProgressEntity> mList) {
        this.mList = mList;
    }

    /**
     * faultRecordId : 8
     * time : 2018-05-09 21:01:53
     * processingPersonName : 开发管理员
     * processingResult : 挂起
     */

    private int faultRecordId;
    private String time;
    private String processingPersonName;
    private String processingResult;
    private String nextProcessingPersonName;
    private int processResult;

    private String repaired;
    private int faultContrailId;

    private List<FaultProcessingProgressEntity> processingProgressList;

    public String getRepaired() {
        return repaired == null ? "" : repaired;
    }

    public void setRepaired(String repaired) {
        this.repaired = repaired;
    }

    public int getFaultContrailId() {
        return faultContrailId;
    }

    public void setFaultContrailId(int faultContrailId) {
        this.faultContrailId = faultContrailId;
    }


    public int getProcessResult() {
        return processResult;
    }

    public void setProcessResult(int processResult) {
        this.processResult = processResult;
    }


    public String getNextProcessingPersonName() {
        if (nextProcessingPersonName != null) {
            nextProcessingPersonName = nextProcessingPersonName.replaceAll("null", "");
            return nextProcessingPersonName;
        } else {
            return "";
        }
    }

    public void setNextProcessingPersonName(String nextProcessingPersonName) {
        this.nextProcessingPersonName = nextProcessingPersonName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrState() {
        return currState;
    }

    public void setCurrState(int currState) {
        this.currState = currState;
    }

    public String getCurrStateName() {
        return currStateName;
    }

    public void setCurrStateName(String currStateName) {
        this.currStateName = currStateName;
    }

    public int getFaultRecordId() {
        return faultRecordId;
    }

    public void setFaultRecordId(int faultRecordId) {
        this.faultRecordId = faultRecordId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProcessingPersonName() {
        return processingPersonName;
    }

    public void setProcessingPersonName(String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }

    public String getProcessingResult() {
        return processingResult == null ? "" : processingResult;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }

    public String getNewState() {
        if (currState == 0) {
            return "未处理";
        } else if (currState == 1) {
            return "执行中";
        } else if (currState == 2) {
            return "挂起";
        } else if (currState == 3) {
            return "关闭";
        } else if (currState == 4) {
            return "完成";
        } else if (currState == 5) {
            return "未分配";
        }
        return "";
    }

    public int getBackgroundResource() {
        if (currState == 0) {
            return R.drawable.ic_untreated;
        } else if (currState == 1) {
            return R.drawable.ic_fault_in_processing;
        } else if (currState == 2) {
            return R.drawable.ic_hangup;
        } else if (currState == 3) {
            return R.drawable.ic_close;
        } else if (currState == 4) {
            return R.drawable.ic_complete;
        } else {
            return R.drawable.ic_distribution;
        }
    }

    public String getNewStateColor() {
        if (currState == 0) {
            return "#f99a6b";
        } else if (currState == 1) {
            return "#efb336";
        } else if (currState == 2) {
            return "#125fa5";
        } else if (currState == 3) {
            return "#e35c5c";
        } else if (currState == 4) {
            return "#0cabdf";
        }
        return "#000000";
    }

    public List<FaultProcessingProgressEntity> getProcessingProgressList() {
        return processingProgressList;
    }

    public List<FaultProcessingProgressEntity> getNewProcessingProgressList() {
        List<FaultProcessingProgressEntity> list = new ArrayList<>();
        if (processingProgressList != null) {
            for (int i = processingProgressList.size() - 1; i >= 0; i--) {
                list.add(processingProgressList.get(i));
            }
        }
        return list;
    }

    public void setProcessingProgressList(List<FaultProcessingProgressEntity> processingProgressList) {
        this.processingProgressList = processingProgressList;
    }
}
