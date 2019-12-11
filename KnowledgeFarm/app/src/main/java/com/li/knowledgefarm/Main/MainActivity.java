package com.li.knowledgefarm.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.li.knowledgefarm.Login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.li.knowledgefarm.R;
import com.li.knowledgefarm.Settings.SettingActivity;
import com.li.knowledgefarm.Shop.ShopActivity;
import com.li.knowledgefarm.Study.SubjectListActivity;
import com.li.knowledgefarm.entity.BagCropNumber;
import com.li.knowledgefarm.entity.User;
import com.li.knowledgefarm.entity.UserCropItem;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private ImageView learn;
    private ImageView water;
    private TextView waterCount;
    private ImageView fertilizer;
    private TextView fertilizerCount;
    private ImageView bag;
    private ImageView shop;
    private ImageView pet;
    private ImageView setting;
    private ImageView photo;
    private TextView nickName;
    private TextView level;
    private TextView account;
    private TextView money;
    private ProgressBar experience;
    private TextView experienceValue;
    private GridLayout lands;
    private Dialog bagDialog;
    private Dialog ifExtention;
    private OkHttpClient okHttpClient;
    private Handler bagMessagesHandler;
    private Gson gson;
    private List<BagCropNumber> dataList;
    private List<UserCropItem> cropList;
    private Handler UpdataLands;
    private Handler cropMessagesHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String messages = (String)msg.obj;
            Log.e("cropList",messages);
            if(!messages.equals("Fail")){
                Type type = new TypeToken<List<UserCropItem>>(){}.getType();
                cropList = gson.fromJson(messages,type);
                showLand();
            }else{
                Toast toast = Toast.makeText(MainActivity.this,"网络异常！",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };
    private int selectLand=0;//选中第几块土地
    private Handler plantMessagesHandler;
    private long lastClickTime=0;
    private long FAST_CLICK_DELAY_TIME=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        okHttpClient = new OkHttpClient();
        gson = new Gson();
        dataList = new ArrayList<>();

        setStatusBar();
        getViews();
        addListener();
        getCrop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
        showUserInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.URL)+"/user/findUserInfoByUserId?userId="+LoginActivity.user.getId())
                        .build();
                Call call = new OkHttpClient().newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("用户信息", "请求失败");
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("用户信息","aaaaaaaaaaaaaaaaa");
                        String result =  response.body().string();
                        if (result.equals("{}")) {
                            Log.e("用户信息","信息异常");
                        }else {
                            Log.e("用户信息",result);
                            Message message = new Message();
                            message.obj = LoginActivity.parsr(URLDecoder.decode(result), User.class);
                            LoginActivity.user = (User) message.obj;
                            if(bagDialog!=null){
                                bagDialog.cancel();
                                selectLand=0;
                            }
                        }

                    }
                });
            }
        }.start();
    }

    /**
     * 获取种植的作物信息
     */
    private void getCrop() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Request request = new Request.Builder().url(getResources().getString(R.string.URL)+"/usercrop/initUserCrop?userId="+LoginActivity.user.getId()).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Message message = Message.obtain();
                        message.obj = "Fail";
                        cropMessagesHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Message message = Message.obtain();
                        message.obj = response.body().string();
                        cropMessagesHandler.sendMessage(message);
                    }
                });
            }
        }.start();
    }

    /**
     * 展示用户信息
     */
    private void showUserInfo() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.huancun)
                .error(R.drawable.meigui)
                .fallback(R.drawable.meigui)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(this).load(LoginActivity.user.getPhoto()).apply(requestOptions).into(photo);
        nickName.setText(LoginActivity.user.getNickName());
        account.setText("账号:"+LoginActivity.user.getAccout());
        level.setText("Lv:"+LoginActivity.user.getLevel());
        money.setText("金币:"+LoginActivity.user.getMoney());
        waterCount.setText(LoginActivity.user.getWater()+"");
        fertilizerCount.setText(LoginActivity.user.getFertilizer()+"");
        int[] levelExperience = getResources().getIntArray(R.array.levelExperience);
        experience.setMax(levelExperience[LoginActivity.user.getLevel()-1]);
        experience.setProgress((int)LoginActivity.user.getExperience());
        experienceValue.setText(""+LoginActivity.user.getExperience()+"/"+levelExperience[LoginActivity.user.getLevel()-1]);
    }

    /**
     * 生成土地
     */
    private void showLand() {
        int flag=0;
        lands.removeAllViews();
        for (int i=1;i<19;i++){
            final ImageView land = new ImageView(this);
            ImageView plant = new ImageView(this);
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.addView(land);
            ViewGroup.LayoutParams lp = new RelativeLayout.LayoutParams(160,160);
            land.setLayoutParams(lp);
            plant.setLayoutParams(lp);
            final int finalI = i;//第几块土地
            if(LoginActivity.user.getLandStauts(finalI)==-1) {
                if(flag==0){
                    plant.setImageResource(R.drawable.kuojian);
                    relativeLayout.addView(plant);
                    //扩建
                    plant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showIfExtensionLand(finalI);
                        }
                    });
                    flag++;
                }
                land.setImageResource(R.drawable.land_green);
            }
            else if (LoginActivity.user.getLandStauts(finalI)==0) {
                land.setImageResource(R.drawable.land);
                //种植
                land.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (System.currentTimeMillis() - lastClickTime < FAST_CLICK_DELAY_TIME){
                            return;
                        }
                        lastClickTime = System.currentTimeMillis();
                        land.setImageResource(R.drawable.land_light);
                        selectLand=finalI;
                        showBagMessages();
                    }
                });
            }
            else {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.huancun)
                        .error(R.drawable.meigui)
                        .fallback(R.drawable.meigui)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                land.setImageResource(R.drawable.land);
                plant.setRotationX(-60);
                UserCropItem crop=null;
                //得到植物信息
                for (int j = 0; j < cropList.size(); j++) {
                    if(cropList.get(j).getUserCropId()==LoginActivity.user.getLandStauts(finalI)){
                        crop=cropList.get(j);
                        break;
                    }
                }
                if (crop!=null){
                    //展示植物不同阶段
                    int status = crop.getProgress() / crop.getCrop().getMatureTime();
                    if(status <0.1){
                        plant.setImageResource(R.drawable.seed);
                    }else if (status<30){
                        Glide.with(this).load(crop.getCrop().getImg1()).apply(requestOptions).into(plant);
                    }else if (status<60){
                        Glide.with(this).load(crop.getCrop().getImg2()).apply(requestOptions).into(plant);
                    }else if (status==100){
                        Glide.with(this).load(crop.getCrop().getImg3()).apply(requestOptions).into(plant);
                    }
                    //植物成长进度条
                    final ProgressBar progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
                    progressBar.setMax(crop.getCrop().getMatureTime());
                    progressBar.setProgress(crop.getProgress());
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_bg));
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(140,20);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    progressBar.setLayoutParams(layoutParams);
                    progressBar.setVisibility(View.GONE);
                    //植物成长值
                    final TextView value = new TextView(this);
                    value.setText(crop.getProgress()+"/"+crop.getCrop().getMatureTime());
                    value.setTextSize(8);
                    value.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    value.setLayoutParams(layoutParams);
                    value.setVisibility(View.GONE);
                    //添加视图
                    relativeLayout.addView(plant);
                    relativeLayout.addView(progressBar);
                    relativeLayout.addView(value);
                    plant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(value.getVisibility()==View.GONE){
                                progressBar.setVisibility(View.VISIBLE);
                                value.setVisibility(View.VISIBLE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                value.setVisibility(View.GONE);
                            }

                            //TODO 浇水施肥收获
                        }
                    });
                }

            }
            lands.addView(relativeLayout);
        }
    }

    private void addListener() {
        learn.setOnClickListener(new MainListener());
        water.setOnClickListener(new MainListener());
        fertilizer.setOnClickListener(new MainListener());
        bag.setOnClickListener(new MainListener());
        shop.setOnClickListener(new MainListener());
        pet.setOnClickListener(new MainListener());
        setting.setOnClickListener(new MainListener());
    }

    /**
     * @Description 扩建土地
     * @Auther 孙建旺
     * @Date 下午 2:39 2019/12/10
     * @Param []
     * @return void
     */
    private void ExtensionLand(final int position, final int money){
        new Thread(){
            @Override
            public void run() {
                super.run();
                FormBody formBody = new FormBody.Builder()
                        .add("userId",LoginActivity.user.getId()+"")
                        .add("landNumber","land"+position)
                        .add("needMoney",money+"").build();
                Request request = new Request.Builder().post(formBody).url(getResources().getString(R.string.URL)+"/user/extensionLand").build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Message message = Message.obtain();
                        message.obj = "Fail";
                        UpdataLands.sendMessage(message);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String callBackMessage = response.body().string();
                        Message message = Message.obtain();
                        message.obj = callBackMessage;
                        UpdataLands.sendMessage(message);
                    }
                });
            }
        }.start();
    }

    /**
     * @Description 更新土地状态
     * @Auther 孙建旺
     * @Date 下午 2:54 2019/12/10
     * @Param []
     * @return void
     */
    private void UpdataLand(final int position){
        UpdataLands = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String message = (String)msg.obj;
                if(message.equals("true")){
                    LoginActivity.user.setLandStauts(position,0);
                    int newMoney = LoginActivity.user.getMoney() - 500;
                    LoginActivity.user.setMoney(newMoney);
                    money.setText("金币:"+newMoney+"");
                    ifExtention.dismiss();
                    lands.removeAllViews();
                    showLand();
                }else if(message.equals("false")){
                    Toast toast = Toast.makeText(MainActivity.this,"没有扩建成功哦！",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(message.equals("notEnoughMoney")){
                    Toast toast = Toast.makeText(MainActivity.this,"你的钱不够了哦！",Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    Toast toast = Toast.makeText(MainActivity.this,"没有找到你的土地哦！",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };
    }

    private void getViews() {
        learn=findViewById(R.id.learn);
        water=findViewById(R.id.water);
        waterCount=findViewById(R.id.waterCount);
        fertilizer=findViewById(R.id.fertilizer);
        fertilizerCount=findViewById(R.id.fertilizerCount);
        bag=findViewById(R.id.bag);
        shop=findViewById(R.id.shop);
        pet=findViewById(R.id.pet);
        setting=findViewById(R.id.setting);
        photo=findViewById(R.id.photo);
        nickName=findViewById(R.id.nickName);
        level=findViewById(R.id.level);
        money=findViewById(R.id.money);
        account=findViewById(R.id.account);
        lands=findViewById(R.id.lands);
        experience=findViewById(R.id.experience);
        experienceValue=findViewById(R.id.experienceValue);
    }
    class MainListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.learn:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, SubjectListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.water:
                    break;
                case R.id.fertilizer:
                    break;
                case R.id.bag:
                    showBagMessages();
                    break;
                case R.id.shop:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, ShopActivity.class);
                    startActivity(intent);
                    break;
                case R.id.pet:
                    break;
                case R.id.setting:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * @Description 获取背包信息数据
     * @Auther 孙建旺
     * @Date 下午 2:38 2019/12/08
     * @Param []
     * @return void
     */
    private void getBagMessages(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Request request = new Request.Builder().url(getResources().getString(R.string.URL)+"/bag/initUserBag?userId="+LoginActivity.user.getId()).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Message message = Message.obtain();
                        message.obj ="Fail";
                        bagMessagesHandler.sendMessage(message);
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Message message = Message.obtain();
                        message.obj =response.body().string();
                        bagMessagesHandler.sendMessage(message);
                    }
                });
            }
        }.start();
    }

    /**
     * 询问扩建
     * @param position
     */
    private void showIfExtensionLand(final int position){
        AlertDialog.Builder showAlert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.extension_land_dialog,null);
        TextView needMoney = layout.findViewById(R.id.needMoney);
        Button cancel = layout.findViewById(R.id.cancelEx);
        Button trueEx = layout.findViewById(R.id.sureEx);
        needMoney.setText("你是否要花费"+(200*position-800)+"金币来扩建这块土地？");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifExtention.dismiss();
            }
        });
        trueEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtensionLand(position,(200*position-800));
                UpdataLand(position);
            }
        });
        showAlert.setView(layout);
        ifExtention = showAlert.create();
        ifExtention.show();
        WindowManager.LayoutParams attrs = ifExtention.getWindow().getAttributes();
        if (ifExtention.getWindow() != null) {
            //bagDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ifExtention.getWindow().setDimAmount(0f);//去除遮罩
        }
        attrs.gravity = Gravity.CENTER;
        final float scale = this.getResources().getDisplayMetrics().density;
        attrs.width = (int)(300*scale+0.5f);
        attrs.height =(int)(250*scale+0.5f);
        ifExtention.getWindow().setAttributes(attrs);
    }

    /**
     * @Description 背包弹出框
     * @Auther 孙建旺
     * @Date 下午 2:01 2019/12/08
     * @Param [position]
     * @return void
     */
    private void showBagMessages(){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this,R.style.dialog_soft_input);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.bag_girdview, null);
        final GridView gridView = layout.findViewById(R.id.bag_grid_view);
        alertBuilder.setView(layout);
        bagDialog = alertBuilder.create();
        bagDialog.show();
        getBagMessages();
        bagMessagesHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String messages = (String)msg.obj;
                Log.e("背包",messages);
                if(!messages.equals("Fail")){
                    Type type = new TypeToken<List<BagCropNumber>>(){}.getType();
                    dataList = gson.fromJson(messages,type);
                    BagCustomerAdapter customerAdapter = new BagCustomerAdapter(alertBuilder.getContext(),dataList,R.layout.gird_adapteritem);
                    gridView.setAdapter(customerAdapter);
                }else{
                    Toast toast = Toast.makeText(MainActivity.this,"获取数据失败！",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };
        WindowManager.LayoutParams attrs = bagDialog.getWindow().getAttributes();
        if (bagDialog.getWindow() != null) {
            //bagDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bagDialog.getWindow().setDimAmount(0f);//去除遮罩
        }
        attrs.gravity = Gravity.RIGHT;
        final float scale = this.getResources().getDisplayMetrics().density;
        attrs.width = (int)(300*scale+0.5f);
        attrs.height =(int)(400*scale+0.5f);
        bagDialog.getWindow().setAttributes(attrs);
        planting(gridView);
    }

    /**
     * 种植作物
     * @param gridView
     */
    private void planting(final GridView gridView) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(selectLand!=0){
                    plant(selectLand,dataList.get(i).getCrop().getId());
                    plantMessagesHandler=new Handler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            String messages = (String)msg.obj;
                            Log.e("种植",messages);
                            if(messages.equals("Fail")){
                                Toast.makeText(MainActivity.this,"网络异常！",Toast.LENGTH_SHORT).show();
                            }else if (messages.equals("true")){
                                Toast.makeText(MainActivity.this,"操作成功！",Toast.LENGTH_SHORT).show();
                                getUserInfo();
                            }else
                                Toast.makeText(MainActivity.this,"操作失败！",Toast.LENGTH_SHORT).show();
                        }
                    };
                }
            }
        });
        bagDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                getCrop();
            }
        });
    }

    /**
     * 请求种植
     * @param selectLand
     * @param id
     */
    private void plant(final int selectLand, final int id) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Request request = new Request.Builder().url(getResources().getString(R.string.URL)+"/user/raiseCrop?userId="+LoginActivity.user.getId()+"&cropId="+id+"&landNumber=land"+selectLand).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Message message = Message.obtain();
                        message.obj ="Fail";
                        plantMessagesHandler.sendMessage(message);
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Message message = Message.obtain();
                        message.obj =response.body().string();
                        plantMessagesHandler.sendMessage(message);
                    }
                });
            }
        }.start();
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色
        }
    }
}
