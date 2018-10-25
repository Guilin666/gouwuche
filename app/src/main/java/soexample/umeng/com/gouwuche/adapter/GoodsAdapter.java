package soexample.umeng.com.gouwuche.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import soexample.umeng.com.gouwuche.R;
import soexample.umeng.com.gouwuche.model.GoodBean;
import soexample.umeng.com.gouwuche.view.ShopView;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.MyViewHolder> {
    private Context context;
    private List<GoodBean.DataBean.ListBean> list;

    public GoodsAdapter(Context context, List<GoodBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public GoodsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(context, R.layout.good_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_data.setText(list.get(i).getTitle());
        myViewHolder.tv_price.setText(list.get(i).getPrice() + "");
        String replace = list.get(i).getImages().replace("https", "http");
        Glide.with(context).load(replace.split("\\|")[0]).into(myViewHolder.item_img);
        myViewHolder.xuan.setChecked(list.get(i).isCheck());
        myViewHolder.xuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(i).isCheck()) {
                    list.get(i).setCheck(false);
                }else{
                    list.get(i).setCheck(true);
                }
                notifyItemChanged(i);
                listener.listChange();

            }
        });

        myViewHolder.shopView.setData(list,i,this);
        myViewHolder.shopView.Result(new ShopView.ShopListener() {
            @Override
            public void noDfide() {
                listener.listChange();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_data;
        ImageView item_img;
        CheckBox xuan;
        TextView tv_price;
        ShopView shopView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_data = (TextView) itemView.findViewById(R.id.tv_data);
            item_img = itemView.findViewById(R.id.item_img);
            xuan = (CheckBox) itemView.findViewById(R.id.xuan);
            tv_price = itemView.findViewById(R.id.tv_price);
           shopView=itemView.findViewById(R.id.shop);

        }
    }


    private GoodsListener listener;
    //监听商品的状态
    public void result(GoodsListener listener){
        this.listener=listener;
    }

    public interface GoodsListener{
        void listChange();
    }
}
