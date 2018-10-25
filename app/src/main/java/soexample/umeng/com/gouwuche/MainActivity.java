package soexample.umeng.com.gouwuche;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import soexample.umeng.com.gouwuche.adapter.SellerAdapter;
import soexample.umeng.com.gouwuche.model.GoodBean;
import soexample.umeng.com.gouwuche.net.OkUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycle_main;
    private List<GoodBean.DataBean> data1;
    private TextView tv_num;
    private TextView tv_sum;
    private SellerAdapter sellerAdapter;
    private CheckBox quan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle_main = findViewById(R.id.recycle_main);
        quan = findViewById(R.id.quan);
        tv_num = findViewById(R.id.tv_num);
        tv_sum = findViewById(R.id.tv_sum);
        doHttp();
        quan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int num=0;
                double price=0;
                boolean checked = quan.isChecked();

                for (int i=0;i<data1.size();i++){
                    List<GoodBean.DataBean.ListBean> list = data1.get(i).getList();
                    for (int j=0;j<list.size();j++){
                        list.get(j).setCheck(checked);
                        num+=list.get(j).getNum();
                        price+= (list.get(j).getPrice()*list.get(j).getNum());
                    }
                }
                if (checked) {
                    tv_sum.setText("总价为:"+price);
                    tv_num.setText("总数量为:"+num);
                }else{
                    tv_sum.setText("总价为:0.0");
                    tv_num.setText("总数量为:0");
                }
                sellerAdapter.notifyDataSetChanged();

            }
        });


    }

    private void doHttp() {
        new OkUtils("http://www.zhaoapi.cn/product/getCarts?uid=71").setOkLisener(new OkUtils.OkListener() {
            @Override
            public void success(final String data) {
                GoodBean goodBean = new Gson().fromJson(data, GoodBean.class);
                data1 = goodBean.getData();
                sellerAdapter = new SellerAdapter(data1, MainActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recycle_main.setLayoutManager(linearLayoutManager);
                recycle_main.setAdapter(sellerAdapter);

                sellerAdapter.setResult(new SellerAdapter.SellerListener() {

                    @Override
                    public void getData(List<GoodBean.DataBean> data1) {
                        int num=0;
                        double price=0;
                        int numAll=0;


                        for(int i=0;i<data1.size();i++){
                            List<GoodBean.DataBean.ListBean> list = data1.get(i).getList();
                            for(int j=0;j<list.size();j++){
                                numAll = list.get(j).getNum();
                                if (list.get(j).isCheck()) {
                                    num+= list.get(j).getNum();
                                    price+= (list.get(j).getPrice()*list.get(j).getNum());
                                }
                            }
                        }
                        if (num<numAll) {
                            quan.setChecked(false);
                        }else{
                            quan.setChecked(true);
                        }
                        tv_num.setText("数量为:"+num);
                        tv_sum.setText("总价为:"+price);
                        sellerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }


}
