package com.ocnyang.qbox.app.module.start.welcome;

import android.support.annotation.NonNull;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.PageSupportFragment;
import com.cleveroad.slidingtutorial.TransformItem;
import com.umeng.analytics.MobclickAgent;
import com.ocnyang.qbox.app.R;

public class SecondCustomPageSupportFragment extends PageSupportFragment {

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_page_second;
	}

	@NonNull
    @Override
	protected TransformItem[] getTransformItems() {
		return new TransformItem[]{
				TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.2f),
				TransformItem.create(R.id.ivSecondImage, Direction.LEFT_TO_RIGHT, 0.6f),
				TransformItem.create(R.id.ivThirdImage, Direction.RIGHT_TO_LEFT, 0.08f),
				TransformItem.create(R.id.ivFourthImage, Direction.LEFT_TO_RIGHT, 0.1f),
				TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.03f),
				TransformItem.create(R.id.ivSixthImage, Direction.LEFT_TO_RIGHT, 0.09f),
				TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.14f),
				TransformItem.create(R.id.ivEighthImage, Direction.LEFT_TO_RIGHT, 0.07f)
		};
	}
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getUmengFragmentName()); //统计页面，"MainScreen"为页面名称，可自定义
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getUmengFragmentName());
	}

	public String getUmengFragmentName(){
		return getContext().getClass().getSimpleName()+"-"
				+this.getClass().getSimpleName();
	};
}
