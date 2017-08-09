
package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

 
public class Basic implements Serializable{

	private static final long serialVersionUID = 2001829232650081792L;
	@SerializedName("city")
    @Expose
    public String city;
    @SerializedName("cnty")
    @Expose
    public String cnty;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;
    @SerializedName("update")
    @Expose
    public Update update;

}
