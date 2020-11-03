package com.haocang.base.utils;

import android.content.Context;

import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：//添加关注
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/5/417:58
 * 修 改 者：
 * 修改时间：
 */
public class ConcernUtil {
    private Context ctx;
    /**
     * 关注id
     */
    private int concernId;
    /**
     * 关注模块类型
     */
    private String concernType;
    public AddConcernInterface concernSucess;
    /**
     * 台账
     */
    public static final String EQUIPMENT_TYPE = "equipment";
    public static final String PATROL_TYPE = "patrol";//巡检
    public static final String FAULT_TYPE = "fault";//缺陷
    public static final String REPAIR_TYPE = "repair";//维修
    public static final String MAINTAIN_TYPE = "maintain";//保养
    public static final String M_POINT = "mpoint";//测点


    public ConcernUtil setContexts(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    /**
     * 关注id
     *
     * @param concernId
     * @return
     */
    public ConcernUtil setConcernId(int concernId) {
        this.concernId = concernId;
        return this;
    }

    /**
     * g关注类型
     *
     * @param type
     * @return
     */
    public ConcernUtil setConcernType(String type) {
        concernType = type;
        return this;
    }


    public ConcernUtil setConcernSuccess(AddConcernInterface concernSucess) {
        this.concernSucess = concernSucess;
        return this;
    }

    /**
     * 添加关注
     */
    public void addConcern() {
        AddParameters addParameters = new AddParameters();
        Map<String, Object> map = new HashMap<>();
        map.put("concernId", concernId);
        map.put("type", concernType);
        addParameters.addParam(map);
        new OkHttpClientManager()
                .setLoadDialog(new ProgressBarDialog(ctx))
                .setUrl(MethodConstants.Base.BASE_CONCERN)
                .setOnNetWorkReponse(onNetworkResponse)
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(addParameters.formBody())
                .builder();
    }

    /**
     * 取消关注
     */
    public void abolishConcern() {
        AddParameters addParameters = new AddParameters();
        Map<String, Object> map = new HashMap<>();
        map.put("concernIds", concernId);
        map.put("concernId", concernId);
        map.put("type", concernType);
        addParameters.addParam(map);
        new OkHttpClientManager()
                .setLoadDialog(new ProgressBarDialog(ctx))
                .setUrl(MethodConstants.Base.BASE_CONCERN + addParameters.formGet())
                .setOnNetWorkReponse(canclerConcern)
                .setRequestMethod(LibConfig.HTTP_DELETE)
//                .setRequestBody(addParameters.formBody())
                .builder();
    }

    OkHttpClientManager.OnNetworkResponse onNetworkResponse = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int id = jsonObject.optInt("id");
                if (id > 0) {
                    ToastUtil.makeText(ctx, "添加关注成功");
                }
                if (concernSucess != null) {
                    concernSucess.concernSucess();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(Response response) {
        }
    };

    OkHttpClientManager.OnNetworkResponse canclerConcern = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(String result) {
            if (concernSucess != null) {
                concernSucess.abolishConcern();
            }
            ToastUtil.makeText(ctx, "取消关注成功");
        }

        @Override
        public void onErrorResponse(Response response) {

        }
    };

    public interface AddConcernInterface {

        void concernSucess();

        void concernError();

        void abolishConcern();
    }

}
