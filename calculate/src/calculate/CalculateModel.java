package calculate;

import java.util.ArrayList;
import java.util.Stack;

public class CalculateModel {
	private ArrayList<String> string = new ArrayList<>();
	
	// 得到数字和正确的运算顺序
	public void translate(String s) {
		Stack<String> stack = new Stack<>();
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(".0123456789".indexOf(c) >= 0)
			{
				str.append(c);
				for(i = i + 1 ; i < s.length(); i ++){
					if(".0123456789".indexOf(s.charAt(i)) >= 0) 
					{
						str.append(s.charAt(i));
					}
					else break;	
				}
//				System.out.println(str.toString());
				string.add(str.toString());
				str.delete(0,str.length());
				i --;
			}
			else if("*/".indexOf(c) >= 0)
			{
				
				while(!stack.isEmpty() && "*/".indexOf(stack.peek()) >= 0) 
				{
					string.add(stack.pop());
				}
				stack.push(s.substring(i, i + 1));
			}
			else if("+-".indexOf(c) >= 0)
			{				
					
				while(!stack.isEmpty() && "*/+-".indexOf(stack.peek()) >= 0) 
				{
					string.add(stack.pop());
				}
					stack.push(s.substring(i, i + 1));
			}
//			if(!stack.isEmpty())
//			{
//				System.out.println(stack);
//			}	
		}
		while(!stack.empty()) {
			string.add(stack.pop());
		}
//			System.out.println(string);
//			
	}
	
	// 计算
	public String calculate() {
		Stack<String> stack = new Stack<>();
		for(String s : string) {
//			System.out.println(s);
			if("+-*/".indexOf(s) >= 0)
			{
//				System.out.println("here");
				double number = 0;
				double num1 = Double.parseDouble(stack.pop());
				double num2 = Double.parseDouble(stack.pop());
//				System.out.println("num1="+num1+",num2="+num2);
				if(s.equals("+")) number = num1 + num2;
				else if(s.equals("-")) number = num2 - num1;
				else if(s.equals("*")) number = num2 * num1;
				else if(s.equals("/") && num1 != 0)number = num2 / num1;
				else
				{
					return "不能除以0";
				}
				stack.push(Double.toString(number));
			}
			else stack.push(s);
		}
		return stack.pop();
	}
		
}
