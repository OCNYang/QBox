
package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

 
public class Cond_ implements Serializable{

	private static final long serialVersionUID = -6158084720062021427L;
	@SerializedName("code")
    @Expose
    public String code;
    @SerializedName("txt")
    @Expose
    public String txt;

}
