package com.haocang.patrol.manage.bean;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {
    private String orgName;
    private String label;
    private Integer value;
    private boolean select = false;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
