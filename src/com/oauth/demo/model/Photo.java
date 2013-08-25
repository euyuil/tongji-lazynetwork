/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oauth.demo.model;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class Photo implements Serializable {

    private static final long serialVersionUID = 175499944757799835L;
    String name = "";
    String uid = "";
    String url_tiny = "";
    String url_main = "";
    String caption = "";
    String pid = "";//photo id
    String aid = "";//album id
    String update_time = "";
   
    public Photo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public String getUrl_tiny() {
        return url_tiny;
    }

    public void setUrl_tiny(String url_tiny) {
        this.url_tiny = url_tiny;
    }
    
    public String getUrl_main () {
        return url_main;
    }

    public void setUrl_main (String url_main ) {
        this.url_main = url_main;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
    
    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }
    
    public String getUpdate_time() {
        return update_time;
    }
    
    public String setUpdate_time(String update_time) {
        return  update_time;
    }
}