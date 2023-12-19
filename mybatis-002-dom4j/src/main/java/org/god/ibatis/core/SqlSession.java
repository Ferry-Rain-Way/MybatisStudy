package org.god.ibatis.core;

import org.god.ibatis.pojo.SqlMappedStatement;

import java.lang.reflect.Method;
import java.sql.*;

public class SqlSession {
    private SqlSessionFactory sqlSessionFactory;
    private Object obj;

    public SqlSession(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }


    //以插入、查询一个为案例进行讲解
    public int insert(String sqlId,Object pojo){
        int count = 0 ;
        try {
            Connection  conn = sqlSessionFactory.getTransaction().getConnection();
            SqlMappedStatement sqlMapSta = sqlSessionFactory.getMappedStatements().get(sqlId);
            //insert into t_user values(#{id},#{name},#{age})
            String oldSql = sqlMapSta.getSql();
            String newSql = oldSql.replaceAll("#\\{[a-zA-Z_0-9$]*}", "?");
            PreparedStatement ps = conn.prepareStatement(newSql);

            int index = 1;
            int fromIndex = 0;
            while (true){
                //查找参数名
                int paramStart = oldSql.indexOf("#", fromIndex);
                if (paramStart<0){//找不到#说明结束
                    break;
                }
                int paramEnd = oldSql.indexOf("}", fromIndex);
                String paramName = oldSql.substring(paramStart + 2, paramEnd).trim();

                //通过反射机制获取属性值
                String getMethodName = "get" + paramName.toUpperCase().charAt(0) + paramName.substring(1);
                Method method = pojo.getClass().getMethod(getMethodName);
                Object invoke = method.invoke(pojo);

                //给?传值
                ps.setString(index,invoke.toString());

                index++;
                fromIndex = paramEnd + 1;
            }

            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public Object selectOne(String sqlId ,Object param){
        try{
            Connection  conn = sqlSessionFactory.getTransaction().getConnection();
            SqlMappedStatement sqlMapSta = sqlSessionFactory.getMappedStatements().get(sqlId);
            //select * from t_user where id = #{id}
            String oldSql = sqlMapSta.getSql();
            String resultType = sqlMapSta.getResultType();
            String newSql = oldSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement ps = conn.prepareStatement(newSql);


                int paramStart = oldSql.indexOf("#", 0);
                int paramEnd = oldSql.indexOf("}", 0);
                String paramName = oldSql.substring(paramStart + 2, paramEnd).trim();

                //直接赋值
                ps.setString(1,param.toString());


            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //当前为SelectOne,所以ResultSet最多只有一个,不循环
                //封装结果集[resultType]
                Class<?> clazz = Class.forName(resultType);
                Object obj = clazz.newInstance();
                ResultSetMetaData rsmd = rs.getMetaData();
                //获取所有属性名
                for(int i=0;i<rsmd.getColumnCount();i++){
                    String catalogName = rsmd.getColumnName(i+1);
                    String setMethodName = "set" + catalogName.toUpperCase().charAt(0) + catalogName.substring(1);
                    Method method = clazz.getMethod(setMethodName, String.class);
                    method.invoke(obj,rs.getString(catalogName));
                }
                return obj;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //提交事务
    public void commit(){
        sqlSessionFactory.getTransaction().commit();
    }

    //回滚事务
    public void rollback(){
        sqlSessionFactory.getTransaction().rollback();
    }

    //关闭事务
    public void close(){
        sqlSessionFactory.getTransaction().close();
    }

}
