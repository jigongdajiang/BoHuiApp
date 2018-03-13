package com.bohui.art.found.artman;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.found.ArtManHomeItemBean;
import com.bohui.art.bean.found.ArtManItemArtBean;
import com.bohui.art.bean.found.ArtManItemBean;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.util.CollectionUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ArtManListAdapter extends BaseAdapter<ArtManItemBean> {
    public ArtManListAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_MAN_LIST;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_art_man_list;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtManItemBean itemData, int position) {
        //头像
        ImageView ivAvr = holder.getView(R.id.iv_art_man_avr);
        //名称
        holder.setText(R.id.tv_art_man_name,itemData.getName());
        //艺术家级别
        int level = itemData.getLevel();
        switch (level){
            case 1:
                holder.setText(R.id.tv_art_man_level,"国家级艺术家");
                break;
            case 2:
                holder.setText(R.id.tv_art_man_level,"省级艺术家");
                break;
            case 3:
                holder.setText(R.id.tv_art_man_level,"市级艺术家");
                break;
        }
        //擅长
        List<String> gooadt = itemData.getGoodat();
        if(!CollectionUtil.isEmpty(gooadt)){
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0;i<gooadt.size();i++){
                if(i == gooadt.size() - 1){
                    stringBuffer.append(gooadt.get(i));
                }else{
                    stringBuffer.append(gooadt.get(i)+"-");
                }
            }
            holder.setText(R.id.tv_good,"擅长:"+stringBuffer.toString());
        }else{
            holder.setText(R.id.tv_good,"");
        }
        //作品数
        holder.setText(R.id.tv_art_number,"作品: "+itemData.getPaintingNum());
        //粉丝
        holder.setText(R.id.tv_art_fans,"粉丝: "+itemData.getFollowNum());
        //添加点击事件
        holder.addOnClickListener(R.id.rl_art_man_top);
        //作品集列表
        GlideUtil.displayCircle(mContext, ivAvr,itemData.getPhoto());
        GridView gridView = holder.getView(R.id.gv_art_man_arts);
        setGridView(gridView,itemData.getPaintingList());
    }

    private int imgWh = 0;//要动态指定ImageView的高度

    /**设置GirdView参数，绑定数据*/
    private void setGridView(GridView gridView,final List<ArtManItemArtBean> artBeans) {
        int size = artBeans.size();
        int length = 100;//gridView每个item的宽度
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        imgWh = (int) (length * density);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView.getLayoutParams();
        params.width = gridviewWidth;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(imgWh); // 设置列表项宽
        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

        GridViewAdapter adapter = new GridViewAdapter(mContext,
                artBeans);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mOnGirdItemClickListener != null){
                    ArtManItemArtBean itemBean = artBeans.get(position);
                    mOnGirdItemClickListener.girdItemClick(itemBean);
                }
            }
        });
    }

    /**GirdView 数据适配器*/
    public class GridViewAdapter extends android.widget.BaseAdapter {
        Context context;
        List<ArtManItemArtBean> list;
        public GridViewAdapter(Context _context, List<ArtManItemArtBean> _list) {
            this.list = _list;
            this.context = _context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_art_man_list_art_img, null);
            ImageView imageView = convertView.findViewById(R.id.iv_art_man_art_img);
            //动态指定宽高，否则不显示
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.width = imgWh;
            params.height = imgWh;
            imageView.setLayoutParams(params);
            ArtManItemArtBean artBean = list.get(position);
            GlideUtil.display(context, imageView,artBean.getCover());
            return convertView;
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(10);
        return linearLayoutHelper;
    }

    private OnGirdItemClickListener mOnGirdItemClickListener;

    public void setOnGirdItemClickListener(OnGirdItemClickListener onGirdItemClickListener) {
        this.mOnGirdItemClickListener = onGirdItemClickListener;
    }

    public interface OnGirdItemClickListener{
        void girdItemClick(ArtManItemArtBean itemBean);
    }
}
