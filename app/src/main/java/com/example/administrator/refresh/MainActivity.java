package com.example.administrator.refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.paojiao.recyclerviewanimators.FadeInAnimator;
import com.paojiao.recyclerviewanimators.adapters.AlphaInAnimationAdapter;
import com.paojiao.recyclerviewanimators.adapters.SlideInBottomAnimationAdapter;
import com.rockerhieu.rvadapter.states.StatesRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private PullableRecyclerView mRecyclerView;
    PullToRefreshLayout pullToRefreshLayout;
    private RecycleViewAdapter adapter;
    private List<String> list;
    private Info info;
    private String url ="https://api.yogamala.cn/";
    private int page = 1;
    private StatesRecyclerViewAdapter statesRecyclerViewAdapter;
    RecyclerViewEmptyCulture recyclerViewEmptyCulture ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);//必须添加，否则报错
        setContentView(R.layout.content_main);
        list =new ArrayList<String>();
        mRecyclerView = (PullableRecyclerView) findViewById(R.id.mRecycler);
        pullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//设置布局管理器
        mRecyclerView.setHasFixedSize(true);//设置固定大小
        adapter = new RecycleViewAdapter(list,MainActivity.this);
        mRecyclerView.setItemAnimator(new FadeInAnimator());//添加加载特效
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        SlideInBottomAnimationAdapter scaleAdapter = new SlideInBottomAnimationAdapter(alphaAdapter);
        recyclerViewEmptyCulture = new RecyclerViewEmptyCulture();
        recyclerViewEmptyCulture.setListener(this);
        statesRecyclerViewAdapter = recyclerViewEmptyCulture.setRecyclerViewEmpty(MainActivity.this, mRecyclerView, scaleAdapter);//让adapter能自动调用对应布局
        mRecyclerView.setAdapter(statesRecyclerViewAdapter);
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                refreshData();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                page = page + 1;
                initList();
            }
        });
        initList();
    }
    private void refreshData() {
        OkHttpUtils
                .get()
                .url(url + "masters")
                .addParams("type", "3")
                .addParams("page", "" + page)
                .build()
                .execute(new MyCallBack(new Info()) {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                        statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_ERROR);
                    }

                    @Override
                    public void onResponse(Object response) {
                        info = (Info) response;
                        if (info.getCode().equals("200")) {
                            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                            if (null != info && !ObjectUtil.isNull(info.getMasters())) {
                                if (!ObjectUtil.isNull(list)) {
                                    list.clear();
                                }
                                decodeData(info.getMasters());
                            }
                        } else if ("405".equals(info.getCode())) {
                            Toast.makeText(MainActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        } else {
                            Toast.makeText(MainActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.DONE);
                        }
                    }
                });
    }

    private void decodeData(List<ImageInfo> masters) {
        if (masters != null){
            for (int i = 0; i < masters.size(); i++) {
                ImageInfo imageInfo = masters.get(i);
                list.add("https://api.yogamala.cn" + imageInfo.getCover());
            }
            statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_NORMAL);//返回有正确数据时候调用

        }

    }

    private void initList() {
        OkHttpUtils
                .get()
                .url(url + "masters")
//                .addHeader("token", token)
                .addParams("type", "3")
                .addParams("page", "" + page)
                .build()
                .execute(new MyCallBack(new Info()) {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(Object response) {
                        info = (Info) response;
                        if(info.getCode().equals("200") && !ObjectUtil.isNull(info.getMasters())){
                            decodeData(info.getMasters());
                        }
                        if (page > 1) {
                            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                            if (ObjectUtil.isNull(info.getMasters())) {
                                Toast.makeText(MainActivity.this, "没有更多的数据了", Toast.LENGTH_SHORT).show();
                                page--;
                            }
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retryRoot:
            case R.id.retry_iv:
            case R.id.retry_text:
            case R.id.retry_button:
                page = 1 ;
                initList();
                break;
        }
    }
}
