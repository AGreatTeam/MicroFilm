package com.zsc.game.greendao;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/21  11:24
 */

@Entity
public class Mls {

    @Id(autoincrement = true)
    private Long id;
    private String vid;
    private String titile;
    private String json;
    @Generated(hash = 339804384)
    public Mls(Long id, String vid, String titile, String json) {
        this.id = id;
        this.vid = vid;
        this.titile = titile;
        this.json = json;
    }
    @Generated(hash = 1913151005)
    public Mls() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVid() {
        return this.vid;
    }
    public void setVid(String vid) {
        this.vid = vid;
    }
    public String getTitile() {
        return this.titile;
    }
    public void setTitile(String titile) {
        this.titile = titile;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
   

}
