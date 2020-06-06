package com.cn.autotest.buildframework;


import com.cn.autotest.exceptions.ErrorException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  简单Spring-beans实现
 */

public class SimpleXMLApplicationContext {

    public Map<String, Object> getBeans() {
        return beans;
    }

    private  Map<String, Object> beans = new HashMap<String, Object>();

    public void init(String filePath) throws ErrorException {
        SAXBuilder sb = new SAXBuilder();
        try {
            Document doc = sb.build(new FileInputStream(filePath+"/"+"SysBeansConfig.xml"));
            //获取根节点
            Element root=doc.getRootElement();
            //将根节点的下的孩子全部放到List中
            List allList= root.getChildren("bean");
            for(int i=0;i<allList.size();i++)
            {
                Element sigElement=(Element)allList.get(i);
                //获取每个<bean>节点的id和class属性的值
                String id=sigElement.getAttribute("id").getValue();
                String clazz=sigElement.getAttribute("class").getValue();
                //根据获取的类名利用反射得到实例
                Class<?> demo=Class.forName(clazz);
                Object obj=demo.newInstance();
                //将id和类的实例放到beans中
                beans.put(id, obj);

                //判断该bean下是否存在property的子节点
                List propertyChild=sigElement.getChildren("property");
                for(int j=0;j<propertyChild.size();j++)
                {
                    //获取此bean下name和bean的属性 ，其中bean指的是配置文件中另一个bean
                    Element propertyElement=(Element)propertyChild.get(j);
                    String name=propertyElement.getAttributeValue("name");  //userDAO
                    String bean=propertyElement.getAttributeValue("bean");    //u
                    System.out.println(bean);
                    //拼接该bean（useDAO）的setter方法 setUserDAO
                    String setterMethodName="set"+name.substring(0,1).toUpperCase()+name.substring(1);
                    //调用setter方法是需要注入com.spring.dao.impl.UserDAOImpl类的 对象
                    Object  fieldObj=beans.get(bean);
                    //获取fieldObj对象的类的类型，
                    Class<?> type=fieldObj.getClass().getInterfaces()[0];

                    Method setterMethod=obj.getClass().getMethod(setterMethodName, type);
                    setterMethod.invoke(obj,fieldObj);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("SimpleXMLApplicationContext 加载失败"+e.getMessage());
        }
    }

    //提供给外界接口
    public static Object getBean(String filePath,String id) throws ErrorException {
        SimpleXMLApplicationContext context = new SimpleXMLApplicationContext();
        context.init(filePath);
        return context.getBeans().get(id);
    }
}
