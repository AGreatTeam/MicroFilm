package com.zsc.game.mvp.model.bean;

/**
 * Created by 1 on 2017/12/17.
 */

public class DataIdBean {

    private String id;

    @Override
    public String
    toString() {
        return "DataIdBean{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataIdBean(String id) {
        this.id = id;
    }
}
