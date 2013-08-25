/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oauth.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class Feed implements Serializable {

    private static final long serialVersionUID = 175499944757799835L;
    String name = "";
    String actor_id = "";
    String source_id = "";
    String description = "";
    String headurl = "";
    Date update_time;
    String title = "";
    String href = "";
    String prefix = "";
    String type = "";
    String url_main = "";
    List<Comment> comments = new ArrayList<Comment>();
    Photo photo = new Photo();

    public Feed() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }
    
    public String getActor_id() {
        return actor_id;
    }

    public void setActor_id(String actor_id) {
        this.actor_id = actor_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String url) {
        this.headurl = url;
    }
    
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    
    public Date getUpdate_time() {
        return update_time;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
    
        public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
     public String getUrl_main() {
        return url_main;
    }

    public void setUrl_main(String url_main) {
        this.url_main = url_main;
    }
    
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}