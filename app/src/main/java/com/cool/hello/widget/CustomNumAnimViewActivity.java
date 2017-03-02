package com.cool.hello.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cool.hello.R;
import com.org.sleepgod.widget.numAnimView.CustomNumAnimView;

import static android.R.attr.id;

public class CustomNumAnimViewActivity extends AppCompatActivity {

    private CustomNumAnimView customNumAnimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_num_anim_view);
        customNumAnimView = (CustomNumAnimView) findViewById(R.id.cna_view);
    }

    public void startAnim(View v){
        customNumAnimView.startAnim();
    }

    public void stopAnim(View view){
        customNumAnimView.stopAnim();
    }
}
