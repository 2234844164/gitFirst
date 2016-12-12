package com.example.administrator.refresh;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rockerhieu.rvadapter.states.StatesRecyclerViewAdapter;


/**
 * Created by Administrator on 2016/5/16.
 */
public class RecyclerViewEmptyCulture {

    private static View.OnClickListener listener;
    private static String empty_hint ;

    public static StatesRecyclerViewAdapter setRecyclerViewEmpty(Activity activity, PullableRecyclerView recyclerView, RecyclerView.Adapter adapter) {
        View loadingView = activity.getLayoutInflater().inflate(R.layout.view_loading, recyclerView, false);//设置loading，error，empty三种情况的布局
        View emptyView = activity.getLayoutInflater().inflate(R.layout.view_empty, recyclerView, false);
        View errorView = activity.getLayoutInflater().inflate(R.layout.view_error, recyclerView, false);

        TextView empty_text = (TextView) emptyView.findViewById(R.id.empty_text);
        if (TextUtils.isEmpty(getEmpty_hint())){
            empty_text.setText("暂无数据");
        }else {
            empty_text.setText(getEmpty_hint());
        }

        LinearLayout retryRoot = (LinearLayout) errorView.findViewById(R.id.retryRoot);
        ImageView retry_iv = (ImageView) errorView.findViewById(R.id.retry_iv);
        TextView retry_text = (TextView) errorView.findViewById(R.id.retry_text);
        Button retry_button = (Button) errorView.findViewById(R.id.retry_button);
        if ( listener != null ){
            retryRoot.setOnClickListener(listener);
            retry_iv.setOnClickListener(listener);
            retry_text.setOnClickListener(listener);
            retry_button.setOnClickListener(listener);
        }
        return new StatesRecyclerViewAdapter(adapter, loadingView, emptyView, errorView);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public static String getEmpty_hint() {
        return empty_hint;
    }

    public static void setEmpty_hint(String empty_hint) {
        RecyclerViewEmptyCulture.empty_hint = empty_hint;
    }
}
