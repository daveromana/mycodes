package com.huangxu.shudugame;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class KeyDialog extends Dialog {

	private final View keys[] = new View[9];
	private final int used[];
	private MyView shuduView ;
	int x ;
	int y ;
	public KeyDialog(Context context, int[] used,int x,int y) {
		super(context);
		this.used=used;
		shuduView = new MyView(context);
		this.x=x;
		this.y=y;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Ñ¡Ôñ¿ò");
		setContentView(R.layout.keypad);
		findView();
		for (int i = 0; i < used.length; i++) {
			if(used[i]!=0){
				keys[used[i]-1].setVisibility(View.INVISIBLE);
			}
		}
		setLiseners();
	}
	private void findView() {
		keys[0]=findViewById(R.id.keypad_1);
		keys[1]=findViewById(R.id.keypad_2);
		keys[2]=findViewById(R.id.keypad_3);
		keys[3]=findViewById(R.id.keypad_4);
		keys[4]=findViewById(R.id.keypad_5);
		keys[5]=findViewById(R.id.keypad_6);
		keys[6]=findViewById(R.id.keypad_7);
		keys[7]=findViewById(R.id.keypad_8);
		keys[8]=findViewById(R.id.keypad_9);
		
	}
	private void setLiseners() {
		for (int i = 0; i < keys.length; i++) {
		final int t=i+1;
		keys[i].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				returnResult(t);
				
			}

		});
		}	
	}
	private void returnResult(int t) {
		shuduView.setSelectTile(x,y,t);
		dismiss();
		
	}
}
