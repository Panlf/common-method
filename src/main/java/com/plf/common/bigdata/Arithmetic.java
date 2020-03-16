package com.plf.common.bigdata;

import java.util.Scanner;
import java.util.Stack;

public class Arithmetic {

	//定义符号优先级，前面大后面小
	private static final String[] priorities = {"(","^","*/","+-",")","="};

	//操作符栈
	private Stack<Character> symbol = new Stack<>();
	//操作数栈
	private Stack<Double> number = new Stack<>();
	
	public double caculate(String str){
		//用来临时存放读取的字符
		char tempChar;
		
		//用来临时存放数字字符串（当为多位数时）
		StringBuffer tempNum = new StringBuffer();
	
		//用StringBuffer来操作，提高效率，另外本算法以'='为结束符
		//所以自动加上一个加号
		StringBuffer buff = new StringBuffer(str).append("=");

		//循环开始解析字符串，当字符串解析完，则计算完成
		while(buff.length()!=0){
			tempChar = buff.charAt(0);
			//判断temp，当temp为操作符时
			buff.delete(0, 1);
			if(!isNum(tempChar)){
				//遇到符号，则表示前面的数字字符都放到了tempNum里了，取出数，压栈，并且情况tempNum
				if(!"".equals(tempNum.toString())){
					double num = Double.parseDouble(tempNum.toString());
					number.push(num);
					tempNum.delete(0, tempNum.length());
				}
				
				//如果symbol栈不是空的，说明之前已经有符号入栈了，把当前的符号和它比，如果当前的符号优先级比前面的高，说明要先计算前面的
				//如果比前面的优先级高，则继续把当前符号入栈，下一个循环还会把数入栈
				//如果比前面的高，为什么不计算现在的呢，因为1、你的第二个操作数还没得到 2、你咋知道后面还有没有符号优先级更高呢
				//所以结束符暂时是必要的，它是用来提醒前面进行计算的
				while(!compare(tempChar) && (!symbol.empty())){
					char ope = symbol.peek();
					if(ope!='('){
						symbol.pop();
					}else{
						//括号的不能算的，先结束，等遇到右括号再来搞定它
						break;
					}
					
					//第二个运算符
					double a = number.pop();
					//第一个运算符
					double b = number.pop();
					
					//运算结果
					double result = 0;
					switch (ope) {
					case '+':
						result = b+a;
						break;
					case '-':
						result = b-a;
						break;
					case '*':
						result = b*a;
						break;
					case '/':
						if(a == 0){
							System.out.println("除数不能为0");
							throw new NumberFormatException("除数不能为0");
						}
						result = b/a;
						break;
					case '^':
						result = Math.pow(b, a);
						break;
					default:
						break;
					}
					
					//将结果放入操作数栈
					number.push(result);
				}
				
				
				//如果不是结束符，当前符号入栈
				if(tempChar != '='){
					if(tempChar ==')'){
						//如果当前符号是右括号。不能入栈，而且还要把之前入栈的左括号去掉
						symbol.pop();
					}else{
						symbol.push(tempChar);
					}
				}
			}else{
				//当为非操作符是(数字)
				tempNum = tempNum.append(tempChar);
			}
		}
		return number.pop();
	}

	/**
	 * 比较当前操作符与栈顶元素操作符优先级，如果比栈顶元素优先级高，则返回true，否则返回false
	 * @param tempChar
	 * @return
	 */
	private boolean compare(char tempChar) {
		
		if(symbol.empty()){
			//当为空时，显然，当前优先级最高，返回true
			return true;
		}
		
		char lastChar = symbol.lastElement();
		
		int lastPriority = -1;
		int currentPriority = -1;
		
		for(int i=0;i<priorities.length;i++){
			if(priorities[i].indexOf(lastChar) != -1){
				lastPriority = i;
			}
			
			if(priorities[i].indexOf(tempChar) != -1){
				currentPriority = i;
			}
		}
		
		if(currentPriority == -1){
			System.out.println("存在非法字符"+tempChar);
			throw new RuntimeException("存在非法字符"+tempChar);
		}
		
		if(lastPriority == -1){
			System.out.println("存在非法字符"+lastChar);
			throw new RuntimeException("存在非法字符"+lastChar);
		}
		
		//如果currentPriority小于lastPriority，则当前操作符优先级高
		return currentPriority < lastPriority;
	}

	/**
	 * 判断传入的字符是否是0-9的数字
	 * @param tempChar
	 * @return
	 */
	private boolean isNum(char tempChar) {
		return tempChar >= '0' && tempChar <= '9';
	}
	
	public static void main(String[] args) {
		Arithmetic arithmetic = new Arithmetic();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("输入一个合理的四则运算式子:");
		String str = scanner.next();
		double reuslt =arithmetic.caculate(str);
		System.out.println(reuslt);
		scanner.close();
	}
}

