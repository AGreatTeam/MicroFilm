package com.zsc.game.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/19  11:02
 */

@Entity
public class Data {
    @Id(autoincrement = true)
    private Long id;
    private String json;
    @Generated(hash = 1257607310)
    public Data(Long id, String json) {
        this.id = id;
        this.json = json;
    }
    @Generated(hash = 2135787902)
    public Data() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }





}
