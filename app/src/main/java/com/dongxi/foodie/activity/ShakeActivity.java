package com.dongxi.foodie.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.dongxi.foodie.R;


/**
 * 摇一摇功能
 */
public class ShakeActivity extends AppCompatActivity {

    private ImageView up;
    private ImageView down;
    //上一次晃动手机的时间
    private long lastTime;
    private SoundPool soundPool;
    private int sound1;
    private Vibrator vibrator;
    private SensorEventListener listener = new SensorEventListener() {
        //当手机的加速度发生变化时调用
        @Override
        public void onSensorChanged(SensorEvent event) {
            //获取手机在不同方向上加速度的变化
            float valuesX = Math.abs(event.values[0]);
            float valuesY = Math.abs(event.values[1]);
            float valuesZ = Math.abs(event.values[2]);

            if (valuesX > 17 || valuesY > 17 || valuesZ > 17) {
                startAnimation();
                playSound();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private SensorManager sensorManager;

    private void playSound() {
        //1.声音的id
        //2.3.表示左右声道的音量
        //4.优先级
        //5.是否循环
        //6.声音播放速率
        soundPool.play(sound1, 1, 1, 0, 0, 1);
        //手机震动
        //1.表示震动的节奏off/on/off/on/off/on......
        //2.表示是否重复震动，-1表示不重复
        vibrator.vibrate(new long[]{100, 200, 100, 200, 100, 200}, -1);
    }

    private void startAnimation() {
        //如果两次晃动手机的时间小于1秒，则只执行一次动画
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastTime < 1000) {
            return;
        }
        lastTime = currentTimeMillis;
        AnimationSet upSet = new AnimationSet(true);
        TranslateAnimation upUp = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF,
                0, TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, -1);
        upUp.setDuration(1000);
        TranslateAnimation upDown = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF,
                0, TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 1);
        upDown.setDuration(1000);
        upDown.setStartOffset(1000);
        upSet.addAnimation(upUp);
        upSet.addAnimation(upDown);
        up.startAnimation(upSet);
        AnimationSet downSet = new AnimationSet(true);
        TranslateAnimation downUp = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF,
                0, TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 1);
        downUp.setDuration(1000);
        TranslateAnimation downDown = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF,
                0, TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, -1);
        downDown.setDuration(1000);
        downDown.setStartOffset(1000);
        downSet.addAnimation(downUp);
        downSet.addAnimation(downDown);
        down.startAnimation(downSet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        up = ((ImageView) findViewById(R.id.up));
        down = ((ImageView) findViewById(R.id.down));
        initSensor();
        initSoundPool();
        //获取手机震动服务
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    /**
     * 初始化声音池
     */
    private void initSoundPool() {
        if (Build.VERSION.SDK_INT > 20) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //1.最大并发流数
            builder.setMaxStreams(3);
            AudioAttributes.Builder aaBuilder = new AudioAttributes.Builder();
            aaBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(aaBuilder.build());
            soundPool = builder.build();
        } else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }
        //加载一个音频文件
        sound1 = soundPool.load(this, R.raw.awe, 1);
    }

    /**
     * 初始化传感器
     */
    private void initSensor() {
        //获取到一个传感器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获得一个加速度传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册传感器监听，
        //1.监听器
        //2.加速度传感器
        //3.传感器灵敏度
        //传感器灵敏度分为四级，从上往下灵敏度依次降低
        //SENSOR_DELAY_FASTEST
        //SENSOR_DELAY_GAME
        //SENSOR_DELAY_UI
        //SENSOR_DELAY_NORMAL
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除对加速度传感器的监听
        sensorManager.unregisterListener(listener);
        if (soundPool != null) {
            //声音池释放资源
            soundPool.release();
        }
    }

}
