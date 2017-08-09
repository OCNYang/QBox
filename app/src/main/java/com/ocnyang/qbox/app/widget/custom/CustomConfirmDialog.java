package com.ocnyang.qbox.app.widget.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ocnyang.qbox.app.R;


/**
 * 自定义确认对话框
 */
public class CustomConfirmDialog {
    private Button btnPositive, btnNegative;
    private TextView tvTitle;
    private AlertDialog.Builder builder;
    private Context context;
    private AlertDialog alertDialog;

    /**
     * 初始化自定义确认框
     *
     * @param context
     * @param title            标题
     * @param positiveListener 确认按钮监听
     */
    public CustomConfirmDialog(Context context, String title, View.OnClickListener positiveListener) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        View customView = getCustomView(title, positiveListener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        builder.setView(customView);
    }

    public void show() {
        alertDialog = builder.create();
        alertDialog.show();
    }

    private View getCustomView(String title, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_layout, null);
        tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        btnPositive = (Button) mView.findViewById(R.id.btn_positive);
        btnPositive.setOnClickListener(positiveListener);
        btnNegative = (Button) mView.findViewById(R.id.btn_negative);
        btnNegative.setOnClickListener(negativeListener);

        return mView;
    }
}
