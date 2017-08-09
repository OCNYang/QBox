
package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

 
public class Cond implements Serializable{

	private static final long serialVersionUID = 970590863497967173L;
	@SerializedName("code_d")
    @Expose
    public String codeD;
    @SerializedName("code_n")
    @Expose
    public String codeN;
    @SerializedName("txt_d")
    @Expose
    public String txtD;
    @SerializedName("txt_n")
    @Expose
    public String txtN;

}
