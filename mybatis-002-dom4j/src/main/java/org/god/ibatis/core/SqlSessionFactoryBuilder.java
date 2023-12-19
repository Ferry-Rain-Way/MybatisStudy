package org.god.ibatis.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.god.ibatis.datasource.GenericDataSource;
import org.god.ibatis.datasource.JNDIDataSource;
import org.god.ibatis.datasource.PooledDataSource;
import org.god.ibatis.datasource.UnPooledDataSource;
import org.god.ibatis.pojo.Const;
import org.god.ibatis.pojo.SqlMappedStatement;
import org.god.ibatis.transaction.Transaction;
import org.god.ibatis.transaction.impl.JdbcTransaction;
import org.god.ibatis.transaction.impl.ManagedTransaction;
import org.god.ibatis.util.Resources;

import java.io.InputStream;
import java.util.*;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactoryBuilder(){}



    /**
     * 获取SqlSessionFactory对象
     * 该方法主要功能是：读取godbatis核心配置文件，并构建SqlSessionFactory对象
     * @param in 指向核心配置文件的输入流
     * @return SqlSessionFactory对象
     */
    public SqlSessionFactory build(InputStream in){
        Transaction transaction = null;
         Map<String, SqlMappedStatement> mappedStatements = null;
        SqlSessionFactory factory = null;

        try {
            SAXReader reader  = new SAXReader();
            Document document = reader.read(in);

            Element environmentId = getEnvironmentId(document);
            // 解析配置文件，创建数据源对象
            GenericDataSource dataSource = getGenericDataSource(environmentId);
            // 解析配置文件，创建事务管理器对象
            transaction = getTransaction(environmentId,dataSource);

            // 解析配置文件，获取所有的SQL映射对象
            mappedStatements = getMappedStatements(document);
            // 将以上信息封装到SqlSessionFactory对象中

           // 返回
            factory  = new SqlSessionFactory(transaction,mappedStatements);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return factory;
    }


    /**
     *  获取godbatis-config.xml的environment的ID
//     * @param in 读取godbatis-config.xml文件的流
     * @return  单个environment标签中对应的内容
     * @throws DocumentException 文档异常
     */
    private Element getEnvironmentId(Document document) throws DocumentException {

        Element environments = (Element) document.selectSingleNode("/configuration/environments");
        String defaultId = environments.attributeValue("default");
         Element environment = (Element) document.selectSingleNode("/configuration/environments/environment[@id='"+defaultId+"']");
        return environment;
    }


    /**
     * 获取数据源
     * @param envIdElt 单个environment标签中对应的内容
     * @return 数据源
     */
    private   GenericDataSource  getGenericDataSource(Element envIdElt){
        Element dataSourceElt = envIdElt.element("dataSource");
        String type = dataSourceElt.attributeValue("type").trim();
        Map<String,String> map = new HashMap<>();

//        //获取url/username/passwd
        //  <property name="url" value="jdbc:mysql://localhost:3306/powernode"/>
        List<Element> propertyElts = dataSourceElt.elements("property");
        propertyElts.forEach(propertyElt -> {
            String name = propertyElt.attributeValue("name");
            String value = propertyElt.attributeValue("value");
            map.put(name,value);
        });

        GenericDataSource dataSource = null;
        if (Const.UN_POOLED_DATASOURCE.equalsIgnoreCase(type)) {
            dataSource = new UnPooledDataSource(map.get("driver"),map.get("url"),map.get("username"),map.get("password"));
        }

        //下面两个未实现
        if (Const.POOLED_DATASOURCE.equalsIgnoreCase(type)) {
            dataSource = new PooledDataSource(null,null,null,null);
        }
        if(Const.JNDI_DATASOURCE.equalsIgnoreCase(type)){
            dataSource = new JNDIDataSource(null,null,null,null);
        }

        return dataSource;
    }


    /**
     *  获取事务管理器
     * @param envIdElt 单个environment标签中对应的内容
     * @param dataSource 数据源
     * @return 事务管理器
     */
    private Transaction getTransaction(Element envIdElt, GenericDataSource dataSource) {
        Element transactionElt  = envIdElt.element("transactionManager");
        String type = transactionElt.attributeValue("type").trim();

        Transaction transaction = null;
        if (type.equals(Const.JDBC_TRANSACTION)) {
            transaction = new JdbcTransaction(dataSource,false);
            //默认开启事务,需要手动提交
        }
        if (type.equals(Const.MANAGED_TRANSACTION)){
            transaction = new ManagedTransaction();
        }
        return transaction;
    }


    /**
     * 解析godbatis-config.xml中的mappers标签
     * 解析所有的SqlMapper.xml文件
//     * @param in godbatis-config.xml的文件流
     * @return 以Map的形式返回解析成功后的所有SqlMappedStatement对象
     * @throws DocumentException
     */
    private Map<String, SqlMappedStatement> getMappedStatements(Document document)
            throws DocumentException {

        /******解析<mappers>标签*******/
        List<String> sqlMapperXmlPath = new ArrayList<>() ;
//        SAXReader reader  = new SAXReader();
//        Document document = reader.read(in);
        List<Node> nodes = document.selectNodes("//mapper");
            // <mapper resource="sqlMapper.xml"/>
        nodes.forEach(mapper ->{
            Element mapperElt = (Element) mapper;
            String resource = mapperElt.attributeValue("resource");
            sqlMapperXmlPath.add(resource);
        });

        /*****解析单个的sqlMapper.xml文件****/
        //Map:解析成功后的所有SqlMappedStatement对象
        Map<String, SqlMappedStatement> mappedStatementMap = new HashMap<>();

        sqlMapperXmlPath.forEach(mapperXmlPath ->{
            //读取sqlMapper.xml文件流
            SAXReader MapperReader  = new SAXReader();
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperXmlPath);
            try {//此异常位于Lamda表达式中,需要捕捉
                Document sqlMapperDoc = MapperReader.read(resourceAsStream);
                //获取<mapper>标签
                Element mapper = (Element) sqlMapperDoc.selectSingleNode("mapper");
                //namespace
                String namespace = mapper.attributeValue("namespace");
                List<Element> elements = mapper.elements();
                elements.forEach(element ->{
                    //单个sql标签
                    String id = element.attributeValue("id");
                    String sqlId = namespace + "." + id;
                    String resultType = element.attributeValue("resultType");
                    String sql = element.getTextTrim();
                    //组合
                    SqlMappedStatement sqlMappedStatement = new SqlMappedStatement(sql, resultType);
                    mappedStatementMap.put(sqlId,sqlMappedStatement);
                });

            } catch (DocumentException e) {
                e.printStackTrace();
            }

        });

        return mappedStatementMap;
    }


}
