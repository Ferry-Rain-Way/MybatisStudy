import com.nfjh.DaoInterface;
import org.junit.Test;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Javassist {
    @Test
    public void testGenerateDaoImpl()throws Exception{
        // 获取类池
        ClassPool pool = ClassPool.getDefault();
        //创建类
        CtClass ctClass = pool.makeClass("com.nfjh.DaoImpl");
        //创建接口
        CtClass ctInterface = pool.makeClass("com.nfjh.DaoInterface");
        //实现接口
        ctClass.addInterface(ctInterface);
        //实现类实现接口中的所有方法
        MethodImpl(ctClass);
        // 在内存中生成class，并且加载到JVM当中
        Class<?> clazz = ctClass.toClass();
        //创建对象
        DaoInterface di = (DaoInterface) clazz.newInstance();
        //调用方法
        int act111插入 = di.insert("act111插入");

    }

    public void MethodImpl(CtClass ctClass) throws Exception{

        Method[] methods = DaoInterface.class.getDeclaredMethods();
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
                    methodCode.append(parameterTypes[i].getSimpleName() + " args" + i);
                    if (i!=parameterTypes.length-1) {
                        methodCode.append(",");//形参列表分隔符,
                    }
                }
                //)
                methodCode.append("){");
                //函数体
                methodCode.append("System.out.println(\"methodBody Excute!\");");
                //返回语句  返回值是否存在
                String returnType = method.getReturnType().getSimpleName();
                if (!returnType.equals("void")) {
                    //在DaoInterface中只写了这两种 int & String
                    if (returnType.equals("int")) {
                        methodCode.append("return 0;");
                    }else if (returnType.equals("String")){
                        methodCode.append("return \"hello world!\";");
                    }
                }

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




    @Test
    public  void  testJavassist() throws    Exception {
            // 获取类池
            ClassPool pool = ClassPool.getDefault();
            // 创建类
            CtClass ctClass = pool.makeClass("com.powernode.javassist.Test");
            // 创建方法
            // 1.返回值类型 2.方法名 3.形式参数列表 4.所属类
            CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, ctClass);
            // 设置方法的修饰符列表
                ctMethod.setModifiers(Modifier.PUBLIC);
            // 设置方法体
                ctMethod.setBody("{System.out.println(\"hello world\");}");
            // 给类添加方法
                ctClass.addMethod(ctMethod);
            // 调用方法
            Class<?> aClass = ctClass.toClass();
            Object o = aClass.newInstance();
            Method method = aClass.getDeclaredMethod("execute");
                method.invoke(o);

    }
}
