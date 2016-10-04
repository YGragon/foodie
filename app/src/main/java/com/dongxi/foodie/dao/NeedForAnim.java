package com.dongxi.foodie.dao;

import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.dongxi.foodie.activity.ShakeActivity;

/**
 * 动画所需工具
 * 
 * @author wangyue
 * */
public class NeedForAnim {

	private static NeedForAnim needForAnim = null;

	/**展开动画*/
	public static final long OPEN_TIME = 500;
	
	/**关合动画*/
	public static final long CLOSE_TIME = 280;
	
	/**开合动画间隔*/
	public static final long OFFSET_TIME = 800;
	
	/**模拟延迟*/
	public static final long SIMULLATE_DELAY = 400;
	
	public static NeedForAnim getInstance() {
		if (needForAnim == null) {
			needForAnim = new NeedForAnim();
		}
		return needForAnim;
	}

	/** 摇一摇手掌上部分动画 */
	public AnimationSet getUpAnim(Handler handler) {
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-0.8f);
		mytranslateanimup0.setDuration(OPEN_TIME);
		mytranslateanimup0.setAnimationListener(getLineVisibleListener(handler));

		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				+0.8f);
		mytranslateanimup1.setDuration(CLOSE_TIME);
		mytranslateanimup1.setStartOffset(OFFSET_TIME);
		mytranslateanimup1.setInterpolator(new AccelerateInterpolator(1));
		mytranslateanimup1.setAnimationListener(getLineGoneListener(handler));

		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);
		return animup;
	}

	/** 摇一摇手掌下部分动画 */
	public AnimationSet getDownAnim() {
		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				+0.8f);
		mytranslateanimdn0.setDuration(OPEN_TIME);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-0.8f);
		mytranslateanimdn1.setDuration(CLOSE_TIME);
		mytranslateanimdn1.setStartOffset(OFFSET_TIME);
		mytranslateanimdn1.setInterpolator(new AccelerateInterpolator(1));
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		return animdn;
	}

	/** 摇出的结果显示动画 */
	public TranslateAnimation getResultVisibleAnim(final Handler handler) {
		TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.2f, Animation.RELATIVE_TO_SELF, 0f);
		anim.setDuration(400);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				handler.sendEmptyMessage(ShakeActivity.RESULT_ANIM_VISIBLE_END);
			}
		});
		return anim;
	}

	/** 摇出的结果隐藏动画 */
	public TranslateAnimation getResultGoneAnim(final Handler handler) {
		TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 3f);
		anim.setDuration(600);
		anim.setInterpolator(new AccelerateInterpolator(0.8f));
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				handler.sendEmptyMessage(ShakeActivity.RESULT_ANIM_GONE_END);
			}
		});
		return anim;
	}

	/** 手掌动画开始时显示边线 */
	public AnimationListener getLineVisibleListener(final Handler handler) {
		AnimationListener lineAnimVisibleListener = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				handler.sendEmptyMessage(ShakeActivity.HAND_ANIM_START);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

			}
		};

		return lineAnimVisibleListener;
	}

	/** 手掌动画结束时隐藏边线 */
	public AnimationListener getLineGoneListener(final Handler handler) {

		AnimationListener lineAnimGoneListener = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				handler.sendEmptyMessage(ShakeActivity.HAND_ANIM_END);
			}
		};

		return lineAnimGoneListener;
	}

}
