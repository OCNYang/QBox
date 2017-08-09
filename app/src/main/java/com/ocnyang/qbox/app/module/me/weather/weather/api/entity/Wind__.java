
package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

 
public class Wind__ implements Serializable{

	private static final long serialVersionUID = -445394030071429193L;
	@SerializedName("deg")
    @Expose
    public String deg;
    @SerializedName("dir")
    @Expose
    public String dir;
    @SerializedName("sc")
    @Expose
    public String sc;
    @SerializedName("spd")
    @Expose
    public String spd;

}
