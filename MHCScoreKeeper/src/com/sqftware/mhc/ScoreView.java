package com.sqftware.mhc;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreView extends LinearLayout
{
    private static final int MAX_SHOTS = 3;
    private static final int MAX_SCORE = (1 + 2 + 3 + 4 + 5) * MAX_SHOTS;

    private final int points;
    private int made;
    private int missed;
    private TextView tvSubtotal;
    private TextView tvMade;
    private TextView tvMissed;
    private TextView tvTotalScore;

    public ScoreView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ScoreView, 0, 0);
        try
        {
            points = a.getInteger(R.styleable.ScoreView_points, 1);
        }
        finally
        {
            a.recycle();
        }

        if (points == 0)
            Log.e("MHC", "Points was 0");
        LayoutInflater.from(context).inflate(R.layout.score_view, this);
        findViews();
        setupClickListeners();
    }

    public void setExternalViews(TextView total)
    {
        tvTotalScore = total;
    }

    private void findViews()
    {
        tvSubtotal = (TextView) findViewById(R.id.tvSubtotal);
        tvMade = (TextView) findViewById(R.id.tvHitCount);
        tvMissed = (TextView) findViewById(R.id.tvMissedCount);
        ((TextView) findViewById(R.id.tvShotNum)).setText("Shot " + points + ":");
    }

    private void setupClickListeners()
    {
        ((Button) findViewById(R.id.btMakeShot)).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (canShoot())
                {
                    ++made;
                    tvMade.setText(Integer.toString(made));
                    tvSubtotal.setText(Integer.toString(made * points));
                    tvTotalScore.setText(Integer.toString(totalScore() + points));
                }
            }
        });
        ((Button) findViewById(R.id.btMissShot)).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (canShoot())
                {
                    ++missed;
                    tvMissed.setText(Integer.toString(missed));
                }
            }
        });
        ((Button) findViewById(R.id.btClear)).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvTotalScore.setText(Integer.toString(totalScore() - (made * points)));
                clear();
            }
        });
    }

    public void clear()
    {
        made = missed = 0;
        tvMade.setText("0");
        tvMissed.setText("0");
        tvSubtotal.setText("0");
    }

    private boolean canShoot()
    {
        if (made + missed < MAX_SHOTS || (points == 5 && totalScore() >= MAX_SCORE && missed == 0))
            return true;

        Toast.makeText(getContext(), "Can't shoot more than " + MAX_SHOTS + " shots", Toast.LENGTH_SHORT).show();
        return false;
    }

    public static int extractScore(TextView tv)
    {
        try
        {
            return Integer.parseInt(tv.getText().toString());
        }
        catch (NullPointerException e)
        {
            Log.e("MHC", "Null on extract score");
        }
        catch (NumberFormatException e)
        {
            Log.e("MHC", "Couldn't get score: " + tv.getText().toString());
        }

        return 0;
    }

    private int totalScore()
    {
        return extractScore(tvTotalScore);
    }
}
