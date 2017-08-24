package xyz.aungpyaephyo.padc.animation.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.animation.R;
import xyz.aungpyaephyo.padc.animation.data.models.AttractionsModel;
import xyz.aungpyaephyo.padc.animation.data.vo.AttractionVO;
import xyz.aungpyaephyo.padc.animation.utils.AppConstants;

/**
 * Created by aung on 7/29/17.
 */

public class AttractionDetailsActivity extends AppCompatActivity {

    private static final String IE_USER_TAP_ATTRACTION_TITLE = "IE_USER_TAP_ATTRACTION_TITLE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_attraction)
    ImageView ivAttraction;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.tv_attraction_desc)
    TextView tvAttractionDesc;

    @BindView(R.id.fab_plus)
    FloatingActionButton fabPlus;

    @BindView(R.id.fab_sms)
    FloatingActionButton fabSms;

    @BindView(R.id.fab_fb_messenger)
    FloatingActionButton fabFbMessenger;

    @BindView(R.id.fab_viber)
    FloatingActionButton fabViber;

    private boolean isOpen = false;

    public static Intent newIntent(Context context, AttractionVO attraction) {
        Intent intent = new Intent(context, AttractionDetailsActivity.class);
        intent.putExtra(IE_USER_TAP_ATTRACTION_TITLE, attraction.getTitle());
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_details);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivAttraction.setTransitionName(getString(R.string.detail_transition_name));
        }

        Intent usedIntent = getIntent();
        Bundle bundle = usedIntent.getExtras();
        if (bundle != null) {
            String attractionTitle = bundle.getString(IE_USER_TAP_ATTRACTION_TITLE);
            AttractionVO tappedAttraction = AttractionsModel.getInstance().getAttractionByTitle(attractionTitle);

            bindData(tappedAttraction);
        }
    }

    @OnClick(R.id.fab_plus)
    public void onTapFabPlus(View view) {
        if (!isOpen) {
            openAnim();
            isOpen = true;
        } else {
            closeAnim();
            isOpen = false;
        }
    }

    private void bindData(AttractionVO tappedAttraction) {
        collapsingToolbarLayout.setTitle(tappedAttraction.getTitle());
        tvAttractionDesc.setText(tappedAttraction.getDesc());

        Glide.with(ivAttraction.getContext())
                .load(AppConstants.BASE_IMAGE_PATH + tappedAttraction.getImages()[0])
                .placeholder(R.drawable.placeholder_attraction_image)
                .into(ivAttraction);
    }

    private void openAnim() {
        ObjectAnimator objAnimRotation = ObjectAnimator.ofFloat(fabPlus, "rotation", 720f, 0f);
        objAnimRotation.setDuration(600);
        objAnimRotation.setInterpolator(new AccelerateInterpolator());
        objAnimRotation.start();

        ObjectAnimator objAnimCallFW = ObjectAnimator.ofFloat(fabSms, "y", fabSms.getY(), fabSms.getY() - 310f);
        objAnimCallFW.setDuration(500);
        objAnimCallFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimCallBW = ObjectAnimator.ofFloat(fabSms, "y", fabSms.getY() - 310, fabSms.getY() - 280f);
        objAnimCallBW.setDuration(100);
        objAnimCallBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asCall = new AnimatorSet();
        asCall.play(objAnimCallBW).after(objAnimCallFW);
        asCall.start();

        ObjectAnimator objAnimFacebookFW = ObjectAnimator.ofFloat(fabViber, "x", fabViber.getX(), fabViber.getX() - 310f);
        objAnimFacebookFW.setDuration(500);
        objAnimFacebookFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimFacebookBW = ObjectAnimator.ofFloat(fabViber, "x", fabViber.getX() - 310, fabViber.getX() - 280f);
        objAnimFacebookBW.setDuration(100);
        objAnimFacebookBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asFacebook = new AnimatorSet();
        asFacebook.play(objAnimFacebookBW).after(objAnimFacebookFW);
        asFacebook.start();

        ObjectAnimator objAnimMapXFW = ObjectAnimator.ofFloat(fabFbMessenger, "x", fabFbMessenger.getX(), fabFbMessenger.getX() - 180f);
        objAnimMapXFW.setDuration(500);
        objAnimMapXFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYFW = ObjectAnimator.ofFloat(fabFbMessenger, "y", fabFbMessenger.getY(), fabFbMessenger.getY() - 180f);
        objAnimMapYFW.setDuration(500);
        objAnimMapYFW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapFW = new AnimatorSet();
        asMapFW.play(objAnimMapYFW).with(objAnimMapXFW);
        asMapFW.start();

        ObjectAnimator objAnimMapXBW = ObjectAnimator.ofFloat(fabFbMessenger, "x", fabFbMessenger.getX() - 180f, fabFbMessenger.getX() - 160f);
        objAnimMapXBW.setDuration(100);
        objAnimMapXBW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYBW = ObjectAnimator.ofFloat(fabFbMessenger, "y", fabFbMessenger.getY() - 180f, fabFbMessenger.getY() - 160f);
        objAnimMapYBW.setDuration(100);
        objAnimMapYBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapBW = new AnimatorSet();
        asMapBW.play(objAnimMapYBW).with(objAnimMapXBW);
        asMapBW.setStartDelay(500);
        asMapBW.start();
    }

    private void closeAnim() {
        ObjectAnimator objAnimRotation = ObjectAnimator.ofFloat(fabPlus, "rotation", 0, 720f);
        objAnimRotation.setDuration(600);
        objAnimRotation.setInterpolator(new AccelerateInterpolator());
        objAnimRotation.start();

        ObjectAnimator objAnimCallFW = ObjectAnimator.ofFloat(fabSms, "y", fabSms.getY(), fabSms.getY() - 30f);
        objAnimCallFW.setDuration(100);
        objAnimCallFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimCallBW = ObjectAnimator.ofFloat(fabSms, "y", fabSms.getY() - 30, fabSms.getY() + 280f);
        objAnimCallBW.setDuration(500);
        objAnimCallBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asCall = new AnimatorSet();
        asCall.play(objAnimCallBW).after(objAnimCallFW);
        asCall.start();

        ObjectAnimator objAnimFacebookFW = ObjectAnimator.ofFloat(fabViber, "x", fabViber.getX(), fabViber.getX() - 30f);
        objAnimFacebookFW.setDuration(100);
        objAnimFacebookFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimFacebookBW = ObjectAnimator.ofFloat(fabViber, "x", fabViber.getX() - 30, fabViber.getX() + 280f);
        objAnimFacebookBW.setDuration(500);
        objAnimFacebookBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asFacebook = new AnimatorSet();
        asFacebook.play(objAnimFacebookBW).after(objAnimFacebookFW);
        asFacebook.start();

        ObjectAnimator objAnimMapXFW = ObjectAnimator.ofFloat(fabFbMessenger, "x", fabFbMessenger.getX(), fabFbMessenger.getX() - 20);
        objAnimMapXFW.setDuration(100);
        objAnimMapXFW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYFW = ObjectAnimator.ofFloat(fabFbMessenger, "y", fabFbMessenger.getY(), fabFbMessenger.getY() - 20);
        objAnimMapYFW.setDuration(100);
        objAnimMapYFW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapFW = new AnimatorSet();
        asMapFW.play(objAnimMapYFW).with(objAnimMapXFW);
        asMapFW.start();

        ObjectAnimator objAnimMapXBW = ObjectAnimator.ofFloat(fabFbMessenger, "x", fabFbMessenger.getX() - 20f, fabFbMessenger.getX() + 160f);
        objAnimMapXBW.setDuration(500);
        objAnimMapXBW.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator objAnimMapYBW = ObjectAnimator.ofFloat(fabFbMessenger, "y", fabFbMessenger.getY() - 20f, fabFbMessenger.getY() + 160f);
        objAnimMapYBW.setDuration(500);
        objAnimMapYBW.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet asMapBW = new AnimatorSet();
        asMapBW.play(objAnimMapYBW).with(objAnimMapXBW);
        asMapBW.setStartDelay(100);
        asMapBW.start();
    }
}
