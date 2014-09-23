package com.huangxu.shudugame;

import android.util.Log;

public class Game {

	private final String str ="360000000004230800000004200"
		+"070460003820000014500013020"
		+"001900000007048300000000045";
	
	private int[] sudu = new int[9*9];

	//存储已经使用了的数据
	private int[][][] used = new int[9][9][] ;
	public Game() {
		this.sudu = fromPuzzleString(str);
		calculateAllUsedTiles();
	}

	//用于计算所有单元格不可用数据
	private void calculateAllUsedTiles() {
		for (int  i= 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				used[i][j]=calculateUsedTiles(i,j);
			}
		}
	}

	/**
	 * 取出某一单元格中已经不可用得到数据
	 * @param x
	 * @param y
	 * @return
	 */
	public int[] getUsedByCool(int x,int y){
		return used[x][y] ;
	}
	//计算某一单元格中已经不可用的数据
	private int[] calculateUsedTiles(int x ,int y) {
		int[] c = new int[9];
		
		for (int i = 0; i < 9; i++) {
			if(i==y){
				continue ;
			}
			int t = getTile(x, i);
			if(t!=0){
				c[t-1] = t ;
			}
		}
		for (int i = 0; i < 9; i++) {
			if(i==x){
				continue ;
			}
			int t = getTile(i, y);
			if(t!=0){
				c[t-1] = t ;
			}
		}
		int startx = (x/3)*3;
		int starty = (y/3)*3;
		for (int i = startx; i < startx+3; i++) {
			for (int j = starty; j < starty+3; j++) {
				if(i==x&&j==y){
					continue ;
				}
				int t = getTile(i, j); 
				if(t!=0){
					c[t-1] = t ;
				}
			}
		}
		int nused = 0 ;
		for(int t:c){
			if(t!=0){
				nused++ ;
			}
		}
		int[] c1 = new int[nused] ;
		nused = 0 ;
		for(int t:c){
			if(t!=0){
				c1[nused++] = t ;
			}
		}
		return c1 ;
	}

	private int getTile(int x ,int y ){
		return sudu[y*9+x];
	}
	public String getTileString(int x,int y){
		int v = getTile(x, y);
		if(v==0){
			return "";
		}else{
			return String.valueOf(v);
		}
	}
	private int[] fromPuzzleString(String str2) {
		int[] sudu = new int[str2.length()];
		for (int i = 0; i < sudu.length; i++) {
			sudu[i] = str2.charAt(i)-'0';
		}
		return sudu;
	}

	public boolean setTileIfValid(int x, int y, int t) {
		int tiles[] = getUsedTiles(x,y);
		if(t!=0){
			for(int tile:tiles){
				if(tile==t){
					return false ;
				}
			}
		}
		setTitle(x,y,t);
		calculateAllUsedTiles();
		return true;
	}

	private void setTitle(int x, int y, int t) {
		sudu[y*9+x]=t;
	}

	private int[] getUsedTiles(int x, int y) {
		
		return used[x][y];
	}
	
	
}
