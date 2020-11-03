package com.haocang.base.utils;

import android.text.TextUtils;
import android.util.Log;

import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;

import org.java_websocket.WebSocket;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

public class WebSocketUtil {

    private static WebSocketUtil websocketUtil = null;
    public StompClient mStompClient;

    public static WebSocketUtil getInstance() {
        if (null == websocketUtil) {
            synchronized (WebSocketUtil.class) {
                if (null == websocketUtil) {
                    websocketUtil = new WebSocketUtil();
                }
            }
        }
        return websocketUtil;
    }


    /**
     * 创建websocket链接
     */
    public void createStompClient() {
        if (mStompClient == null) {
            Map<String, String> map = new HashMap<>();
            map.put("Cookie", LibConfig.getCookie());
            String address = LibConstants.WS_ADDRESS_IP;
            if (!TextUtils.isEmpty(AppApplication.getInstance().getUserEntity().getWebsocketAddress())) {
                String wsAddress = "";
                if (address.contains("https")) {
                    wsAddress = address.replace("https", "wss");
                } else if (address.contains("http")) {
                    wsAddress = address.replace("http", "wss");
                }
                address = wsAddress + "/websocket/websocket";
            }
            mStompClient = Stomp.over(WebSocket.class, address, map);
            mStompClient.connect();
            mStompClient.lifecycle().subscribe(new Action1<LifecycleEvent>() {
                @Override
                public void call(LifecycleEvent lifecycleEvent) {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.v("websocket", "连接成功");
                            break;
                        case ERROR:
                            Log.v("websocket", "error", lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            Log.v("websocket", "close", lifecycleEvent.getException());
                            break;
                    }
                }
            });
        }

    }

    public void registerStompTopic(String path, OnRegisterStompResult onRegisterStompResult) {
        if (mStompClient != null) {
            mStompClient.topic(path).subscribe(new Action1<StompMessage>() {
                @Override
                public void call(StompMessage stompMessage) {
                    onRegisterStompResult.setStompResult(stompMessage);
                    Log.i("sssss", (stompMessage.getStompCommand() + "====" + stompMessage.getPayload()));
                }
            });
        }
    }


    /**
     * 断开链接
     */
    public void closeClient() {
        if (mStompClient != null) {
            mStompClient.disconnect();
        }
    }

    private OnRegisterStompResult onRegisterStompResult;

    public interface OnRegisterStompResult {
        void setStompResult(StompMessage stompMessage);
    }
}
