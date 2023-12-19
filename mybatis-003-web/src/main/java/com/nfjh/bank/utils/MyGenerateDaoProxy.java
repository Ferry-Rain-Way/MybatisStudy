package com.nfjh.bank.utils;

/**
 * @desc 动态生成接口的实现类
 * @author 小郭
 * @version 1.0
 * @since 1.0
 */
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MyGenerateDaoProxy {
    private static String utilName;
    private static SqlSession sqlSession;
    private static  Class daoInterface ;

    private MyGenerateDaoProxy(){}

    /**
     *
     * @param sqlS sqlsession对象
     * @param di  接口.class
     * @param un  SqlSessionUtil 工具类的全限定类名
     * @return  创建的实现类的对象 以Object返回
     *          所以在获取后需要转型
     */
    public static  Object generate(SqlSession sqlS, Class di,String un){
        sqlSession = sqlS;
        daoInterface = di;
        utilName = un;

        // 获取类池
        ClassPool pool = ClassPool.getDefault();
        //创建类
        CtClass ctClass = pool.makeClass(daoInterface.getName() + "Proxy");//"com.nfjh.DaoImpl"
        //创建接口
        CtClass ctInterface = pool.makeClass(daoInterface.getName());//"com.nfjh.DaoInterface"
        //实现接口
        ctClass.addInterface(ctInterface);

        Object obj = null;
        try{
            //实现类实现接口中的所有方法
            MethodImpl(ctClass,ctInterface);
            // 在内存中生成class，并且加载到JVM当中
            Class<?> clazz = ctClass.toClass();
            //创建对象
//            DaoInterface di = (DaoInterface) clazz.newInstance();
             obj = clazz.newInstance();

            //调用方法            //调用方法有程序员完成
//            int act111插入 = di.insert("act111插入");

        }catch(Exception e){
            e.printStackTrace();
        }
        return obj;


    }

    public static void MethodImpl(CtClass ctClass, CtClass ctInterface) throws Exception{
        Method[] methods = daoInterface.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            try{
                StringBuilder methodCode = new StringBuilder();
                //访问修饰符 接口都是public
                methodCode.append("public ");
                //返回值
                String typeName = method.getReturnType().getName();
                methodCode.append(typeName + " ");
                //函数名(
                String name = method.getName();
                methodCode.append(name + "(");
                //参数列表
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0;i<parameterTypes.length;i++){
                    methodCode.append(parameterTypes[i].getName() + " arg" + i);
                    if (i!=parameterTypes.length-1) {
                        methodCode.append(",");//形参列表分隔符,
                    }
                }
                //)
                methodCode.append("){");
                //函数体
                //动态拼接方法体
                String methodBody = getMethodBody(method);
                methodCode.append(methodBody);

                //}
                methodCode.append("}");
                System.out.println("->" +methodCode.toString());
                //将拼接好的字符串添加到方法
                CtMethod ctMethod =  CtMethod.make(methodCode.toString(), ctClass);
                //将实现的方法添加到类中
                ctClass.addMethod(ctMethod);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }

    public static String getMethodBody(Method method){
/*
    @Override
    public int updateByActno(Account act) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.update("accuntspace.updateByActno",act);
        sqlSession.commit();
        return count;
    }
*/
      //拼接方法体
        //㊟:sqlSession 需要是全限定类名
        //SqlSessionUtil也需要是全限定类名
        StringBuilder body = new StringBuilder();
        body.append("org.apache.ibatis.session.SqlSession sqlSession = "+utilName+".openSession();");
        //sqlId
        String sqlId = daoInterface.getName() +"." + method.getName();
        //sqlSession执行的方法类型
        SqlCommandType sqlCommandType = sqlSession.getConfiguration().getMappedStatement(sqlId).getSqlCommandType();
        //SqlCommandType是枚举类型 逐个判断
        if (sqlCommandType == SqlCommandType.INSERT) {

        }
        if (sqlCommandType == SqlCommandType.DELETE) {

        }
        if (sqlCommandType == SqlCommandType.UPDATE) {
                body.append("return sqlSession.update(\""+sqlId+"\",arg0);");
        }
        if (sqlCommandType == SqlCommandType.SELECT) {
            String returnType = method.getReturnType().getName();
            body.append("return ("+returnType+")sqlSession.selectOne(\""+sqlId+"\", arg0);");
        }
        return body.toString();
    }
    
}
