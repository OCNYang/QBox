package com.ocnyang.qbox.app.module.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.ocnyang.qbox.app.Html5Activity;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.database.FunctionDao;
import com.ocnyang.qbox.app.model.entities.FunctionBean;
import com.ocnyang.qbox.app.model.entities.RefreshFindFragmentEvent;
import com.ocnyang.qbox.app.module.find.chinacalendar.ChinaCalendarActivity;
import com.ocnyang.qbox.app.module.find.constellation.ConstellationActivity;
import com.ocnyang.qbox.app.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class FindMoreActivity extends BaseCommonActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_findmore)
    Toolbar mToolbarFindmore;
    @BindView(R.id.recyclerview_findmore)
    RecyclerView mRecyclerviewFindmore;
    public FindMoreAdapter mFindMoreAdapter;
    List<FunctionBean> mFunctionBeanList;
    public ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    public ItemTouchHelper mItemTouchHelper;
    public FunctionDao mFunctionDao;

    public int flagSmall;
    public int flagBig;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_find_more);
    }

    @Override
    public void initView() {
        flagSmall = 0;
        flagBig = 0;
        initToolbar();
        initData();
        initRecyclerView();

    }

    private void initData() {
        mFunctionDao = new FunctionDao(getApplicationContext());
        mFunctionBeanList = mFunctionDao.queryFunctionBeanListSmallNoMore();
    }

    private void initRecyclerView() {
        mRecyclerviewFindmore.setLayoutManager(new LinearLayoutManager(this));
        mFindMoreAdapter = new FindMoreAdapter(mFunctionBeanList,this);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mFindMoreAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerviewFindmore);

        mFindMoreAdapter.enableDragItem(mItemTouchHelper);
        mFindMoreAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from,
                                         RecyclerView.ViewHolder target, int to) {

                //颜色标记 & 数字 的变化
                View itemView = source.itemView;
                TextView viewById = (TextView) itemView.findViewById(R.id.icon_item_findmore);
                viewById.setText(String.valueOf(to+1));
                View itemView1 = target.itemView;
                ((TextView) itemView1.findViewById(R.id.icon_item_findmore)).setText(String.valueOf(from+1));
                if (from==4&&to==5){
                    itemView.findViewById(R.id.flag_item_findmore).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    itemView1.findViewById(R.id.flag_item_findmore).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    return;
                }
                if (from==5&&to==4){
                    itemView.findViewById(R.id.flag_item_findmore).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    itemView1.findViewById(R.id.flag_item_findmore).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

                flagSmall++;

            }
        });

        addHeaderView();

        addFooterView();

        mRecyclerviewFindmore.setAdapter(mFindMoreAdapter);
        mRecyclerviewFindmore.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.name_item_findmore);
                switch (textView.getText().toString()) {
                    case "万年历":
                        startActivity(new Intent(FindMoreActivity.this, ChinaCalendarActivity.class));
                        break;
                    case "快递查询":
                        Intent intent = new Intent(FindMoreActivity.this, Html5Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url","https://m.kuaidi100.com/");
                        intent.putExtra("bundle",bundle);
                        startActivity(intent);
                        break;
                    case "黄金数据":
                        notOpen();
                        break;
                    case "股票数据":
                        notOpen();
                        break;
                    case "更多":
                        startActivity(new Intent(FindMoreActivity.this, FindMoreActivity.class));
                        break;
                    case "身份证查询":
                        Intent intent_idcard = new Intent(FindMoreActivity.this, QueryInfoActivity.class);
                        intent_idcard.putExtra(QueryInfoActivity.QUERY_STYLE,QueryInfoActivity.QUERY_IDCARD);
                        startActivity(intent_idcard);
                        break;
                    case "邮编查询":
                        notOpen();
                        break;
                    case "手机归属地":
                        Intent intent_tel = new Intent(FindMoreActivity.this, QueryInfoActivity.class);
                        intent_tel.putExtra(QueryInfoActivity.QUERY_STYLE,QueryInfoActivity.QUERY_TEL);
                        startActivity(intent_tel);
                        break;
                    case "QQ吉凶":
                        Intent intent_qq = new Intent(FindMoreActivity.this, QueryInfoActivity.class);
                        intent_qq.putExtra(QueryInfoActivity.QUERY_STYLE,QueryInfoActivity.QUERY_QQ);
                        startActivity(intent_qq);
                        break;
                    case "星座运势":
                        startActivity(new Intent(FindMoreActivity.this, ConstellationActivity.class));
                        break;
                    case "周公解梦":
                        notOpen();
                        break;
                    case "汇率":
                        notOpen();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void notOpen(){
        Toast.makeText(FindMoreActivity.this, "该功能现在暂时未开放！", Toast.LENGTH_SHORT).show();
    }

    private void addFooterView() {
        View footerView = getLayoutInflater().inflate(R.layout.activity_find_more_footer,
                ((ViewGroup) mRecyclerviewFindmore.getParent()), false);
        footerView.findViewById(R.id.history_findmore_footer).setOnClickListener(this);
        footerView.findViewById(R.id.laohuangli_findmore_footer).setOnClickListener(this);
        footerView.findViewById(R.id.xiaohua_findmore_footer).setOnClickListener(this);

        Switch historySwitch = (Switch) footerView.findViewById(R.id.switch_history_findmore_footer);
        Switch laohuangliSwitch = (Switch) footerView.findViewById(R.id.switch_laohuangli_findmore_footer);
        Switch xiaohuaSwitch = (Switch) footerView.findViewById(R.id.switch_xiaohua_findmore_footer);
        TextView userStar = (TextView) footerView.findViewById(R.id.userstar_fondmore_footer);

        historySwitch.setChecked((Boolean) SPUtils.get(FindMoreActivity.this,Const.STAR_IS_OPEN,true));
        laohuangliSwitch.setChecked((Boolean) SPUtils.get(FindMoreActivity.this,Const.STUFF_IS_OPEN,true));
        xiaohuaSwitch.setChecked((Boolean) SPUtils.get(FindMoreActivity.this,Const.JOKE_IS_OPEN,true));
        userStar.setText(((String) SPUtils.get(FindMoreActivity.this, Const.USER_STAR, "白羊座")));

        historySwitch.setOnCheckedChangeListener(this);
        laohuangliSwitch.setOnCheckedChangeListener(this);
        xiaohuaSwitch.setOnCheckedChangeListener(this);

        mFindMoreAdapter.addFooterView(footerView);
    }

    private void addHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.activity_find_more_header,
                ((ViewGroup) mRecyclerviewFindmore.getParent()),false);
        mFindMoreAdapter.addHeaderView(headerView);
    }


    private void initToolbar() {
        mToolbarFindmore.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbarFindmore.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.history_findmore_footer:
                break;
            case R.id.laohuangli_findmore_footer:
                break;
            case R.id.xiaohua_findmore_footer:
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        flagBig++;

        switch(buttonView.getId()){
            case R.id.switch_history_findmore_footer:
                SPUtils.put(this,Const.STAR_IS_OPEN,isChecked);
                break;
            case R.id.switch_laohuangli_findmore_footer:
                SPUtils.put(this,Const.STUFF_IS_OPEN,isChecked);
                break;
            case R.id.switch_xiaohua_findmore_footer:
                SPUtils.put(this,Const.JOKE_IS_OPEN,isChecked);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        List<FunctionBean> data = mFindMoreAdapter.getData();

        for (int i = 0; i < data.size(); i++) {
            FunctionBean functionBean = data.get(i);

            int j = i+1;

            if (functionBean.getId() != j) {
                if (j < 6) {
                    functionBean.setId(j);
                }else {
                    functionBean.setId(j+1);
                }
            }
        }
        mFunctionDao.updateAllFunctionBean(data);
        mFunctionDao.updateMoreFunctionBean();

        super.onPause();

    }

    @Override
    protected void onDestroy() {
        if (flagSmall>0||flagBig>0) {
            EventBus.getDefault().post(new RefreshFindFragmentEvent(flagSmall, flagBig));
        }
        super.onDestroy();
    }
}
