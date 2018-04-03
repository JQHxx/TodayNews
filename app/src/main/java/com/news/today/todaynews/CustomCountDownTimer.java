package com.news.today.todaynews;

import android.os.Handler;

public class CustomCountDownTimer implements Runnable{
	private static final long PASS_TIME = 1000; 
	private Handler handler = new Handler();
	private int timeSecond;
	private ICountDownHandler countDownHandler;
	private boolean isRun =false;
	
	/**
	 * 计时器
	 * @param time 外部传时间(秒)
	 * @param countDownHandler
	 * */
	public CustomCountDownTimer(int time, ICountDownHandler countDownHandler){
		this.timeSecond = time;
		this.countDownHandler = countDownHandler;
		start();
	}
	
	@Override
	public void run() {
		if(isRun){
			if(countDownHandler != null){
				countDownHandler.onTicker(timeSecond);
			}
			if (timeSecond == 0) {
				cancel();
				if(countDownHandler != null){
					countDownHandler.onFinish();
				}
			} else {
				timeSecond--;
				if (handler != null) {
					handler.postDelayed(this, PASS_TIME);
				}
			}
		}
	}
	
	private void start(){
		isRun = true;
		if (handler != null) {
			handler.post(this);
		}
	}
	
	public void cancel(){
		isRun = false;
		if (handler != null) {
			handler.removeCallbacks(this);
		}
	}
	
	public void reStart(){
		start();
	}
	
	public void release(){
		isRun = false;
		handler = null;
	}
	
	public boolean isStillCountDown(){
		if(isRun){
			return true;
		}
		return false;
	}
	
	public int getCurrentTime(){
		return timeSecond;
	}
	
	public interface ICountDownHandler{
		public void onTicker(int time);
		public void onFinish();
	}
}
