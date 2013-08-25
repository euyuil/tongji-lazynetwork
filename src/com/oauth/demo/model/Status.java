/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oauth.demo.model;

/**
 *
 * @author Administrator
 */
public class Status {

    private static final long serialVersionUID = 175499944757799835L;
    String message = "";
    String comment_count = "0";
    String time = "";
    String uid = "0";

    public Status() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
     public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
