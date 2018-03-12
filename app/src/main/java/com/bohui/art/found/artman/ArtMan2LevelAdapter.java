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
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.found.ArtMan2LevelBean;
import com.bohui.art.bean.found.ArtManHomeItemBean;
import com.bohui.art.bean.found.ArtManItemArtBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtMan2LevelAdapter extends BaseAdapter<ArtMan2LevelBean> {
    public ArtMan2LevelAdapter(Context context, List<ArtMan2LevelBean> datas) {
        super(context, datas);
    }

    public ArtMan2LevelAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_MAN_2LEVEL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_art_man_2level;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtMan2LevelBean itemData, int position) {
        holder.setText(R.id.tv_type_top,itemData.getName());
        GridView gridView = holder.getView(R.id.gv_art_man_arts);
        setGridView(gridView,itemData.getList());
        holder.addOnClickListener(R.id.rl_art_man_2);
        holder.addOnClickListener(R.id.iv_more);
    }

    private int imgWh = 0;//要动态指定ImageView的高度
    private void setGridView(GridView gridView, final List<ArtManHomeItemBean> artBeans) {
        int size = artBeans.size();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int length = 60;//图片的高度
        int lrW = 30;//图片的左右间距
        int gridItemWidth = (int) ((length + lrW) * density);//每个Item的宽度
        int gridViewWidth = size * gridItemWidth;//GirdView的总宽度
        imgWh = (int) (length * density);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView.getLayoutParams();
        params.width = gridViewWidth;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(gridItemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(1); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

        GridViewAdapter adapter = new GridViewAdapter(mContext,
                artBeans);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mOnGirdItemClickListener != null){
                    ArtManHomeItemBean itemBean = artBeans.get(position);
                    mOnGirdItemClickListener.girdItemClick(itemBean);
                }
            }
        });
    }
    private OnGirdItemClickListener mOnGirdItemClickListener;

    public void setOnGirdItemClickListener(OnGirdItemClickListener onGirdItemClickListener) {
        this.mOnGirdItemClickListener = onGirdItemClickListener;
    }

    public interface OnGirdItemClickListener{
        void girdItemClick(ArtManHomeItemBean itemBean);
    }
    /**GirdView 数据适配器*/
    public class GridViewAdapter extends android.widget.BaseAdapter {
        Context context;
        List<ArtManHomeItemBean> list;

        public GridViewAdapter(Context _context, List<ArtManHomeItemBean> _list) {
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
            convertView = layoutInflater.inflate(R.layout.item_art_man_2level_art_man, null);
            ImageView imageView = convertView.findViewById(R.id.iv_2level);
            //动态指定宽高，否则不显示
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            params.width = imgWh;
            params.height = imgWh;
            imageView.setLayoutParams(params);
            ArtManHomeItemBean artBean = list.get(position);
            GlideUtil.displayCircle(mContext, imageView, artBean.getPhoto());
            TextView tv = convertView.findViewById(R.id.tv_des);
            tv.setText(artBean.getName());
            return convertView;
        }
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setMargin(0, 2,0,0);
        return linearLayoutHelper;
    }
}
