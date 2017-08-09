package com.ocnyang.qbox.app.module.me.weather.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ocnyang.qbox.app.R;

import java.util.ArrayList;
import java.util.Collections;


public class LabelSpinner extends FrameLayout {
	
	public interface OnSelectionChangedListener{
		public void OnSelectionChanged(int position);
	}

	private TextView mTitleTextView, mSummaryTextView, mSelectedTextView;
	private int mSelection = -1;
	private DropdownPopup mPopup;
	private ArrayList<Object> mEntries = new ArrayList<Object>();
	
	private final int textColor = 0xffffffff;
	private final int textSize = 16;
	private final int popupBackgroundColor = 0xff2f3136;
	private final int activatedColor = 0x11ffffff;
	
	private final float density;
	private OnSelectionChangedListener mOnSelectionChangedListener;
	
	public void setOnSelectionChangedListener(OnSelectionChangedListener listener){
		mOnSelectionChangedListener = listener;
	}


	public LabelSpinner(Context context) {
		this(context, null);
	}

	public LabelSpinner(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LabelSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		density = context.getResources().getDisplayMetrics().density;
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//		View.inflate(context, R.layout.label_spinner_layout, this);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelSpinner);
		
		String title = a.getString(R.styleable.LabelSpinner_titleText);
		String summary = a.getString(R.styleable.LabelSpinner_summaryText);
		CharSequence[] entries = a.getTextArray(R.styleable.LabelSpinner_entries);
		if(entries != null){
			Collections.addAll(mEntries, entries);
		}
		a.recycle();
		LayoutParams leftViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		leftViewParams.gravity = Gravity.LEFT|Gravity.CENTER_VERTICAL;
		
		mTitleTextView = new TextView(context);
		mTitleTextView.setTextColor(textColor);
		mTitleTextView.setTextSize(textSize);
		mTitleTextView.setText(title);
		
//		if(TextUtils.isEmpty(summary)){
			addView(mTitleTextView, leftViewParams);
//		}else{
//			LinearLayout linearLayout = new LinearLayout(context);
//			linearLayout.setOrientation(LinearLayout.VERTICAL);
//			
//			
//			LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//					LinearLayout.LayoutParams.WRAP_CONTENT);
//			linearLayout.addView(mTitleTextView, titleParams);
//			
//			mSummaryTextView = new TextView(context);
//			mSummaryTextView.setTextColor(0xff666666);
//			mSummaryTextView.setTextSize(14f);
//			mSummaryTextView.setText(summary);
//			if(TextUtils.isEmpty(summary)){
//				mSummaryTextView.setVisibility(View.GONE);
//			}
//			LinearLayout.LayoutParams summaryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//					LinearLayout.LayoutParams.WRAP_CONTENT);
//			linearLayout.addView(mSummaryTextView, summaryParams);
//			
//			addView(linearLayout, leftViewParams);
//		}
		
		
		mSelectedTextView = new TextView(context);
		mSelectedTextView.setTextColor(textColor);
		mSelectedTextView.setTextSize(textSize);
		mSelectedTextView.setGravity(Gravity.CENTER_VERTICAL);
		
		if(!isInEditMode()){
			Drawable drawable = context.getResources().getDrawable( R.drawable.iconfont_arrow);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			mSelectedTextView.setCompoundDrawables(null, null,drawable, null);
		}else{
			mTitleTextView.setText("title");
			mSummaryTextView.setText("summary");
			
		}
		LayoutParams selectedParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		selectedParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		addView(mSelectedTextView, selectedParams);
		
		mPopup = new DropdownPopup(context, mSelectedTextView, mEntries);
		setSelection(0, false);
		setClickable(true);
	}
	
	
	private TextView makePopupListViewItem(boolean fake){
		TextView textView = new TextView(getContext());
//		textView.setCheckMarkDrawable(getContext().getResources().get)
		textView.setTextColor(textColor);
		textView.setTextSize(textSize);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setSingleLine();
		int paddingHorizontal = (int)(density * 8f);
		textView.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
		textView.setMinHeight((int) (density * 40f));
		if(!fake){
			StateListDrawable sel = new StateListDrawable();
//			sel.addState(new int[]{android.R.attr.state_checked, -android.R.attr.state_pressed }, new ColorDrawable(0x66003377));
			sel.addState(new int[]{android.R.attr.state_activated}, new ColorDrawable(activatedColor));
//			sel.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(0x33999999));
			sel.addState(new int[]{}, new ColorDrawable(Color.TRANSPARENT));
			textView.setBackgroundDrawable(sel);
		}
		return textView;
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	@Override
	public boolean performClick() {
		boolean handled = super.performClick();
		if (!handled) {
			handled = true;
			if (!mPopup.isShowing()) {
				mPopup.show();//getTextDirection(), getTextAlignment());
			}
		}
		return handled;
	}

	public void setSelection(int position , boolean notifyListener) {
		if(position < mEntries.size()){
			if(mSelection != position){
				mSelection = position;
				if(!isInEditMode()){
					mSelectedTextView.setText(mEntries.get(position).toString());
				}else{
					mSelectedTextView.setText("selection");
				}
				if(notifyListener){
					if(mOnSelectionChangedListener != null){
						mOnSelectionChangedListener.OnSelectionChanged(position);
					}
				}
			}
		}else{
			Toast.makeText(getContext(), "setSelection position must < entries.size!!", Toast.LENGTH_SHORT).show();
		}
	}

	public int getSelectedItemPosition() {
		return mSelection;
	}

	private class DropdownPopup {
		// private CharSequence mHintText;
		private ListAdapter mAdapter;
		private View mAnchor;
		private PopupWindow mPopupWindow;
		private ListView mListView;
		private boolean mMeasured = false;

		public DropdownPopup(Context context,View anchor, ArrayList<? extends Object> entries) {
			mAnchor = anchor;
			mAdapter = new SimpleAdapter(entries);
			mListView = new ListView(context);
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setAdapter(mAdapter);
			
//			mListView.measure(specWidth, specHeight);
			mPopupWindow = new PopupWindow(mListView);
//			setModal(true);
//			mPopupWindow.setAnimationStyle(R.style.PopupAnimationStyle);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
//			mPopupWindow.setBackgroundDrawable(new ColorDrawable());
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(popupBackgroundColor));
//			DropdownPopup.this.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.popup_background));
			//Toast.makeText(getContext(), "w->"+ mListView.getMeasuredWidth() + "\nh->" + mListView.getMeasuredHeight(), Toast.LENGTH_SHORT).show();
			
			mListView.setItemChecked(LabelSpinner.this.getSelectedItemPosition(), true);
			mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					LabelSpinner.this.setSelection(position, true);
					// if (mOnItemClickListener != null) {
					// LabelSpinner.this.performItemClick(v, position,
					// mAdapter.getItemId(position));
					// }
					mPopupWindow.dismiss();
				}
			});
		}
		public boolean isShowing(){
			return mPopupWindow.isShowing();
		}
		private class SimpleAdapter extends BaseAdapter{
			
			private ArrayList<? extends Object> arrayList;
			public SimpleAdapter(ArrayList<? extends Object> arrayList){
				this.arrayList = arrayList;
			}

			@Override
			public int getCount() {
				return arrayList.size();
			}

			@Override
			public Object getItem(int position) {
				return arrayList.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView == null){ 
					convertView = makePopupListViewItem(false);
				}
				final TextView tv = (TextView)convertView;
				tv.setText(getItem(position).toString());
				return tv;
			}
			
		}

		void computeContentWidth() {
			if(!mMeasured){
				int width = 0;
				int specWidth = MeasureSpec.makeMeasureSpec(0,//ViewGroup.LayoutParams.WRAP_CONTENT,
						MeasureSpec.UNSPECIFIED);
				int specHeight =  MeasureSpec.makeMeasureSpec(0,//Integer.MAX_VALUE,//ViewGroup.LayoutParams.MATCH_PARENT,
						MeasureSpec.UNSPECIFIED);
//				long start = System.currentTimeMillis();
				for(Object entry : mEntries){
					TextView tv = makePopupListViewItem(true);
					tv.setText(entry.toString());
					tv.measure(specWidth, specHeight);
					width = Math.max(width, tv.getMeasuredWidth());
				}
//				Toast.makeText(getContext(), "measure use time->" + (System.currentTimeMillis() - start) + "ms", Toast.LENGTH_SHORT).show();
				mPopupWindow.setWidth(width);
				mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);// mListView.getMeasuredHeight());
				mMeasured = true;
			}
		}

		public void show(){//int textDirection, int textAlignment) {
			if(mListView.getCheckedItemPosition() != getSelectedItemPosition()){
				//UiUtil.toastDebug(getContext(), "ListView.getCheckedItemPosition() != getSelectedItemPosition");
				mListView.setItemChecked(getSelectedItemPosition(), true);
			}
			if(mPopupWindow.isShowing()){
				return ;
			}
			computeContentWidth();
			mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
			int xOff = mAnchor.getWidth() - mPopupWindow.getWidth();
			mPopupWindow.showAsDropDown(mAnchor,xOff,0);
		}
	}
//	int getThemeAttrColor(int attr) {
//        if (mContext.getTheme().resolveAttribute(attr, mTypedValue, true)) {
//            if (mTypedValue.type >= TypedValue.TYPE_FIRST_INT
//                    && mTypedValue.type <= TypedValue.TYPE_LAST_INT) {
//                return mTypedValue.data;
//            } else if (mTypedValue.type == TypedValue.TYPE_STRING) {
//                return mResources.getColor(mTypedValue.resourceId);
//            }
//        }
//        return 0;
//    }
}
