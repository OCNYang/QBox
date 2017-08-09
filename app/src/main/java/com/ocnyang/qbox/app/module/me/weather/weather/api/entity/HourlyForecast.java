
package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

 
public class HourlyForecast implements Serializable{

	private static final long serialVersionUID = 6455471587449878617L;
	@SerializedName("date")
    @Expose
    public String date;
    @SerializedName("hum")
    @Expose
    public String hum;
    @SerializedName("pop")
    @Expose
    public String pop;
    @SerializedName("pres")
    @Expose
    public String pres;
    @SerializedName("tmp")
    @Expose
    public String tmp;
    @SerializedName("wind")
    @Expose
    public Wind_ wind;

}
