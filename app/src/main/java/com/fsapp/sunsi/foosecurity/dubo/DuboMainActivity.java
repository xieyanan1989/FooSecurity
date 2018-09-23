package com.fsapp.sunsi.foosecurity.dubo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.dubo.view.ListLineFeedView;

import java.util.ArrayList;

public class DuboMainActivity extends AppCompatActivity {
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final String KEY_BUNDLE_LID = "KEY_BUNDLE_LID";
    public static final String KEY_BUNDLE_PLAY_ID = "KEY_BUNDLE_PLAY_ID";
    public static final String KEY_BUNDLE_POLL_ID = "KEY_BUNDLE_POLL_ID";
    private final String[] arrayLids = {LotteryId.SSQ, LotteryId.JCZQ, LotteryId.JCLQ};
    private ArrayList<View> list;
    private TextView tvBalance;
    private LinearLayout contentlayout;
    private Context mContext = DuboMainActivity.this;
    private Intent intent;
    private Bundle bundle;
    private ImageView messageView;
    private boolean isgoing = false;
    private boolean isfirst = false;
    private TextView pad_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dubo_main);
        intent = new Intent();
        bundle = new Bundle();
        contentlayout=(LinearLayout)findViewById(R.id.main_grid_view_show);
        list = new ArrayList<View>(arrayLids.length);
        for (int i = 0; i < arrayLids.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.home_main_grid_view_item, null);
            ImageView icon = (ImageView) view.findViewById(R.id.main_grid_view_item_icon);
            TextView name = (TextView) view.findViewById(R.id.main_grid_view_item_name);
            LinearLayout grid_view_item=(LinearLayout)view.findViewById(R.id.grid_view_item);
            icon.setBackgroundResource(LotteryId.getLotteryIcon(arrayLids[i]));
            name.setText(LotteryId.getLotteryName(arrayLids[i]));
            view.setId(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            layoutParams.setMargins(5, 5, 5, 5);
            view.setLayoutParams(layoutParams);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.setClass(DuboMainActivity.this, LotteryId.getLotteryClass(arrayLids[v.getId()]));
                    bundle.putString(KEY_BUNDLE_LID, arrayLids[v.getId()]);
                    intent.putExtra(KEY_BUNDLE, bundle);
                    DuboMainActivity.this.startActivity(intent);
                }
            });
            list.add(view);
        }
        ListLineFeedView listView = new ListLineFeedView(DuboMainActivity.this, list, 3);
        contentlayout.addView(listView.getView());

    }
}
