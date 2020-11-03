package com.haocang.patrol.patrolinhouse.bean;

import com.haocang.base.config.AppApplication;
import com.haocang.base.utils.TimeUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatrolTaskDetailDTO {

    //巡检任务状态
    public static final String EXECUTE_STATUS_UNALLOCATED = "unallocated";
    public static final String EXECUTE_STATUS_TOBEEXECUTED = "toBeExecuted";
    public static final String EXECUTE_STATUS_EXECUTING = "executing";
    public static final String EXECUTE_STATUS_FINISHED = "finished";
    public static final String EXECUTE_STATUS_ABNORMAL = "abnormal";
    public static final String EXECUTE_STATUS_INTERRUPT = "interrupt";

    private Integer id;

    private Integer patrolPlanId;

    private String name;

    private String executeStatus;

    private String type;

    private Integer executorId;

    private String executorName;

    private Integer orgId;

    private String orgName;

    private Date executeDate;

    private Date startTime;

    private Date endTime;

    private Integer validPathLength;

    private Integer actualPathLength;

    private Date actualStartTime;

    private Date actualEndTime;

    private Integer abnormalCount;

    private Integer inspectedCount;

    private List<PatrolPointDetailDTO> patrolPointDetailDTOs;

    //private List<PatrolTaskPointStep> patrolTaskPointSteps;

    private List<PatrolActualPath> patrolActualPath;

    private List<PatrolPlanPath> patrolTaskPlanPaths; //patrolTaskPlanPaths

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatrolPlanId() {
        return patrolPlanId;
    }

    public void setPatrolPlanId(Integer patrolPlanId) {
        this.patrolPlanId = patrolPlanId;
    }

    public String getName() {
        return name;
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
        return executorName;
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

    public Integer getValidPathLength() {
        return validPathLength;
    }

    public void setValidPathLength(Integer validPathLength) {
        this.validPathLength = validPathLength;
    }

    public Integer getActualPathLength() {
        return actualPathLength;
    }

    public void setActualPathLength(Integer actualPathLength) {
        this.actualPathLength = actualPathLength;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public Integer getAbnormalCount() {
        return abnormalCount;
    }

    public void setAbnormalCount(Integer abnormalCount) {
        this.abnormalCount = abnormalCount;
    }

    public Integer getInspectedCount() {
        return inspectedCount;
    }

    public void setInspectedCount(Integer inspectedCount) {
        this.inspectedCount = inspectedCount;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<PatrolPointDetailDTO> getPatrolPointDetailDTOs() {
        return patrolPointDetailDTOs;
    }

    public void setPatrolPointDetailDTOs(final List<PatrolPointDetailDTO> patrolPointDetailDTOs) {
        this.patrolPointDetailDTOs = patrolPointDetailDTOs;
    }

    public List<PatrolActualPath> getPatrolActualPath() {
        return patrolActualPath;
    }

    public void setPatrolActualPath(final List<PatrolActualPath> patrolActualPath) {
        this.patrolActualPath = patrolActualPath;
    }


    /**
     * 获取巡检倒计时
     *
     * @return
     */
    public String getTimeCountdown() {
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(endTime);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(executeDate);
        String timeCountdown = "";
        Date date = new Date();
        /**
         * 已经开始了
         */
        if (date.after(timeCalendar.getTime())) {
            timeCountdown = AppApplication.getContext().getString(R.string.patrol_is_finish);
        } else {
            timeCountdown = TimeUtil.getDuringTwo(date, timeCalendar.getTime());
        }
        return timeCountdown;
    }

    /**
     * 获取巡检点个数
     *
     * @return
     */
    public Integer getPointCount() {
        return patrolPointDetailDTOs != null ? patrolPointDetailDTOs.size() : 0;
    }

    /**
     * 是否已开始
     *
     * @return
     * @1/状态为执行中
     * @2/当前时间在开始时间和结束之间
     */
    public boolean isStart() {
        boolean isStart = false;
        if (EXECUTE_STATUS_EXECUTING.equals(getExecuteStatus())) {
            isStart = true;
        }
        return isStart;
    }

    /**
     * @return
     */
    public Date getStartTimeFormat() {
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(startTime);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(executeDate);
//        timeCalendar.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR));
//        timeCalendar.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH));
//        timeCalendar.set(Calendar.DAY_OF_YEAR, dateCalendar.get(Calendar.DAY_OF_YEAR));
        return timeCalendar.getTime();
    }

    /**
     * @return
     */
    public Date getEndTimeFormat() {
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(endTime);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(executeDate);
//        timeCalendar.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR));
//        timeCalendar.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH));
//        timeCalendar.set(Calendar.DAY_OF_YEAR, dateCalendar.get(Calendar.DAY_OF_YEAR));
        return timeCalendar.getTime();
    }

    public String getShowStatus() {
        Map<String, String> statusMap;
        String[] patrolStatusLabelArray = AppApplication.getContext().getResources().getStringArray(R.array.patrol_status_labels_all);
        String[] patrolStatusKeysArray = AppApplication.getContext().getResources().getStringArray(R.array.patrol_status_keys_all);
        statusMap = new HashMap<>();
        for (int i = 0; i < patrolStatusLabelArray.length && i < patrolStatusKeysArray.length; i++) {
            statusMap.put(patrolStatusKeysArray[i], patrolStatusLabelArray[i]);
        }
        return statusMap.get(getExecuteStatus());
    }

    /**
     * 是否可开始
     * 1.状态为执行中，执行人是当前人
     * 2.状态为未分配，但是在开始时间两小时之内
     *
     * @return
     */
    public boolean canStart() {
        boolean canStart = false;
        Date start = getStartTimeFormat();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, 2);
        Date twoHourLater = c.getTime();
        if (getExecutorId() != null
                && AppApplication.getInstance().getUserEntity().getId() == getExecutorId()) {
            if (PatrolTaskListDTO.TO_BE_EXECUTED.equals(getExecuteStatus())
                    && twoHourLater.after(start)) {
                canStart = true;
            } else if (PatrolTaskListDTO.EXECUTING.equals(getExecuteStatus())) {
                canStart = true;
            }
        }

        return canStart;
    }

    public List<PatrolPlanPath> getPatrolTaskPlanPaths() {
        return patrolTaskPlanPaths;
    }

    public void setPatrolTaskPlanPaths(List<PatrolPlanPath> patrolTaskPlanPaths) {
        this.patrolTaskPlanPaths = patrolTaskPlanPaths;
    }
}
