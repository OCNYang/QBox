package com.ocnyang.qbox.app.utils;
/**
 * 号码验证工具类
 *
 */
public class NumberUtils
{
	/**
	 * 中国移动拥有号码段为:139,138,137,136,135,134,159,158,157(3G),151,150,188(3G),187(3G
	 * );13个号段 中国联通拥有号码段为:130,131,132,156(3G),186(3G),185(3G);6个号段
	 * 中国电信拥有号码段为:133,153,189(3G),180(3G);4个号码段
	 */
	private static String mRegMobileStr = "^1(([3][456789])|([5][01789])|([8][78]))[0-9]{8}$";
	private static String mRegMobile3GStr = "^((157)|(18[78]))[0-9]{8}$";
	private static String mRegUnicomStr = "^1(([3][012])|([5][6])|([8][56]))[0-9]{8}$";
	private static String mRegUnicom3GStr = "^((156)|(18[56]))[0-9]{8}$";
	private static String mRegTelecomStr = "^1(([3][3])|([5][3])|([8][09]))[0-9]{8}$";
	private static String mRegTelocom3GStr = "^(18[09])[0-9]{8}$";
	private static String mRegPhoneString = "^(?:13\\d|15\\d)\\d{5}(\\d{3}|\\*{3})$";

	private String mobile = "";
	private int facilitatorType = 0;
	private boolean isLawful = false;
	private boolean is3G = false;

	public NumberUtils(String mobile)
	{
		this.setMobile(mobile);
	}

	public void setMobile(String mobile)
	{
		if (mobile == null)
		{
			return;
		}
		/** */
		/** 第一步判断中国移动 */
		if (mobile.matches(NumberUtils.mRegMobileStr))
		{
			this.mobile = mobile;
			this.setFacilitatorType(0);
			this.setLawful(true);
			if (mobile.matches(NumberUtils.mRegMobile3GStr))
			{
				this.setIs3G(true);
			}
		}
		/** */
		/** 第二步判断中国联通 */
		else if (mobile.matches(NumberUtils.mRegUnicomStr))
		{
			this.mobile = mobile;
			this.setFacilitatorType(1);
			this.setLawful(true);
			if (mobile.matches(NumberUtils.mRegUnicom3GStr))
			{
				this.setIs3G(true);
			}
		}
		/** */
		/** 第三步判断中国电信 */
		else if (mobile.matches(NumberUtils.mRegTelecomStr))
		{
			this.mobile = mobile;
			this.setFacilitatorType(2);
			this.setLawful(true);
			if (mobile.matches(NumberUtils.mRegTelocom3GStr))
			{
				this.setIs3G(true);
			}
		}
		/** */
		/** 第四步判断座机 */
		if (mobile.matches(NumberUtils.mRegPhoneString))
		{
			this.mobile = mobile;
			this.setFacilitatorType(0);
			this.setLawful(true);
			if (mobile.matches(NumberUtils.mRegMobile3GStr))
			{
				this.setIs3G(true);
			}
		}
	}

	public String getMobile()
	{
		return mobile;
	}

	public int getFacilitatorType()
	{
		return facilitatorType;
	}

	public boolean isLawful()
	{
		return isLawful;
	}

	public boolean isIs3G()
	{
		return is3G;
	}

	private void setFacilitatorType(int facilitatorType)
	{
		this.facilitatorType = facilitatorType;
	}

	private void setLawful(boolean isLawful)
	{
		this.isLawful = isLawful;
	}

	private void setIs3G(boolean is3G)
	{
		this.is3G = is3G;
	}

}
