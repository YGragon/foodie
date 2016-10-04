package com.dongxi.foodie.dao;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.dongxi.foodie.R;

import java.util.HashMap;

/**
 * 声音所需工具
 * @author wangyue
 * */
public class NeedForSound {

	/** 摇一摇需要的声音池view */
	private  SoundPool sndPool = null;

	private HashMap<Integer, Integer> soundPoolMap = null;
	
	private static NeedForSound needForSound = null;

	public static NeedForSound getInstance() {
		if (needForSound == null) {
			needForSound = new NeedForSound();
		} 
		return needForSound;
	}

	/** 添加声音 */
	public  void addSound(final Context context) {
		soundPoolMap = new HashMap<Integer, Integer>();
		sndPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 5);
		new Thread() {
			public void run() {
				try {
					soundPoolMap.put(0, sndPool.load(context.getResources().openRawResourceFd(R.raw.shake_sound_male), 1));
					soundPoolMap.put(1, sndPool.load(context.getResources().openRawResourceFd(R.raw.shake_match), 1));
					soundPoolMap.put(2, sndPool.load(context.getResources().openRawResourceFd(R.raw.shake_nomatch), 1));
				} catch (NullPointerException e) {
				}
			}
		}.start();
	}

	/** 播放摇一摇开始声音 */
	public  void playStartSound() {
		try {
			sndPool.play(soundPoolMap.get(0), (float) 1, (float) 1, 0, 0, (float) 1.2);// 播放摇一摇过程声音
		} catch (NullPointerException e) {
		} catch (IndexOutOfBoundsException e) {
		}
	}

	/** 播放摇一摇结束声音 */
	public  void playEndSound() {
		try {
			sndPool.play(soundPoolMap.get(1), (float) 1, (float) 1, 0, 0, (float) 1.0);// 播放摇一摇结果声音
		} catch (NullPointerException e) {
		} catch (IndexOutOfBoundsException e) {
		}
	}

	/** 什么都没摇到 */
	public  void playNothingSound() {
		try {
			sndPool.play(soundPoolMap.get(2), (float) 1, (float) 1, 0, 0, (float) 1.0);// 播放摇一摇结果声音
		} catch (NullPointerException e) {
		} catch (IndexOutOfBoundsException e) {
		}
	}
}
