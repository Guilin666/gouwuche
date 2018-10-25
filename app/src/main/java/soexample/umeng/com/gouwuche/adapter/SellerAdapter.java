package soexample.umeng.com.gouwuche.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.gouwuche.R;
import soexample.umeng.com.gouwuche.model.GoodBean;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.MyViewHolder>{
    private  Context context;
    private  List<GoodBean.DataBean> data1=new ArrayList<>();

    public SellerAdapter(List<GoodBean.DataBean> data1, Context context) {
        this.data1=data1;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.seller_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_seller.setText(data1.get(i).getSellerName());
        GoodsAdapter goodsAdapter = new GoodsAdapter(context, data1.get(i).getList());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        myViewHolder.child_recycle.setLayoutManager(staggeredGridLayoutManager);
        myViewHolder.child_recycle.setAdapter(goodsAdapter);
        //实例化监听回调数据
        goodsAdapter.result(new GoodsAdapter.GoodsListener() {
            @Override
            public void listChange() {
                listener.getData(data1);//将数据回调
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_seller;
        private  RecyclerView child_recycle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_seller=itemView.findViewById(R.id.tv_seller);
            child_recycle=itemView.findViewById(R.id.child_recycle);
        }
    }
    private SellerListener listener;
    public void setResult(SellerListener listener){
        this.listener=listener;
    }

    public interface SellerListener{
        void getData(List<GoodBean.DataBean> data1);
    }

}
