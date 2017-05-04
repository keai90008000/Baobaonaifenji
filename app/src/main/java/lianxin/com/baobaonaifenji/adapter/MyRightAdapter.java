package lianxin.com.baobaonaifenji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import lianxin.com.baobaonaifenji.Bean.Bean;
import lianxin.com.baobaonaifenji.R;
import lianxin.com.baobaonaifenji.view.MyGridView;


public class MyRightAdapter extends RecyclerView.Adapter<MyRightAdapter.MyViewHolder>{
    private Context context;
    List<Bean.RsBean.ChildrenBeanX> dataList;
    public MyRightAdapter(Context context) {
        this.context = context;
    }
    //设置数据
    public void setData(List<Bean.RsBean.ChildrenBeanX> dataList){
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.right_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mBrandTv.setText(dataList.get(position).getDirName());
        //取出对应的里面的小布局 设置给GridView
        List<Bean.RsBean.ChildrenBeanX.ChildrenBean> children = dataList.get(position).getChildren();
        //将代表产品的数据给GridView
        MyAdapter myAdapter = new MyAdapter(context,children);
        holder.mGrid.setAdapter(myAdapter);
    }

    @Override
    public int getItemCount() {
        return dataList==null?0:dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView mBrandTv;
        private final MyGridView mGrid;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBrandTv = (TextView) itemView.findViewById(R.id.brandTv);
            mGrid = (MyGridView) itemView.findViewById(R.id.gridView);
        }
    }
    //内部类的形式定义GridView的适配器
    class MyAdapter extends BaseAdapter{
        private Context context;
        private  List<Bean.RsBean.ChildrenBeanX.ChildrenBean> childList;
        private ImageView mImage;
        private TextView nameTv;
        private LinearLayout item_layout;

        public MyAdapter(Context context, List<Bean.RsBean.ChildrenBeanX.ChildrenBean> childList) {
            this.context = context;
            this.childList = childList;
        }

        @Override
        public int getCount() {
            return childList==null?0:childList.size();
        }

        @Override
        public Object getItem(int i) {
            return childList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.item_layout,null);
                holder = new ViewHolder();
                mImage = (ImageView) view.findViewById(R.id.image);
                nameTv = (TextView) view.findViewById(R.id.nameTv);
                item_layout = (LinearLayout) view.findViewById(R.id.item_layout);
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }
            BitmapUtils bitmapUtils = new BitmapUtils(context);
            bitmapUtils.display(mImage,childList.get(i).getImgApp());
            nameTv.setText(childList.get(i).getDirName());
            //设置GridView的点击事件
            item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,childList.get(i).getDirName(),Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
        class ViewHolder{
            ImageView item_image;
            TextView item_nameTv;
            LinearLayout item_lin;
        }
    }
}
