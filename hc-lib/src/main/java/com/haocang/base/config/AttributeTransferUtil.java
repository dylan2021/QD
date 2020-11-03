package com.haocang.base.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class AttributeTransferUtil {

    private static final AttributeTransferUtil service = new AttributeTransferUtil();

//    private final Logger LOG =LoggerFactory.getLogger(this.getClass());

    private AttributeTransferUtil(){}

    public static AttributeTransferUtil example(){
        return service;
    }

    public Object CloneAttribute(Object clone,Object beCloned){
        Field[] fieldClone = null;
        Field[] fieldBeCloned = null;
        Map<String,Field> map = new HashMap<String,Field>();
        try {
            Class<?> classClone = clone.getClass();
            Class<?> classBecloned = beCloned.getClass();

            fieldClone = classClone.getDeclaredFields();
            fieldBeCloned = classBecloned.getDeclaredFields();

            for(int t =0;t<fieldBeCloned.length;t++){
                map.put(fieldBeCloned[t].getName(), fieldBeCloned[t]);
            }

            for(int i=0;i<fieldClone.length;i++){
                String fieldCloneName = fieldClone[i].getName();
                Field fie = map.get(fieldCloneName);
                if(fie!=null){
                    Method method1 = classClone.getMethod(getMethodName(fieldCloneName));
                    Method method2 = classBecloned.getMethod(setMethodName(fieldCloneName),fie.getType());
                    method2.invoke(beCloned,method1.invoke(clone));
                }
            }
        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
        }finally{
            fieldClone = null;
            fieldBeCloned = null;
            map.clear();
        }
        return beCloned;
    }

    private String getMethodName(String fieldName){
        String head = fieldName.substring(0, 1).toUpperCase();
        String tail = fieldName.substring(1);
        return "get"+head+tail;
    }

    private String setMethodName(String fieldName){
        String head = fieldName.substring(0, 1).toUpperCase();
        String tail = fieldName.substring(1);
        return "set"+head+tail;
    }
}
