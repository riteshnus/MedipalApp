package com.nus.iss.android.medipal.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;

public class FaqHelpActivity extends AppCompatActivity
{
    TextView ques1, ques2, ques3, ques4, ques5, ques6, ans1, ans2, ans3, ans4, ans5, ans6;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_help);

        ques1 = (TextView)findViewById(R.id.ques1);
        ques2 = (TextView)findViewById(R.id.ques2);
        ques3 = (TextView)findViewById(R.id.ques3);
        ques4 = (TextView)findViewById(R.id.ques4);
        ques5 = (TextView)findViewById(R.id.ques5);
        ques6 = (TextView)findViewById(R.id.ques6);

        ans1 = (TextView)findViewById(R.id.ans1);
        ans2 = (TextView)findViewById(R.id.ans2);
        ans3 = (TextView)findViewById(R.id.ans3);
        ans4 = (TextView)findViewById(R.id.ans4);
        ans5 = (TextView)findViewById(R.id.ans5);
        ans6 = (TextView)findViewById(R.id.ans6);


        ques1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ans1.getVisibility() == View.GONE)
                {
                    ans1.setVisibility(View.VISIBLE);
                    ans2.setVisibility(View.GONE);
                    ans3.setVisibility(View.GONE);
                    ans4.setVisibility(View.GONE);
                    ans5.setVisibility(View.GONE);
                    ans6.setVisibility(View.GONE);
                }
                else
                {
                    ans1.setVisibility(View.GONE);
                }
                //Toast.makeText(FaqHelpActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ques2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if(ans2.getVisibility() == View.GONE)
            {
                ans2.setVisibility(View.VISIBLE);
                ans1.setVisibility(View.GONE);
                ans3.setVisibility(View.GONE);
                ans4.setVisibility(View.GONE);
                ans5.setVisibility(View.GONE);
                ans6.setVisibility(View.GONE);
            }
            else
            {
                ans2.setVisibility(View.GONE);
            }
        }
    });

        ques3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(ans3.getVisibility() == View.GONE)
                {
                    ans3.setVisibility(View.VISIBLE);
                    ans1.setVisibility(View.GONE);
                    ans2.setVisibility(View.GONE);
                    ans4.setVisibility(View.GONE);
                    ans5.setVisibility(View.GONE);
                    ans6.setVisibility(View.GONE);
                }
                else
                {
                    ans3.setVisibility(View.GONE);
                }
            }
        });

        ques4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if(ans4.getVisibility() == View.GONE)
            {
                ans4.setVisibility(View.VISIBLE);
                ans1.setVisibility(View.GONE);
                ans2.setVisibility(View.GONE);
                ans3.setVisibility(View.GONE);
                ans5.setVisibility(View.GONE);
                ans6.setVisibility(View.GONE);
            }
            else
            {
                ans4.setVisibility(View.GONE);
            }
        }
    });

        ques5.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if(ans5.getVisibility() == View.GONE)
            {
                ans5.setVisibility(View.VISIBLE);
                ans1.setVisibility(View.GONE);
                ans2.setVisibility(View.GONE);
                ans3.setVisibility(View.GONE);
                ans4.setVisibility(View.GONE);
                ans6.setVisibility(View.GONE);
            }
            else
            {
                ans5.setVisibility(View.GONE);
            }
        }
    });

        ques6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(ans6.getVisibility() == View.GONE)
                {
                    ans6.setVisibility(View.VISIBLE);
                    ans1.setVisibility(View.GONE);
                    ans2.setVisibility(View.GONE);
                    ans3.setVisibility(View.GONE);
                    ans4.setVisibility(View.GONE);
                    ans5.setVisibility(View.GONE);
                }
                else
                {
                    ans6.setVisibility(View.GONE);
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
