package com.lufficc.simplereader.model;

import java.util.Date;

/**
 * Created by lcc_luffy on 2016/8/9.
 */

public class BaseModel {
    private long id;
    private long createdAt;
    private long updatedAt;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
