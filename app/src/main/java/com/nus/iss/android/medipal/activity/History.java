package com.nus.iss.android.medipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nus.iss.android.medipal.R;

public class History extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        onClickListener();
    }

    public void onClickListener()
    {
        Button btnMedicineHistory = (Button) findViewById(R.id.btn_medicinehistory);
        Button btnMeasurementHistory = (Button) findViewById(R.id.btn_measurementhistory);

        final Bundle b = new Bundle();
        btnMedicineHistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                b.putString("NAME", "CONSUMPTION");
                Intent intent = new Intent(History.this, HistoryList.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        btnMeasurementHistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                b.putString("NAME", "MEASUREMENT");
                Intent intent = new Intent(History.this, HistoryList.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        Button btnFaq = (Button) findViewById(R.id.btn_Faq);
        btnFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(History.this, FaqHelp.class));
            }
        });
    }
}
