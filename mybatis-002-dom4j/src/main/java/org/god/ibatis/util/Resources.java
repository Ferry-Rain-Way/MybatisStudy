package org.god.ibatis.util;

import java.io.InputStream;

public class Resources {
   private  Resources (){}
    public static InputStream getResourceAsStream(String resourcesPath){
       return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcesPath);
        //ClassLoader.getSystemResourceAsStream(resourcesPath);
    }

}
