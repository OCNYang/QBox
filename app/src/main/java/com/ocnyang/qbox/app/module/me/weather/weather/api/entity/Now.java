
package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

 
public class Now implements Serializable{

	private static final long serialVersionUID = -4680776502608495585L;
	@SerializedName("cond")
    @Expose
    public Cond_ cond;
    @SerializedName("fl")
    @Expose
    public String fl;
    @SerializedName("hum")
    @Expose
    public String hum;
    @SerializedName("pcpn")
    @Expose
    public String pcpn;
    @SerializedName("pres")
    @Expose
    public String pres;
    @SerializedName("tmp")
    @Expose
    public String tmp;
    @SerializedName("vis")
    @Expose
    public String vis;
    @SerializedName("wind")
    @Expose
    public Wind__ wind;

}
