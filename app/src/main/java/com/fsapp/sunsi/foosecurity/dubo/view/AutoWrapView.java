package com.fsapp.sunsi.foosecurity.dubo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.fsapp.sunsi.foosecurity.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * TODO: document your custom view class.
 */
public class AutoWrapView extends ViewGroup  {
    private final boolean DEBUG = true;
    /** 完全居中 **/
    public static final int CENTER = 0x0f0;
    /** 内容居中 **/
    public static final int CENTER_CONTEXT = 0x0f1;
    /** 适应居中 **/
    public static final int CENTER_WRAP = 0x0E1;

    /** 自适应，跟据内容的大小显示界面 **/
    public static final int WRAP_CONTENT = 0x1a1;
    /** 全屏显示，跟据屏幕的大小显示界面 **/
    public static final int FILL_PARENT = 0x1a2;

    public static final int GAP_W = 2;
    public static final int GAP_Y = 5;

    private int mCenter;
    /** 范围 **/
    private int range;

    private int count;
    /** 每一行所占的个数*/
    private int col;
    private boolean stretch;

    /** 统一高度 **/
    private int height = 0;
    private int width = 0;
    /** 宽*/
    private int gapW = 0;
    /** 高*/
    private int gapH = 0;

    private int parentWidth;
    private int parentHeight;

    private ArrayList<Integer> listW;
    private ArrayList<Integer> listOffset;
    public ArrayList<CheckBox> mCheckLists;

    private OnAutoViewListener onView;

    public AutoWrapView(Context context) {
        super(context);
        listW = new ArrayList<Integer>();
        listOffset = new ArrayList<Integer>();
        mCheckLists = new ArrayList<CheckBox>();
    }

    public AutoWrapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        listW = new ArrayList<Integer>();
        listOffset = new ArrayList<Integer>();
        mCheckLists = new ArrayList<CheckBox>();
    }

    public AutoWrapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        listW = new ArrayList<Integer>();
        listOffset = new ArrayList<Integer>();
        mCheckLists = new ArrayList<CheckBox>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        count = getChildCount();
        int width = 0;
        int ws = 0;
        int row = 1;
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        listW.clear();
        listOffset.clear();

        for (int index = 0; index < count; index++) {
            final View child = getChildAt(index);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            if(col > 0 && stretch){
                width = countSize();
                height = width;

//				System.out.println("1111width >> " + width + " :: height >> " + height);
//				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
//				params.gravity = Gravity.CENTER;
//				child.setLayoutParams(params);
            }else{
                width = this.width > 0 ? this.width : child.getMeasuredWidth();
                height = this.height > 0 ? this.height : child.getMeasuredHeight();

//				System.out.println("222width >> " + width + " :: height >> " + height);
            }

            listW.add(width);

            //计算一行的球所占的个数
            ws += width + gapW * 2;

            //固定个数 并且 不进行拉伸
            if(col > 0 && !stretch){
                if(index % col == 0 && index > 0){
                    listOffset.add((parentWidth + width - ws) >> 1);
                    ws = width;
                    row++;
                }
            }else{
                if(ws > parentWidth){
                    listOffset.add((parentWidth + width - ws) >> 1);
                    ws = width;
                    row++;
                }
            }
            if(index == count - 1){
                // 不居中显示并且为第一行 进行处理 wrap_conter
                if(mCenter == CENTER_WRAP && row == 1){
                    parentWidth = ws + gapW * 4;
                }

                if(range == WRAP_CONTENT){
                    int temp = ws + gapW * 4;
                    parentWidth = temp > parentWidth? temp: parentWidth;
                }

                listOffset.add((parentWidth - (ws + gapW * 4)) >> 1);
            }
//				System.out.println(index + "   " + row);
        }

//		parentHeight = (row == 0)? height + gapH * 2: row * (height + gapH * 2);
        parentHeight = (row == 0)? height + gapH * 2: row * (height + gapH)+gapH;
        setMeasuredDimension(parentWidth, parentHeight); //设定当前界面给定的范围

    }

    @Override
    protected void onLayout(boolean arg0, int left, int top, int right, int bottom) {
        int width = 0;
        int row = 0;// which row lay you view relative to parent
        int lengthX = 0; // right position of child relative to parent
        int lengthY = 0; // bottom position of child relative to parent
        int screenWidth = right-left;

        mCheckLists.clear();

        for (int i = 0; i < count; i++) {

            width = listW.get(i);

            lengthX += width + gapW * 2;
            lengthY = (row + 1) * (height + gapH );

            if(mCenter == CENTER && i == 0){
                lengthX += listOffset.get(row);
                screenWidth = right - left - listOffset.get(row);
            }else if(mCenter == CENTER_CONTEXT && i == 0){
                Collections.sort(listOffset);
                lengthX += listOffset.get(0);
                screenWidth = right - left - listOffset.get(0);
            }

//			System.out.println(i + "  lengthX : " + lengthX + "   right-left : " + (screenWidth) );
            if (lengthX > screenWidth) {
//				System.out.println(i + "  listOffset " + listOffset.size() + "  " +listOffset.toString());
                row++;
                lengthX = width + gapW * 2;
                lengthY = (row + 1) * (height + gapH );

                if(mCenter == CENTER && row < listOffset.size()){
                    lengthX += listOffset.get(row);
                    screenWidth = right - left - listOffset.get(row);
                }else if(mCenter == CENTER_CONTEXT){
                    lengthX += listOffset.get(0);
                    screenWidth = right - left - listOffset.get(0);
                }
//				System.out.println(listOffset.size() + "   " + row + "   " + listOffset.get(row));
            }
            // if it can't drawing on a same line , skip to next line
            View child = getChildAt(i);
            child.setId(i);
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);

            child.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(onView != null) onView.onView(view);
                }
            });

            //当前的View 如果是 CheckBox 就把他给存起来
            if(child instanceof CheckBox){
//				System.out.println("width >> " + width + " :: height >> " + height);
                CheckBox checkBox = (CheckBox) child;
//				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
//				params.gravity = Gravity.CENTER;
//				checkBox.setLayoutParams(params);
                checkBox.setWidth(width);
                checkBox.setHeight(height);
                checkBox.setGravity(Gravity.CENTER);
                mCheckLists.add((CheckBox)child);
            }
        }
    }

    private int countSize(){
        return (parentWidth - col * gapW * 2) / col;
    }

    public void setCol(int col, boolean stretch){
        this.col = col;
        this.stretch = stretch;
    }

    /**
     * 左右上下之间的间距
     * @param gapW
     */
    public void setGap(int gapW, int gapH) {
        this.gapW = gapW;
        this.gapH = gapH;
    }

    /**
     * 界面的宽
     * @return
     */
    public int getParentWidth() {
        return parentWidth;
    }

    /**
     * 界面的高
     * @return
     */
    public int getParentHeight() {
        return parentHeight;
    }

    /**
     * 是否居中显示 内陪居中
     * @param center CENTER 全居中显示  CENTER_CONTEXT 内部环境对外居中显示
     */
    public void setCenter(int center){
        this.mCenter = center;
    }

    /**
     * 内容的显示模式
     * @param range FILL_PARENT 全居显示  WRAP_CONTENT 自适应显示
     */
    public void setRange(int range){
        this.range = range;
    }

    /**
     * 清除选中的框
     */
    public void clear(){
        for(CheckBox checkBox: mCheckLists){
            if(checkBox.isChecked()){
                checkBox.setChecked(false);
            }
        }
    }

    /**
     * 给值，给定一个组合，使组合里相对应的ID选中
     * @param group
     */
    public void setSelect(int[] group){
        clear(); //先清除
        if(group != null){
            for(int i: group){
                CheckBox checkBox = mCheckLists.get(i);
                checkBox.setChecked(true);
            }
        }
    }

    /**
     * 给值，给定一个数，使其相对应的ID选中
     * @param index
     */
    public void setSelect(int index){
        clear(); //先清除
        CheckBox checkBox = mCheckLists.get(index);
        checkBox.setChecked(true);
    }

    /**
     * 设置胆 不可点击
     * @param group 不可点击的组合
     */
    public void setEnabled(int[] group){
        clear(); //先清除
        for(CheckBox checkBox: mCheckLists){
            if(!checkBox.isEnabled()) checkBox.setEnabled(true);
        }
        if(group != null){
            for(int i: group){
                CheckBox checkBox = mCheckLists.get(i);
                checkBox.setEnabled(false);
            }
        }
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    /**
     * 实现 每个View的点击事件
     * @param onView OnAutoViewListener
     */
    public void setOnAutoViewListener(OnAutoViewListener onView){
        this.onView = onView;
    }

    public interface OnAutoViewListener{
        public void onView(View v);
    }
}