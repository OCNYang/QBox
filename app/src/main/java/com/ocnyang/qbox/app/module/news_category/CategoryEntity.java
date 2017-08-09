package com.ocnyang.qbox.app.module.news_category;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 频道实体类
 */

@Entity
public class CategoryEntity {
    @Id(autoincrement = true)
    private Long id;

    private String name;
    private String key;
    private int order;
    @Generated(hash = 747555915)
    public CategoryEntity(Long id, String name, String key, int order) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.order = order;
    }
    @Generated(hash = 725894750)
    public CategoryEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CategoryEntity){
            CategoryEntity obj1 = (CategoryEntity) obj;
            if (obj1.getName().equals(this.getName()) && obj1.getKey().equals(this.getKey())) {
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
}
