/**
 * Copyright &copy; 2017-2027 hcsystem All rights reserved.
 */
package com.haocang.patrol.patrolinhouse.bean;


/**
 * 巡检步骤Entity
 * @author zhangfang
 * @version 2018-03-13
 */
public class PatrolStep {
	private static final long serialVersionUID = 1L;

	//id没有生成,暂时手写
//	@ApiModelProperty(value="ID, 修改时为必填")
    private Integer id;
	
	//@NotNull(message="patrolStep_patrolPointIdIsNotNull", groups = { UpdGroup.class })
//	@ApiModelProperty(value = "关联的巡检点")
	private Integer patrolPointId;
	
//	@NotBlank(message="patrolStep_stepNotBlank", groups = { AddGroup.class, UpdGroup.class })
//	@ApiModelProperty(value = "巡检步骤名称")
	private String step;
	
//	@NotNull(message="patrolStep_resultTypeIsNotNull", groups = { AddGroup.class, UpdGroup.class })
//	@ApiModelProperty(value = "巡检步骤结果类型：1状态，2数据")
	private Integer resultType;			
	
//	@NotNull(message="patrolStep_stateNotNull")
//	@ApiModelProperty(value = "巡检步骤做逻辑删除: -1为已删除")
	private Integer state;
	
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getPatrolPointId() {
		return patrolPointId;
	}

	public void setPatrolPointId(Integer patrolPointId) {
		this.patrolPointId = patrolPointId;
	}
	
	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
	
	public Integer getResultType() {
		return resultType;
	}

	public void setResultType(Integer resultType) {
		this.resultType = resultType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}