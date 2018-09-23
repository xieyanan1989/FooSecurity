package com.fsapp.sunsi.foosecurity.dubo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchListBean;
import com.fsapp.sunsi.foosecurity.dubo.ticai.JLSFCLass;
import com.fsapp.sunsi.foosecurity.dubo.ticai.JZBFCLass;
import com.fsapp.sunsi.foosecurity.dubo.ticai.JZBQCCLass;
import com.fsapp.sunsi.foosecurity.dubo.ticai.JZRQSPFCLass;
import com.fsapp.sunsi.foosecurity.dubo.ticai.JZSPFCLass;
import com.fsapp.sunsi.foosecurity.dubo.ticai.JZZHGGCLass;
import com.fsapp.sunsi.foosecurity.dubo.ticai.JZZJQSCLass;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;

import java.util.List;

/**
 * Created by xyn-pc on 2018/9/13.
 */

public class SectionListAdapter extends AmazingAdapter {
    private LayoutInflater inflater;
    private List<JZMatchBean> jzMatchBeans;
    private List<JZMatchListBean> jzMatchListBeans;
    private Context context ;
    private String lotteryId;
    private String playType;
    public SectionListAdapter(List<JZMatchBean> jzMatchBeans, List<JZMatchListBean> jzMatchListBeans,LayoutInflater inflater) {
        this.jzMatchBeans = jzMatchBeans;
        this.jzMatchListBeans = jzMatchListBeans;
        this.inflater =  inflater;
    }

    @Override
    protected void onNextPageRequested(int page) {

    }

    @Override
    protected void bindSectionHeader(View view, final int position, boolean displaySectionHeader) {
        LinearLayout parentLayout = (LinearLayout) view.findViewById(R.id.section_parent_layout);
        if (displaySectionHeader) {
            parentLayout.setVisibility(View.VISIBLE);
            final String sectionString = getSections()[getSectionForPosition(position)];
            TextView lSectionTitle = (TextView) view.findViewById(R.id.header_section);
            CheckBox section_expend_cb = (CheckBox) view.findViewById(R.id.section_expend_cb);
            lSectionTitle.setText(sectionString);
            section_expend_cb.setChecked(isSectionChecked(sectionString));
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSection(sectionString,position);
                }
            });
        } else {
            parentLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 获取数据与文件
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getAmazingView(int position, View convertView, ViewGroup parent) {
        JZMatchBean mJCBDBean = getItem(position);
        View res = convertView;
        if (res == null)
            res = inflater.inflate(R.layout.jingcai_section_layout, null);
        LinearLayout parent_layout = (LinearLayout) res.findViewById(R.id.parent_layout);
        final String sectionString = getSections()[getSectionForPosition(position)];
        if(isSectionChecked(sectionString)){
            parent_layout.setVisibility(View.VISIBLE);
            parent_layout.removeAllViews();
            showItems(mJCBDBean, parent_layout);
        }else {
            parent_layout.setVisibility(View.GONE);
        }
        return res;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        final String sectionString = getSections()[getSectionForPosition(position)];
        TextView header_section = (TextView) header.findViewById(R.id.header_section);
        CheckBox section_expend_cb = (CheckBox) header.findViewById(R.id.section_expend_cb);
        header_section.setText(sectionString);
        section_expend_cb.setChecked(isSectionChecked(sectionString));
    }

    @Override
    public int getPositionForSection(int section) {
        if (section < 0)
            section = 0;
        if (section >= jzMatchListBeans.size())
            section = jzMatchListBeans.size() - 1;
        int c = 0;
        for (int i = 0; i < jzMatchListBeans.size(); i++) {
            if (section == i) {
                return c;
            }
            c += jzMatchListBeans.get(i).getMatchList().size();
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        int c = 0;
        for (int i = 0; i < jzMatchListBeans.size(); i++) {
            if (position >= c && position < (c + jzMatchListBeans.get(i).getMatchList().size())) {
                return i;
            }
            c += jzMatchListBeans.get(i).getMatchList().size();
        }
        return -1;
    }

    @Override
    public String[] getSections() {
        String[] res = new String[jzMatchListBeans.size()];
        for (int i = 0; i < jzMatchListBeans.size(); i++) {
            res[i] = jzMatchListBeans.get(i).getSection();
        }
        return res;
    }

    @Override
    public int getCount() {
        return jzMatchBeans.size();
    }

    @Override
    public JZMatchBean getItem(int position) {
        return jzMatchBeans.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    /**
     * section的checkbox状态操作 状态：1为展开显示，2未展示
     *
     * @param sectionString
     * @return
     */
    private boolean isSectionChecked(String sectionString) {
        for (JZMatchListBean bean : jzMatchListBeans) {
            if (bean.getSection().equals(sectionString)) {
                if (bean.getStatus() == 1) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 点击section事件处理
     *
     * @param sectionString
     */
    private void onClickSection(String sectionString, int position) {
        try {
            for (JZMatchListBean bean : jzMatchListBeans) {
                if (bean.getSection().equals(sectionString)) {
                    if (bean.getStatus() == 1) {
                        bean.setStatus(2);
                        this.notifyDataSetChanged();
                    } else if (bean.getStatus() == 2) {
                        bean.setStatus(1);
                        this.notifyDataSetChanged();
                    }
//                    sectionLV.setSelection(position-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<JZMatchBean> getJzMatchBeans() {
        return jzMatchBeans;
    }

    public void setJzMatchBeans(List<JZMatchBean> jzMatchBeans) {
        this.jzMatchBeans = jzMatchBeans;
    }

    public List<JZMatchListBean> getJzMatchListBeans() {
        return jzMatchListBeans;
    }

    public void setJzMatchListBeans(List<JZMatchListBean> jzMatchListBeans) {
        this.jzMatchListBeans = jzMatchListBeans;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private JCAbstractClass mJCItemClass;//
    private void showItems(JZMatchBean bean, LinearLayout parent_layout) {
        mJCItemClass = null;
        if (lotteryId.equals(LotteryId.JCZQ)) {
            if (playType.equals(LotteryId.PLAY_ID_01)) {
                if (mJCItemClass == null) {
                    mJCItemClass = new JZSPFCLass(context, inflater,lotteryId);
                }
                mJCItemClass.showItems(bean, parent_layout);
            }
            if (playType.equals(LotteryId.PLAY_ID_02)) {
                if (mJCItemClass == null) {
                    mJCItemClass = new JZRQSPFCLass(context, inflater,lotteryId);
                }
                mJCItemClass.showItems(bean, parent_layout);
            }
            if (playType.equals(LotteryId.PLAY_ID_03)) {
                if (mJCItemClass == null) {
                    mJCItemClass = new JZZJQSCLass(context, inflater,lotteryId);
                }
                mJCItemClass.showItems(bean, parent_layout);
            }
            if (playType.equals(LotteryId.PLAY_ID_04)) {
                if (mJCItemClass == null) {
                    mJCItemClass = new JZBQCCLass(context, inflater,lotteryId);
                }
                mJCItemClass.showItems(bean, parent_layout);
            }
            if (playType.equals(LotteryId.PLAY_ID_05)) {
                if (mJCItemClass == null) {
                    mJCItemClass = new JZBFCLass(context, inflater,lotteryId);
                }
                mJCItemClass.showItems(bean, parent_layout);
            }
            if (playType.equals(LotteryId.PLAY_ID_06)) {
                if (mJCItemClass == null) {
                    mJCItemClass = new JZZHGGCLass(context, inflater,lotteryId);
                }
                mJCItemClass.showItems(bean, parent_layout);
            }
        }
        if (lotteryId.equals(LotteryId.JCLQ)) {
            if (playType.equals(LotteryId.PLAY_ID_01)) {
                if (mJCItemClass == null) {
                    mJCItemClass = new JLSFCLass(context, inflater,lotteryId);
                }
                mJCItemClass.showItems(bean, parent_layout);
            }
        }
        mJCItemClass.setBetOnClickListener(new JCAbstractClass.OnBetOnClickListener() {
            @Override
            public void onOkBtnClick(String lotteryId, String playMethod, JZMatchBean jzMatchBean, boolean status) {
                if(onsEectionBetOnClickListener != null){
                    onsEectionBetOnClickListener.onsEectionBetOnClickListener(lotteryId,playMethod,jzMatchBean,status);
                }
            }
        });
    }
    private OnsEectionBetOnClickListener onsEectionBetOnClickListener;
    public interface OnsEectionBetOnClickListener {
        public void onsEectionBetOnClickListener(String lotteryId, String playMethod, JZMatchBean jzMatchBean, boolean status);
    }
    public void setOnsEectionBetOnClickListener(OnsEectionBetOnClickListener onsEectionBetOnClickListener) {
        this.onsEectionBetOnClickListener = onsEectionBetOnClickListener;
    }
}
