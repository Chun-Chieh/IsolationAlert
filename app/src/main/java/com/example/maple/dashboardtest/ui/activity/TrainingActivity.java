package com.example.maple.dashboardtest.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.ui.fragment.TrainingFragment;

import static android.view.View.GONE;
import static com.example.maple.dashboardtest.util.convertDpToPixel;
import static com.example.maple.dashboardtest.util.getNavigationBarHeight;

public class TrainingActivity extends AppCompatActivity {
    private FloatingActionButton mFAB;
    private Fragment mFragment;
    private FrameLayout mFragmentContainer;
    private Button mButtonTrainingDone;
    private TextView mTextViewExplanation, mTextViewTitle;

    private Animator mCircularReveal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        mFragmentContainer = findViewById(R.id.frame_training_container);

        mTextViewTitle = findViewById(R.id.text_view_training_title);
        mTextViewExplanation = findViewById(R.id.text_view_training_explanation);

        mFAB = findViewById(R.id.fab_cr);
        mFAB.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorAccent)));
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment();
            }
        });
        setViewMargin(mFAB, 16);


        mButtonTrainingDone = findViewById(R.id.btn_training_done);
        setViewMargin(mButtonTrainingDone, 0);
        // todo: set the btn visibility and onclick event
        mButtonTrainingDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "btn pressed", Toast.LENGTH_SHORT)
                        .show();
//                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    public void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void loadFragment() {
        mFragment = new TrainingFragment();
        getFragmentManager().beginTransaction().replace(mFragmentContainer.getId(), mFragment).commit();
        revealFragmentContainer(mFAB, mFragmentContainer);
    }

    private void revealFragmentContainer(final View clickedView, final View fragmentContainer) {
        prepareCircularReveal(clickedView, fragmentContainer);
        clickedView.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setInterpolator(null) // default interpolator
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mTextViewTitle.setVisibility(GONE);
                        mTextViewExplanation.setVisibility(GONE);
                        mFAB.setVisibility(GONE);

                        fragmentContainer.setVisibility(View.VISIBLE);
                        mCircularReveal.start();

                        // todo: put this line at desired location
                        mButtonTrainingDone.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .start();
    }

    private void prepareCircularReveal(View startView, View targetView) {
        int centerX = (startView.getLeft() + startView.getRight()) / 2;
        int centerY = (startView.getTop() + startView.getBottom()) / 2;
        float finalRadius = (float) Math.hypot((double) centerX, (double) centerY);
        mCircularReveal = ViewAnimationUtils.createCircularReveal(targetView, centerX, centerY, 0, finalRadius);
        mCircularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCircularReveal.removeListener(this);
            }
        });
    }


    /**
     * Setup the margins for the views in Training Activity in pixels
     * left: 0, right: 0, top: 0, bottom: navigation bar height + 16dp (converted to px)
     *
     * @param view that needs to setup the margins
     */
    private void setViewMargin(View view, int dp){
        int navHeight = getNavigationBarHeight(this);
        int margin = (int) convertDpToPixel(dp, this); // Converts dip into its equivalent px
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginParams.setMargins(0, 0, 0, navHeight + margin);
    }
}
