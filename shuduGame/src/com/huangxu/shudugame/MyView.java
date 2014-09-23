package com.huangxu.shudugame;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MyView extends View {

	private float width ;
	private float height ;
	Game game = new Game();
	public MyView(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.width = w/9f;
		this.height = h/9f ;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		Paint backgroudPaint = new Paint();
		//…Ë÷√ª≠± —’…´
		backgroudPaint.setColor(getResources().getColor(R.color.shudu_backgroud));
		//ª≠”Œœ∑±≥æ∞
		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroudPaint);
		
		Paint darkPaint = new Paint();
		darkPaint.setColor(getResources().getColor(R.color.shudu_dark));
		
		Paint hilitePaint = new Paint();
		hilitePaint.setColor(getResources().getColor(R.color.shudu_hilite));
		
		Paint lightPaint = new Paint();
		lightPaint.setColor(getResources().getColor(R.color.shudu_light));
		
		//ªÊ÷∆9π¨∏Ò
		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, i*height, getWidth(), i*height, lightPaint);
			canvas.drawLine(0, i*height+1, getWidth(), i*height+1, hilitePaint);
			canvas.drawLine(i*width, 0, i*width, getHeight(), lightPaint);
			canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hilitePaint);
			
		}
		for (int i = 0; i < 9; i++) {
			if(i%3!=0){
				continue ;
			}
			canvas.drawLine(0, i*height, getWidth(), i*height, darkPaint);
			canvas.drawLine(0, i*height+1, getWidth(), i*height+1, hilitePaint);
			canvas.drawLine(i*width, 0, i*width, getHeight(), darkPaint);
			canvas.drawLine(i*width+1, 0, i*width+1, getHeight(), hilitePaint);
		}
		
		Paint numberPaint = new Paint();
		numberPaint.setColor(Color.BLACK);
		numberPaint.setStyle(Style.STROKE);
		numberPaint.setTextSize(height*0.75f);
		numberPaint.setTextAlign(Paint.Align.CENTER);
		FontMetrics fm = numberPaint.getFontMetrics();
		float x = width/2;
		float y = height/2 - (fm.ascent+fm.descent)/2;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText(game.getTileString(i, j), i*width+x, j*height+y, numberPaint);	
			}
		}
		
		super.onDraw(canvas);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()!=MotionEvent.ACTION_DOWN){
			return super.onTouchEvent(event);
		}
		int selectX=(int)(event.getX()/width);
		int selectY=(int)(event.getY()/height);
		int[] used = game.getUsedByCool(selectX, selectY);
		KeyDialog dailog = new KeyDialog(this.getContext(), used,selectX,selectY); 
		dailog.show();
		return true ;
	}

	public void setSelectTile(int x,int y,int t) {
		if(game.setTileIfValid(x, y,t)){
			invalidate();
		}
		
	}
}
