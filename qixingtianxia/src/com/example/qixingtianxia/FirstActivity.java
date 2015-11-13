package com.example.qixingtianxia;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class FirstActivity extends Activity {
	ImageView r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first);
		r = (ImageView) findViewById(R.id.rela);
		initview();
	}

	private void initview() {
		AnimationSet set = new AnimationSet(false);
		/*//移动
		TranslateAnimation tran=new TranslateAnimation
			(0, 300, 0, -300);
		tran.setDuration(1000);
		tran.setFillAfter(true);*/
		// 旋转
		RotateAnimation roat = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		roat.setDuration(1000);
		roat.setFillAfter(true);
		// 缩放
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1000);
		scale.setFillAfter(true);
		// 透明
		AlphaAnimation alpha = new AlphaAnimation(0, 1);

		alpha.setDuration(1000);
		alpha.setFillAfter(true);

		set.addAnimation(roat);
		set.addAnimation(scale);
		set.addAnimation(alpha);
		//set.addAnimation(tran);
		r.startAnimation(set);
		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				startActivity(new Intent(FirstActivity.this, MainActivity.class));
				finish();
			}
		});

		
	}

}
