package com.spendwell.utils;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Override the RelativeLayout to produce the pull effect of the payFragment
 * 
 * @author Yifei Gao
 * 
 */
public class PullView extends RelativeLayout {

	private Context mContext;
	private Scroller mScroller;
	private int mScreenHeight = 0;
	private int mLastDownY = 0;
	private int mCurrentY = 0;
	private int mDelY = 0;
	private boolean mCloseFlag = false;
	private Handler mHandler;

	public PullView(Context context) {
		super(context);
		mContext = context;
		setUpView();
	}

	public PullView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setUpView();
	}

	public void setUpView() {
		// use BounceInterpolator to produce the bounce effect
		BounceInterpolator interpolator = new BounceInterpolator();
		mScroller = new Scroller(mContext, interpolator);

		WindowManager manager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		mScreenHeight = metrics.heightPixels;
	}

	public void startBounceAnim(int startY, int dy, int duration) {
		mScroller.startScroll(0, startY, 0, dy, duration);
		invalidate();
	}

	public void setHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// record the down position
			mLastDownY = (int) event.getY();
			return true;
		case MotionEvent.ACTION_MOVE:
			mCurrentY = (int) event.getY();
			mDelY = mCurrentY - mLastDownY;
			// calculate the change in Y
			if (mDelY < 0) {
				// if change is smaller than 0, process the scroll
				scrollTo(0, -mDelY);
			}
			break;
		case MotionEvent.ACTION_UP:
			mCurrentY = (int) event.getY();
			mDelY = mCurrentY - mLastDownY;
			if (mDelY < 0) {
				if (Math.abs(mDelY) > mScreenHeight / 2) {
					// if change in Y in lager than half of the screen height,
					// complete the scroll
					startBounceAnim(this.getScrollY(), mScreenHeight, 1000);
					mHandler.obtainMessage(1).sendToTarget();
					mCloseFlag = true;
				} else {
					// set the stroller back to original position
					startBounceAnim(this.getScrollY(), -this.getScrollY(), 1000);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	public void reset() {
		this.setVisibility(View.VISIBLE);
		mCloseFlag = false;
		startBounceAnim(this.getScrollY(), -this.getScrollY(), 1000);
	}

	@Override
	public void computeScroll() {
		// handle the unfinished scroll event
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		} else {
			if (mCloseFlag) {
				this.setVisibility(View.GONE);
			}
		}
	}
}
