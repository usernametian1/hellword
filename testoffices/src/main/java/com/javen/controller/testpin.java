package com.javen.controller;

import java.util.Scanner;

public class testpin {
     int maxCount=0;
	
	public static void main(String[] args) {
		int acount = 10;
		int count = 0;
		double bottlecap = 0;//瓶盖
	double bottle = 0;  //空瓶子
		
		
		for(int i =0;i<acount;i++){
			if(i!=0 && i%2== 0){
				count++;
				bottlecap++;
				bottle++;
				System.out.println("啤酒"+count);
			System.out.println("瓶盖"+bottlecap);
				System.out.println("空瓶子"+bottle);
			}
			if(bottlecap != 0 && bottlecap%4 == 0){
				count++;
				bottlecap = bottlecap-4+1;
				bottle++;
			}
			if(bottle!=0 && bottle%2 == 0){
				count++;
				bottle = bottle-2+1;
				bottlecap++;
			}
		}		
		System.out.println(count);
		
		
		testpin tes = new testpin();
		tes.recursion(10, 0, 0, 0);
		System.out.println("总数:" + tes.maxCount);
		
		int adds = all+tes.drink(5, 5);
		System.out.println("一共喝了"+adds);
	}
	
	
	
	public void recursion(int money, int cap, int bottle, int count) {
		if (cap < 4 && bottle < 2 && money < 2) {
				maxCount = count;
			return;
		}
		if (cap >= 4) {
			recursion(money, cap - 4 + 1, bottle + 1, count + 1);
		}
		if (bottle >= 2) {
			recursion(money, cap + 1, bottle - 2 + 1, count + 1);
		}
		if (money >= 2) {
			recursion(money - 2, cap + 1, bottle + 1, count + 1);
		}
		return;
	}
	
	public static int all = 5;	
	public static int leftBottle;	
	public static int leftTop;
	
//	public int drenk(int bottle , int top){
//		if(bottle>=2 || top >=4){
//			 ltop = (top/4)+(top%4)+(bottle/2);
//			 lbottle=(bottle/2)+(bottle%2)+(top/4);
//			 return (bottle/2) + (top/4)+drenk(lbottle,ltop);
//		}
//		return 0;
//		
//	}
	public static int drink(int bottle, int top) {		
	if(bottle >=2 || top >=4){			
		leftBottle = (bottle/2)+(bottle%2)+(top/4);			
		leftTop = (top/4) + (top%4) + (bottle/2);			
		return  (bottle/2) + (top/4) + drink(leftBottle,leftTop);		
	}		
	 return 0; 	
    }
	 
    
}
	


