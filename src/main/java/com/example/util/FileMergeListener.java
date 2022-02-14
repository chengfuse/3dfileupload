package com.example.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * 文件session共享监听
 * 作者：陈福森
 * @Date: 2022-1-4 12:13
 */
@Component
public class FileMergeListener {

    private HttpSession session;

    //创建session
    public  void  setSession(HttpSession session){
        this.session=session;
    }
    //获取session
    public  Object getSession(String  key){
        if (session == null) {
            return null;
        }
        return session.getAttribute(key);
    }

    //删除session
    public  void  removeSession(String  key){
        session.removeAttribute(key);
    }
}
