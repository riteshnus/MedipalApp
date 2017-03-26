package com.nus.iss.android.medipal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;

public class FaqHelp extends AppCompatActivity
{
    TextView ques1, ques2, ans1, ans2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_help);

        ques1 = (TextView)findViewById(R.id.ques1);
        ques2 = (TextView)findViewById(R.id.ques2);

        ans1 = (TextView)findViewById(R.id.ans1);
        ans2 = (TextView)findViewById(R.id.ans2);

        ques1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ans1.getVisibility() == View.GONE)
                {
                    ans1.setVisibility(View.VISIBLE);
                }
                else
                {
                    ans1.setVisibility(View.GONE);
                }
                //Toast.makeText(FaqHelp.this, "Button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ques2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(ans2.getVisibility() == View.GONE)
                {
                    ans2.setVisibility(View.VISIBLE);
                }
                else
                {
                    ans2.setVisibility(View.GONE);
                }
            }
        });

    }
}
