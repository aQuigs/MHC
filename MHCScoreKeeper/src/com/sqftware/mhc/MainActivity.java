package com.sqftware.mhc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity
{
    private ScoreView[] scores;
    private ScrollView scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scroller = (ScrollView) findViewById(R.id.svScroller);
        final TextView tvTotalScore = (TextView) findViewById(R.id.tvTotalScore);
        scores = new ScoreView[5];
        scores[0] = (ScoreView) findViewById(R.id.sv1);
        scores[1] = (ScoreView) findViewById(R.id.sv2);
        scores[2] = (ScoreView) findViewById(R.id.sv3);
        scores[3] = (ScoreView) findViewById(R.id.sv4);
        scores[4] = (ScoreView) findViewById(R.id.sv5);
        for (ScoreView sv : scores)
            sv.setExternalViews(tvTotalScore);

        final TextView tvHatScore = (TextView) findViewById(R.id.tvHatScore);
        final TextView prev = (TextView) findViewById(R.id.tvPrevScore);
        ((Button) findViewById(R.id.btNewGame)).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                scroller.fullScroll(ScrollView.FOCUS_UP);
                prev.setText(tvTotalScore.getText());
                tvTotalScore.setText("0");
                for (ScoreView sv : scores)
                    sv.clear();
                tvHatScore.setText("0");
            }
        });

        ((Button) findViewById(R.id.btHatsIncr)).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int hatScore = ScoreView.extractScore(tvHatScore) - 1;
                tvHatScore.setText(Integer.toString(hatScore));
                int totalScore = ScoreView.extractScore(tvTotalScore);
                tvTotalScore.setText(Integer.toString(totalScore - 1));
            }
        });

        ((Button) findViewById(R.id.btClearHats)).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int hatScore = ScoreView.extractScore(tvHatScore);
                int totalScore = ScoreView.extractScore(tvTotalScore);
                tvHatScore.setText("0");
                tvTotalScore.setText(Integer.toString(totalScore - hatScore));
            }
        });
    }
}
