package com.li.knowledgefarm.Study.GetSubjectQuestion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.li.knowledgefarm.Login.LoginActivity;
import com.li.knowledgefarm.R;
import com.li.knowledgefarm.Study.Interface.SubjectInterface;
import com.li.knowledgefarm.entity.Question3Num;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 孙建旺
 * @description 获取数学题目
 * @date 2020/03/23 下午 8:10
 */

public class GetMathQuestion extends SubjectInterface {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Gson gson = new Gson();
    private List<Question3Num> list = null;
    private Handler getMath;
    private final Activity context;
    private Intent intent;

    public GetMathQuestion(Activity activity, Intent intent) {
        this.context = activity;
        this.intent = intent;
    }

    /**
     * @Description 获取数学题
     * @Auther 孙建旺
     * @Date 上午 8:56 2019/12/11
     * @Param []
     * @return void
     */
    @Override
    public void getQuestion() {
        getMathHandler(intent);
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                super.run();
                if (LoginActivity.user.getMathRewardCount() <= 0) {
                    Toast.makeText(context,"今天的数学任务做完了哦",Toast.LENGTH_SHORT).show();
                } else {
                    Request request = null;
                    switch (LoginActivity.user.getGrade()) {
                        case 1:
                            request = new Request.Builder().url(context.getResources().getString(R.string.URL) + "/answer/OneUpMath").build();
                            break;
                        case 2:
                            request = new Request.Builder().url(context.getResources().getString(R.string.URL) + "/answer/OneDownMath").build();
                    }
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Message message = Message.obtain();
                            message.obj = "Fail";
                            getMath.sendMessage(message);
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Message message = Message.obtain();
                            message.obj = response.body().string();
                            getMath.sendMessage(message);
                        }
                    });
                }
            }
        }.start();
    }

    /**
     * @Description 处理返回值
     * @Author 孙建旺
     * @Date 下午 8:28 2020/03/23
     * @Param []
     * @return java.util.List<com.li.knowledgefarm.entity.Question3Num>
     */
    @Override
    public void getMathHandler(final Intent intent) {
        getMath = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String data = (String)msg.obj;
                if(!data.equals("Fail") && !data.equals("html")) {
                    Type type = new TypeToken<List<Question3Num>>() {
                    }.getType();
                    list = gson.fromJson(data, type);
                    if(list != null){
                        intent.putExtra("math",(Serializable) list);
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context,"网络出了点问题",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context,"网络出了点问题",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

}