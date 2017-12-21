package com.zsc.game.util;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/20  19:02
 */

public class MData {

    private int id;
    private Long finished;

    public MData(int id, Long finished) {
        this.id = id;
        this.finished = finished;
    }

    public MData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getFinished() {
        return finished;
    }

    public void setFinished(Long finished) {
        this.finished = finished;
    }
}
