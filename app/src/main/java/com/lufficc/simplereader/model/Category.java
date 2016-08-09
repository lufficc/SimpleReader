package com.lufficc.simplereader.model;

import java.util.Date;

/**
 * Created by lcc_luffy on 2016/8/9.
 */

public class Category extends BaseModel {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
