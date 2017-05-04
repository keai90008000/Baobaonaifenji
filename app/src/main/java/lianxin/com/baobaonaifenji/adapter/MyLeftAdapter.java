package lianxin.com.baobaonaifenji.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lianxin.com.baobaonaifenji.Bean.Bean;
import lianxin.com.baobaonaifenji.R;
import lianxin.com.baobaonaifenji.app.MyApplication;

public class MyLeftAdapter extends RecyclerView.Adapter<MyLeftAdapter.MyViewHolder>{
    private Context context;
    List<Bean.RsBean> dataList;
    onItemClickListenter onItemClickListenter;
    public MyLeftAdapter(Context context) {
        this.context = context;
    }
    //封装接口
    public void setOnItemClickListenter(onItemClickListenter onItemClickListenter){
        this.onItemClickListenter = onItemClickListenter;
    }
    //设置数据的方法
    public void setData(List<Bean.RsBean> dataList){
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.left_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mleft_nameTv.setText(dataList.get(position).getDirName());
        //判断索引更换背景与颜色
        if(MyApplication.LEFT_POSITION == position){
            holder.mleft_nameTv.setTextColor(context.getResources().getColor(R.color.text_check_color));
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.back_check));
        }else{
            holder.mleft_nameTv.setTextColor(context.getResources().getColor(R.color.text_normal_color));
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        //设置点击事件
        if(onItemClickListenter !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取当前点击布局的索引
                    int position1 = holder.getLayoutPosition();
                    onItemClickListenter.setOnItemClickListenter(holder.itemView,position1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList==null?0:dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView mleft_nameTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mleft_nameTv = (TextView) itemView.findViewById(R.id.left_nameTv);
        }
    }
    //书写一个接口  作为点击左侧RecyclerView的监听
    public interface onItemClickListenter{
        void setOnItemClickListenter(View view, int position);
    }
}
