package com.zsc.game.mvp.model.bean;

import java.util.List;

/**
 * Created by 苏照亮 2017/12/18.
 */

public class VideoCatagory {

    public String msg;
    public RetBean ret;
    public String code;

    public static class RetBean {

        public AdvBean adv;
        public int pnum;
        public int totalRecords;
        public int records;
        public int totalPnum;
        public List<?> bannerList;
        public List<ListBean> list;

        public static class AdvBean {

            public String imgURL;
            public String dataId;
            public String htmlURL;
            public String shareURL;
            public String title;
        }

        public static class ListBean {

            public String airTime;
            public String duration;
            public String loadtype;
            public String score;
            public String angleIcon;
            public String dataId;
            public String description;
            public String loadURL;
            public String shareURL;
            public String pic;
            public String title;
            public String roomId;
        }
    }
}
