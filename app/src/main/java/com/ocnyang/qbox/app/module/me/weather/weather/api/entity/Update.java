
package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

 
public class Update implements Serializable{

	private static final long serialVersionUID = 8789602206113122980L;
	@SerializedName("loc")
    @Expose
    public String loc;
    @SerializedName("utc")
    @Expose
    public String utc;

}
