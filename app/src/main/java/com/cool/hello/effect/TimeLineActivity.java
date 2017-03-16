package com.cool.hello.effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cool.hello.R;
import com.cool.hello.model.TimeLineModel;

import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity {

    private RecyclerView mTimeLineRecyclerView;
    private List<TimeLineModel> mTimeLineModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        getSupportActionBar().setTitle(getClass().getSimpleName());

        initData();
        initView();
    }

    private void initView() {
        mTimeLineRecyclerView = (RecyclerView) findViewById(R.id.rv_time_line);
        mTimeLineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTimeLineRecyclerView.setAdapter(new TimeLineAdapter());
    }

    private void initData() {
        for (int i = 1; i <= 15; i++) {
            TimeLineModel timeLineModel = new TimeLineModel("巡逻点" + i);
            mTimeLineModels.add(timeLineModel);
        }

    }


    public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder>{

        @Override
        public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TimeLineActivity.this).inflate(R.layout.item_time_line, parent, false);
            return new TimeLineViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return mTimeLineModels.size();
        }

        @Override
        public void onBindViewHolder(TimeLineViewHolder holder, int position) {
            final TimeLineModel timeLineModel = mTimeLineModels.get(position);
            if(position == mTimeLineModels.size() -1){
                holder.mLineView.setVisibility(View.GONE);
            }else {
                holder.mLineView.setVisibility(View.VISIBLE);
            }
            holder.mPollingLocation.setText(timeLineModel.pollingLocation);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TimeLineActivity.this,timeLineModel.pollingLocation,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder{

        public View mLineView;
        public TextView mPollingLocation;

        public TimeLineViewHolder(View itemView) {
            super(itemView);
            mLineView = itemView.findViewById(R.id.v_line);
            mPollingLocation = (TextView) itemView.findViewById(R.id.tv_polling_location);
        }
    }
}
