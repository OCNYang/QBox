// (c)2016 Flipboard Inc, All Rights Reserved.

package com.ocnyang.qbox.app.model.entities;

public class Item {
    public String description;
    public String imageUrl;

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
