package calculate;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CalculatePanel extends JPanel{
	private JButton display;
	private JPanel panel;
	private StringBuffer sb;
	private CalculateModel model;
	
	public CalculatePanel() {
		setLayout(new BorderLayout());
		
		model = new CalculateModel();
		
		sb = new StringBuffer();

		// 初始化显示
		display = new JButton("0");
		display.setEnabled(false);
		
		// BorderLayout为边框布局
		add(display, BorderLayout.NORTH);
		
		// AC键
		JButton AC = new JButton("AC");
		AC.addActionListener(event -> {
			display.setText("0");
			sb.delete(0, sb.length());
		});
		add(AC,BorderLayout.SOUTH);
		
		// 数字监听器
		ActionListener insert = new InsertAction();
		
		// 符号监听器
		ActionListener command = new CommandAction();
		
		// 按钮画板
		panel = new JPanel();
		
		// GridLayout为网格布局，这里设置4*4的网格
		panel.setLayout(new GridLayout(4, 4));
		
		addButton("7", insert);
		addButton("8", insert);
		addButton("9", insert);
		addButton("/", command);
		addButton("4", insert);
		addButton("5", insert);
		addButton("6", insert);
		addButton("*", command);
		addButton("1", insert);
		addButton("2", insert);
		addButton("3", insert);
		addButton("-", command);
		addButton("0", insert);
		addButton(".", command);
		addButton("=", command);
		addButton("+", command);
		add(panel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			CalculatePanel cp = new CalculatePanel();
			JFrame frame = new JFrame();
			frame.add(cp);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocation(200, 100);
			frame.pack();
			frame.setVisible(true);
		});
		
	}
	
	// 创建按钮并将其添加到画板
	public void addButton(String s, ActionListener listener) {
		JButton button = new JButton(s);
		button.addActionListener(listener);
		panel.add(button);
	}
	
 
	private class InsertAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String input = e.getActionCommand();
			sb.append(input);
			display.setText(sb.toString());

		}
		
	}
	
	private class CommandAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if(sb.length() > 0)
			{
				
				if("+-*/".indexOf(sb.charAt(sb.length() - 1)) >= 0) 
				{
					sb.deleteCharAt(sb.length() - 1);
					sb.append(command);
					display.setText(sb.toString());
					if(command.equals("="))
					{
						model.translate(sb.toString());
						display.setText(sb.toString() + model.calculate());
						sb.delete(0, sb.length());
					}
				}
				else if(command.equals("="))
				{
					sb.append(command);
					model.translate(sb.toString());
					display.setText(sb.toString() + model.calculate());
					sb.delete(0, sb.length());
					
				}
				else 
				{
					
					sb.append(command);
					display.setText(sb.toString());
					
				}
				
			}
			else if(command.equals("-"))
			{
				sb.append(command);
				display.setText(sb.toString());
				
			}
			
		}
		
	}
	
	
}
