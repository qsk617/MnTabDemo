package cc.mn.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 山野书生(1203596603@qq.com)
 * Date: 2015-11-27
 * Time: 20:35
 * Version 1.0
 */

public class TabGroupView extends LinearLayout {

    private OnItemClickListener onItemClickListener;

    private List<TabView> tabLayouts;

    private String tag = TabGroupView.class.getSimpleName();

    public TabGroupView(Context context) {
        this(context, null);
    }

    public TabGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        tabLayouts = new ArrayList<>();
        this.setOrientation(HORIZONTAL); //设置默认为水平布局
    }

    /**
     * 选中某一个
     * */
    public void setCurrentItem(int item){
        changeItem();
        TabView tabLayout = tabLayouts.get(item);
        tabLayout.setChecked(true);
    }

    /**
     * @param position 当前界面索引
     * @param positionOffset 滑动的百分比
     * */
    public void onPageScrolling(int position, float positionOffset) {
        if(positionOffset>0){
            TabView tabLayout = tabLayouts.get(position);
            tabLayout.onScrolling(1-positionOffset);
            if(position+1 < tabLayouts.size()){
                TabView nextTabLayout = tabLayouts.get(position+1);
                nextTabLayout.onScrolling(positionOffset);
            }
        }
    }

    /**
     * 设置监听器
     * */
    private void initListener(){
        int count = this.getChildCount();
        for(int i=0; i<count; i++){
            final TabView tabLayout = (TabView)this.getChildAt(i);
            tabLayouts.add(tabLayout);
            final int finalI = i;
            tabLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeItem(); //清除选中状态
                    tabLayout.setChecked(true);
                    if(onItemClickListener!=null){
                        onItemClickListener.onClick(finalI, tabLayout);
                    }
                }
            });
        }
    }

    /**
     * 清除选中状态
     * */
    private void changeItem(){
        for(TabView tabLayout:tabLayouts){
            tabLayout.setChecked(false);
        }
    }

    /**
     * 设置每个Item的监听事件
     * */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        initListener();
    }

    /**
     * 点击事件
     * */
    public interface OnItemClickListener{
        void onClick(int position, TabView tabLayout);
    }

    /**
     * 设置只能水平布局
     * */
    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(HORIZONTAL);
    }
}

