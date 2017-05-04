package lianxin.com.baobaonaifenji;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lianxin.com.baobaonaifenji.Bean.Bean;
import lianxin.com.baobaonaifenji.adapter.MyLeftAdapter;
import lianxin.com.baobaonaifenji.adapter.MyRightAdapter;
import lianxin.com.baobaonaifenji.app.MyApplication;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mleft;
    private RecyclerView mRight;
    private MyLeftAdapter myLeftAdapter;
    private MyRightAdapter myRightAdapter;
    private String path = "http://mock.eoapi.cn/success/4q69ckcRaBdxhdHySqp2Mnxdju5Z8Yr4";
    List<Bean.RsBean> dataList = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Bean.RsBean> list = (List<Bean.RsBean>) msg.obj;
            MyApplication.LEFT_POSITION = 0;
            dataList.clear();
            dataList.addAll(list);
            myLeftAdapter.setData(dataList);
            //取出初始是右侧的数据
            List<Bean.RsBean.ChildrenBeanX> children = dataList.get(MyApplication.LEFT_POSITION).getChildren();
            myRightAdapter.setData(children);
            //设置左侧item的点击监听
            myLeftAdapter.setOnItemClickListenter(new MyLeftAdapter.onItemClickListenter() {
                @Override
                public void setOnItemClickListenter(View view, int position) {
                    MyApplication.LEFT_POSITION = position;
                    myLeftAdapter.notifyDataSetChanged();
                    List<Bean.RsBean.ChildrenBeanX> children = dataList.get(MyApplication.LEFT_POSITION).getChildren();
                    myRightAdapter.setData(children);
                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化资源ID
        initView();
        //解析数据
        getData();

    }
    //解析数据的方法
    private void getData() {
        //解析数据的方法

            //创建OkHttp对象
            OkHttpClient okHttpClient = new OkHttpClient();
            //创建一个Request
            Request request = new Request.Builder().url(path).build();
            //创建Call对象
        Call call = okHttpClient.newCall(request);
        //请求加入调度
          call.enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                  //响应成功之后是在子线程中 所以通过Handler发送到主线程
              }

              @Override
              public void onResponse(Call call, Response response) throws IOException {
                  String result = response.body().string();
                  Gson gson = new Gson();
                  Bean bean = gson.fromJson(result, Bean.class);
                  List<Bean.RsBean> rs = bean.getRs();

                  Message msg = Message.obtain();

                  msg.obj = rs;
                  handler.sendMessage(msg);
              }
          });
        }

        //初始化资源ID
        private void initView() {
            mleft = (RecyclerView) findViewById(R.id.left_recyclerView);
            mRight = (RecyclerView) findViewById(R.id.right_recyclerView);
            //设置管理者
            mleft.setLayoutManager(new LinearLayoutManager(this));
            mRight.setLayoutManager(new LinearLayoutManager(this));
            //设置适配器
            myLeftAdapter = new MyLeftAdapter(this);
            myRightAdapter = new MyRightAdapter(this);
            mleft.setAdapter(myLeftAdapter);
            mRight.setAdapter(myRightAdapter);
            //设置条目动画
            mleft.setItemAnimator(new DefaultItemAnimator());
            mRight.setItemAnimator(new DefaultItemAnimator());
        }

    }
