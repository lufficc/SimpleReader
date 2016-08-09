package com.lufficc.simplereader.model;

/**
 * Created by lcc_luffy on 2016/8/9.
 */

public class Folder extends BaseModel{
    private String name;
    private String description;
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
