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
public class Comment implements Serializable {

    private static final long serialVersionUID = 175499944757799835L;
   String name = "";
   String time = "";
   String text = "";
   String comment_id = "";
   String uid = "";

    public Comment() {
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
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public String getText() {
        return  text ;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }
}