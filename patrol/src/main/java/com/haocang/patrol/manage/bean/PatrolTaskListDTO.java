package com.haocang.patrol.manage.bean;

import com.haocang.base.config.AppApplication;
import com.haocang.base.utils.TimeUtil;

import java.util.Date;

public class PatrolTaskListDTO {

    public static final String UNALLOCATED = "unallocated";
    public static final String TO_BE_EXECUTED = "toBeExecuted";
    public static final String EXECUTING = "executing";

    public PatrolTaskListDTO(String name, Integer executorId, String executorName) {
        this.name = name;
        this.executorId = executorId;
        this.executorName = executorName;
    }

    private Integer id;/*巡检ID*/

    private String name;/*巡检任务名称*/

    private String executeStatus;/*执行状态*/

    private String type;/*巡检类型*/

    private Integer executorId;    /*巡检执行人ID*/

    private String executorName;/*巡检执行人名称*/

    private Date executeDate;/*执行时间*/

    private Date startTime;    /*巡检开始时间*/

    private Date endTime;    /*巡检结束时间*/
    private Integer orgId;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    private String no;/**/

    private Integer patrolPointCount;/*巡检点数量*/

    private Integer inspectedCount;/*已检查项数量*/

    private Integer abnormalCount;/*异常数量*/

    public boolean isConcerned() {
        return concerned;
    }

    public void setConcerned(boolean concerned) {
        this.concerned = concerned;
    }

    private boolean concerned;//是否关注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 任务名称字数限制
     */
    private final int taskNameLimitCount = 14;

    public String getName() {
        String taskName = name;
        if (taskName != null && taskName.length() > taskNameLimitCount) {
            taskName = taskName.substring(0, taskNameLimitCount);
        }
        return taskName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Integer executorId) {
        this.executorId = executorId;
    }

    public String getExecutorName() {
        return executorName != null ? executorName : "";
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getPatrolPointCount() {
        return patrolPointCount == null ? 0 : patrolPointCount;
    }

    public void setPatrolPointCount(Integer patrolPointCount) {
        this.patrolPointCount = patrolPointCount;
    }

    public Integer getInspectedCount() {
        return inspectedCount == null ? 0 : inspectedCount;
    }

    public void setInspectedCount(Integer inspectedCount) {
        this.inspectedCount = inspectedCount;
    }

    public Integer getAbnormalCount() {
        return abnormalCount;
    }

    public void setAbnormalCount(Integer abnormalCount) {
        this.abnormalCount = abnormalCount;
    }

    public boolean isOutsidePatrol() {
        boolean isOutside = false;
        if (type != null && "outside".toLowerCase().equals(type.toLowerCase())) {
            isOutside = true;
        }
        return isOutside;
    }

    private final double nh = 1000 * 60 * 60;

    /**
     * 获取显示时间.
     *
     * @return
     */
    public String getShowTime() {
        String showTime = "";
        if (startTime != null) {
            showTime += TimeUtil.getDateStrHHmm(startTime);
        }
        showTime += "-";
        if (endTime != null) {
            showTime += TimeUtil.getDateStrHHmm(endTime);
        }
        return showTime;
    }

    /**
     * 获取显示日期.
     *
     * @return
     */
    public String getShowDate() {
        String showDate = "";
        if (executeDate != null) {
            showDate = TimeUtil.getDayStr(executeDate);
        }
        return showDate;
    }

    /**
     * 是否可分配.
     *
     * @return
     */
    public boolean canAllocate() {
        boolean canAllocate = false;
        if (PatrolTaskListDTO.UNALLOCATED.equals(getExecuteStatus())) {
            canAllocate = true;
        }
        if ((PatrolTaskListDTO.TO_BE_EXECUTED.equals(getExecuteStatus())||PatrolTaskListDTO.EXECUTING.equals(getExecuteStatus()))
                && getExecutorId() == AppApplication.getInstance().getUserEntity().getId()) {
            canAllocate = true;
        }
        return canAllocate;
    }
}
