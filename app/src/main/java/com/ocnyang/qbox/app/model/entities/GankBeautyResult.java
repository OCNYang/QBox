// (c)2016 Flipboard Inc, All Rights Reserved.

package com.ocnyang.qbox.app.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankBeautyResult {
    public boolean error;
    public @SerializedName("results") List<GankBeauty> beauties;
}
