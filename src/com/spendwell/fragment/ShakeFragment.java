package com.spendwell.fragment;

import com.example.budgetwell.R;
import com.spendwell.activity.AccountListActitivy;
import com.spendwell.utils.ShakeListener;
import com.spendwell.utils.ShakeListener.onShakeListener;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Fragment that is obtained in the MainActivity's ViewPager, contains
 * onShakeListener which Start AccountListActivity and show user's account
 * details
 * 
 * @author Yifei Gao
 * 
 */
public class ShakeFragment extends Fragment {
	private ShakeListener mShakeListener = null;
	private Vibrator mVibrator;
	private SoundPool soundPool;
	private ImageView background;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shake_activity, null);
		mVibrator = (Vibrator) getActivity().getApplication().getSystemService(
				Context.VIBRATOR_SERVICE);
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(getActivity(), R.raw.cash, 1);
		mShakeListener = new ShakeListener(getActivity());
		// initialized the onShakeListener
		background = (ImageView) view.findViewById(R.id.background);

		mShakeListener.setOnShakeListener(new onShakeListener() {

			@Override
			public void onShake() {
				mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1);
				mShakeListener.stop();
				// stop the listener to avoid more than one event
				// play the vibrate effect

				// play sound effect
				soundPool.play(1, 1, 1, 0, 0, 1);
				// start the vibrate animation
				startAnim();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						Intent it = new Intent(getActivity(),
								AccountListActitivy.class);
						startActivityForResult(it, 100);
					}
				}, 1500);
			}
		});
		return view;
	}

	@Override
	public void onDestroy() {
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
		super.onDestroy();
	}

	// vibrate animation that shake the imageView
	public void startAnim() {
		TranslateAnimation anim = new TranslateAnimation(0, 10, 0, 0);
		anim.setRepeatMode(TranslateAnimation.RESTART);
		anim.setInterpolator(new CycleInterpolator(5f));
		anim.setDuration(1000);
		background.startAnimation(anim);
	}

	// handle the result when user turn off the AccountListActivity, turn the
	// listener back on
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 200) {
			mShakeListener.start();
			// turn on the listener again
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
