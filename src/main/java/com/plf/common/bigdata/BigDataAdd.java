package com.plf.common.bigdata;

import java.util.Scanner;

public class BigDataAdd {
	static int N = 100;
	static int a[] = new int[N];
	static int b[] = new int[N];
	static int c[] = new int[N+1];
	static String s1 =  new String();
	static String s2 =  new String();
	Scanner scanner=null;
	
	public static void main(String[] args) {
		BigDataAdd add = new BigDataAdd();
		add.Input();
		add.Add(a, b, c);
		add.Output();
		add.CloseScanner();
	}
	
	private void Output(){
		System.out.println("result=");
		int flag = N;
		while(c[flag] == 0){
			flag--;
			if(flag == -1){
				System.out.print("0");
				return;
			}
		}
		for(int i=flag;i>=0;i--){
			System.out.print(c[i]);
		}
		
		System.out.println();
	}
	
	private void Add(int a[],int b[],int c[]){
		for(int i=0;i<N;i++){
			c[i] = a[i]+b[i];
		}
		
		for(int i=0;i<N;i++){
			c[i+1] += c[i]/10;
			c[i]=c[i]%10;
		}
	}
	
	private void Input(){
		scanner = new Scanner(System.in);
		System.out.println("input two big data:");
		s1 = scanner.nextLine();
		s2 = scanner.nextLine();
		GetDigit(s1,a);
		GetDigit(s2,b);
	}
	
	private static void GetDigit(String s,int a[]){
		int len = s.length();
		for(int i=0;i<len;i++){
			a[i] = s.charAt(len-1-i)-'0';
		}
	}

	private void CloseScanner(){
		if(scanner!=null){
			scanner.close();
		}
	}
}
