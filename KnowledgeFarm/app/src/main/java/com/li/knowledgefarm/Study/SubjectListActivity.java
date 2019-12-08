package com.li.knowledgefarm.Study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.li.knowledgefarm.Main.MainActivity;
import com.li.knowledgefarm.R;

public class SubjectListActivity extends AppCompatActivity {
    /** 返回*/
    private ImageView iv_return;
    /** 数学*/
    private ImageView iv_math;
    /** 自定义点击事件监听器*/
    private CustomerListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        /** 加载视图*/
        getViews();
        /** 注册点击事件监听器*/
        registListener();
        setStatusBar();
    }

    class CustomerListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_return:
                    Intent intent = new Intent();
                    intent.setClass(SubjectListActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.iv_math:
                    Intent intent1 = new Intent();
                    intent1.setClass(SubjectListActivity.this,MathActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }

    /**
     * 加载视图
     */
    private void getViews(){
        iv_return = findViewById(R.id.iv_return);
        iv_math = findViewById(R.id.iv_math);
    }

    /**
     * 注册点击事件监听器
     */
    private void registListener(){
        listener = new CustomerListener();
        iv_return.setOnClickListener(listener);
        iv_math.setOnClickListener(listener);
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏，并且不显示字体
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏文字颜色为暗色
        }
    }
}
