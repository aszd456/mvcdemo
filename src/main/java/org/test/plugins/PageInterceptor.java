package org.test.plugins;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @ClassName PageInterceptor 自定义分页插件
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/3/23 9:38
 * @Version 1.0
 **/
@Intercepts(@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}
))
public class PageInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();//获取参数，4个
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        if(rowBounds!=RowBounds.DEFAULT){
            Executor executor = (Executor) invocation.getTarget();
            BoundSql boundSql = ms.getBoundSql(parameterObject);//拦截下来要执行的sql
            //反射
            Field additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            /**
             * 将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
             * 值为 false 则指示反射的对象应该实施 Java 语言访问检查;实际上setAccessible是启用和禁用访问安全检查的开关,
             * 并不是为true就能访问为false就不能访问 ；
             * 由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的
             */
            additionalParametersField.setAccessible(true);
            Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
            if (rowBounds instanceof PageRowBounds) {
                MappedStatement countMs = newMappedStatement(ms, Long.class);
                CacheKey countKey = executor.createCacheKey(countMs, parameterObject, RowBounds.DEFAULT, boundSql);
                String countSql = "select count(*) from (" + boundSql.getSql() + ") temp";
                BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
                Set<String> keySet = additionalParameters.keySet();
                for (String key : keySet) {
                    countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
                }
                List<Object> countQueryResult = executor.query(countMs, parameterObject, RowBounds.DEFAULT, (ResultHandler) args[3], countKey, countBoundSql);
                Long count = (Long) countQueryResult.get(0);
                ((PageRowBounds) rowBounds).setTotal(count);
            }
            CacheKey pageKey = executor.createCacheKey(ms, parameterObject, rowBounds, boundSql);
            pageKey.update("RowBounds");
            String pageSql = boundSql.getSql() + " limit " + rowBounds.getOffset() + "," + rowBounds.getLimit();
            BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameterObject);
            Set<String> keySet = additionalParameters.keySet();
            for (String key : keySet) {
                pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
            }
            List list = executor.query(ms, parameterObject, RowBounds.DEFAULT, (ResultHandler) args[3], pageKey, pageBoundSql);
            return list;
        }
        //不需要分页，直接返回结果
        return invocation.proceed();
    }

    private MappedStatement newMappedStatement(MappedStatement ms, Class<Long> longClass) {
        MappedStatement.Builder builder = new MappedStatement.Builder(
                ms.getConfiguration(), ms.getId() + "_count", ms.getSqlSource(), ms.getSqlCommandType()
        );
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), longClass, new ArrayList<>(0)).build();
        builder.resource(ms.getResource())
                .fetchSize(ms.getFetchSize())
                .statementType(ms.getStatementType())
                .timeout(ms.getTimeout())
                .parameterMap(ms.getParameterMap())
                .resultSetType(ms.getResultSetType())
                .cache(ms.getCache())
                .flushCacheRequired(ms.isFlushCacheRequired())
                .useCache(ms.isUseCache())
                .resultMaps(Collections.singletonList(resultMap));
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        return builder.build();
    }

}
