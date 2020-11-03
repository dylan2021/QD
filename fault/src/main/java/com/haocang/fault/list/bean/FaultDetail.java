package com.haocang.fault.list.bean;

import org.json.JSONArray;

import java.util.List;

public class FaultDetail {
    private FaultManagerEntity faultDto;
    private List<?> faultRemoveRecordDtos;

    public FaultManagerEntity getFaultDto() {
        return faultDto;
    }

    public void setFaultDto(FaultManagerEntity faultDto) {
        this.faultDto = faultDto;
    }

    public List<?> getFaultRemoveRecordDtos() {
        return faultRemoveRecordDtos;
    }

    public void setFaultRemoveRecordDtos(List<?> faultRemoveRecordDtos) {
        this.faultRemoveRecordDtos = faultRemoveRecordDtos;
    }
}
