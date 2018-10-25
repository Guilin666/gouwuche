package soexample.umeng.com.gouwuche.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.gouwuche.R;
import soexample.umeng.com.gouwuche.adapter.GoodsAdapter;
import soexample.umeng.com.gouwuche.model.GoodBean;

public class ShopView extends RelativeLayout {
    private List<GoodBean.DataBean.ListBean> data=new ArrayList<>();
    private int i;
    private GoodsAdapter goodsAdapter;
    private EditText editText;
    private int num;

    public ShopView(Context context) {
        super(context);
        init(context);
    }

    public ShopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {

        View view = View.inflate(context, R.layout.shop_view, null);
       ImageView add= view.findViewById(R.id.add);


       add.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               num++;
               editText.setText(num +"");
               data.get(i).setNum(num);
               listener.noDfide();
               goodsAdapter.notifyItemChanged(i);
           }
       });

        editText = view.findViewById(R.id.ed_test);

        ImageView jian= view.findViewById(R.id.jian);
        jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                if (num <1) {
                    Toast.makeText(context, "至少一个", Toast.LENGTH_SHORT).show();
                }else{
                    editText.setText(num +"");
                }
                data.get(i).setNum(num);
                listener.noDfide();
                goodsAdapter.notifyItemChanged(i);
            }
        });
        addView(view);

    }

    public void setData(List<GoodBean.DataBean.ListBean> data, int i, GoodsAdapter goodsAdapter) {
        this.data = data;
        this.i=i;
        this.goodsAdapter=goodsAdapter;
        num = data.get(i).getNum();
        editText.setText(num +"");
    }



    private ShopListener listener;
    public void Result(ShopListener listener){
        this.listener=listener;
    }


    public interface ShopListener{
        void noDfide();
    }
}
