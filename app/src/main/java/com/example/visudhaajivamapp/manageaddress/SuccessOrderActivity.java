package com.example.visudhaajivamapp.manageaddress;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.addedbysam.AllSubCategory;
import com.example.visudhaajivamapp.addedbysam.CartListActivity;
import com.example.visudhaajivamapp.addedbysam.ProductCategory;
import com.example.visudhaajivamapp.addedbysam.ProductDetail;
import com.example.visudhaajivamapp.addedbysam.ProductSubCategory;
import com.suddenh4x.ratingdialog.AppRating;
import com.suddenh4x.ratingdialog.preferences.RatingThreshold;

public class SuccessOrderActivity extends AppCompatActivity {
    public static SuccessOrderActivity successOrderActivity;
    LottieAnimationView lottieAnimationView;
    LinearLayout successLayout;
    Button continueShopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_order);
        successOrderActivity = SuccessOrderActivity.this;
        continueShopping = findViewById(R.id.continue_shopping_id);
        lottieAnimationView = findViewById(R.id.animationViewId);
        successLayout = findViewById(R.id.successLayoutId);

        Transition transitionLottie = new Fade();
        transitionLottie.setDuration(600);
        transitionLottie.addTarget(R.id.animationViewId);
        Transition transitionSuccess = new Slide();
        transitionSuccess.setDuration(600);
        transitionSuccess.addTarget(R.id.successLayoutId);
//        try {
//            Thread.sleep(1000);
//            lottieAnimationView.playAnimation();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        lottieAnimationView.playAnimation();

        //Lottie Animation part
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                TransitionManager.beginDelayedTransition((ViewGroup) lottieAnimationView.getParent(),transitionLottie);
                lottieAnimationView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });

        //        Lottie Transition Part
        transitionLottie.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) { }

            @Override
            public void onTransitionEnd(Transition transition) {
                TransitionManager.beginDelayedTransition((ViewGroup)successLayout.getParent(),transitionSuccess);
                successLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTransitionCancel(Transition transition) { }

            @Override
            public void onTransitionPause(Transition transition) { }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

        new AppRating.Builder(this)
                .setMinimumLaunchTimes(0)
                .setMinimumDays(0)
                .setMinimumLaunchTimesToShowAgain(1)
                .setMinimumDaysToShowAgain(1)
                .setRatingThreshold(RatingThreshold.FOUR)
                .showIfMeetsConditions();

        //finishing all recent activities and moving to Dashboard Main
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishRecentActivities();
                if(SuccessOrderActivity.successOrderActivity!=null){
                    SuccessOrderActivity.successOrderActivity.finish();
                    SuccessOrderActivity.successOrderActivity = null;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    private void finishRecentActivities(){
        if(ProductCategory.productCategory!=null){
            ProductCategory.productCategory.finish();
            ProductCategory.productCategory = null;
        }
        if(AllSubCategory.allSubCategory!=null){
            AllSubCategory.allSubCategory.finish();
            AllSubCategory.allSubCategory = null;
        }
        if(ProductSubCategory.productSubCategory!=null){
            ProductSubCategory.productSubCategory.finish();
            ProductSubCategory.productSubCategory = null;
        }
        if(ProductDetail.productDetail!=null){
            ProductDetail.productDetail.finish();
            ProductDetail.productDetail = null;
        }
        if(CartListActivity.cartListActivity!=null){
            CartListActivity.cartListActivity.finish();
            CartListActivity.cartListActivity = null;
        }
        if(AddressDetailsActivity.addressDetailsActivity!=null){
            AddressDetailsActivity.addressDetailsActivity.finish();
            AddressDetailsActivity.addressDetailsActivity = null;
        }
        if(ConfirmOrderActivity.confirmOrderActivity!=null){
            ConfirmOrderActivity.confirmOrderActivity.finish();
            ConfirmOrderActivity.confirmOrderActivity = null;
        }
    }
}