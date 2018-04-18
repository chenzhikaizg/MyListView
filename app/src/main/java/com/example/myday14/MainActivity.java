package com.example.myday14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mArrs;
    private TextView mTvChange;
    private MyVerticalListView mVlv;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVlv = (MyVerticalListView)findViewById(R.id.mvLv);
        MyListView mLv= (MyListView) findViewById(R.id.m_lv);
        mTvChange = (TextView)findViewById(R.id.tv_change);
        mArrs = new ArrayList<>();
        for (int i=0;i<20;i++){
            mArrs.add("第"+i+"行");

        }

        mTvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrs.clear();
                for (int i=0;i<20;i++){
                    mArrs.add("变化"+i+"行");

                }
                mVlv.closeMenu();
                myAdapter.notifyDataSetChanged();

            }
        });
        myAdapter = new MyAdapter();
        mLv.setAdapter(myAdapter);



    }
    class MyAdapter extends BaseAdapter{

            @Override
            public int getCount() {
                return mArrs.size();
            }

            @Override
            public Object getItem(int position) {
                return mArrs.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.adapter, parent, false);
                TextView viewById = view.findViewById(R.id.m_tv);
                viewById.setText(mArrs.get(position));
                return view;
            }
    }

}
