package com.bailuyiting.commons.until;

import com.bailuyiting.commons.core.domain.AbstractSuperEntity;

import java.lang.reflect.Field;
import java.util.Map;

public class FeignUntils {
    /**
     * 当Feign发送失败，自动实例化对象并设置feignSuccess为false
     * @param t
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    public static Object feignFailure(Class t) {
        try {
            Object o = t.newInstance();
            Field feignSuccess = t.getDeclaredField("feignSuccess");
            feignSuccess.setAccessible(true);
            feignSuccess.set(o,false);
            return o;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 判断feign成功
     * @param entity
     * @param method
     */
    public static void isSuccess(AbstractSuperEntity entity, String method){
        if(!entity.getFeignSuccess()){
            StringBuilder builder = new StringBuilder();
            builder.append("Feign:")
                    .append(method)
                    .append("失败");
            throw new RuntimeException(builder.toString());
        }
    }

    /**
     * feign返回结果 如果是null则新建
     * @param entity
     * @return
     */
    public static <T extends AbstractSuperEntity>AbstractSuperEntity feignResult(AbstractSuperEntity entity,Class<T> c){
        if(entity==null){
            AbstractSuperEntity en=null;
            try {
                en=c.newInstance();
                en.setFeignSuccess(true);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            return en;
        }
        entity.setFeignSuccess(true);
        return entity;
    }

}
