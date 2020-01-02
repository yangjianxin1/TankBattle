package com.tank.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.tank.request.CloseClientRequest;
import com.tank.request.LoginRequest;
import com.tank.request.RegisterRequest;
import com.tank.view.controller.TankController;

public class LoginFrame extends JFrame {
	private JButton loginBt;// 登陆按钮
	private JButton registerBt;// 注册按钮
	private JLabel jl_1;// 登录的版面
	// private JFrame jf_1;// 登陆的框架
	private JTextField idText;// 用户名
	private JPasswordField passText;// 密码
	private JLabel jl_admin;
	private JLabel jl_password;

	public static void main(String[] args) {
		new LoginFrame();
	}

	public LoginFrame() {
		Font font = new Font("黑体", Font.PLAIN, 20);// 设置字体
		// jf_1 = new JFrame("登陆界面");
		this.setSize(450, 400);
		jl_1 = new JLabel();

		jl_admin = new JLabel("用户名");
		jl_admin.setBounds(20, 50, 60, 50);
		jl_admin.setFont(font);

		jl_password = new JLabel("密码");
		jl_password.setBounds(20, 120, 60, 50);
		jl_password.setFont(font);

		loginBt = new JButton("登陆"); // 更改成loginButton
		loginBt.setBounds(90, 250, 100, 50);
		loginBt.setFont(font);
		loginBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idText.getText().trim().equals("") || passText.getText().trim().equals("")) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "用户名和密码不能为空", "", JOptionPane.ERROR_MESSAGE);
				} else {
					LoginRequest request = new LoginRequest(idText.getText(), new String(passText.getPassword()));
					TankController.channel.writeAndFlush(request);
				}
			}
		});

		registerBt = new JButton("注册");
		registerBt.setBounds(250, 250, 100, 50);
		registerBt.setFont(font);
		registerBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterRequest request = new RegisterRequest(idText.getText(), new String(passText.getPassword()));
				TankController.channel.writeAndFlush(request);
			}
		});

		// 加入文本框
		idText = new JTextField();
		idText.setBounds(150, 50, 250, 50);
		idText.setFont(font);

		passText = new JPasswordField();// 密码输入框
		passText.setEchoChar('*');
		passText.setBounds(150, 120, 250, 50);
		passText.setFont(font);

		jl_1.add(idText);
		jl_1.add(passText);

		jl_1.add(jl_admin);
		jl_1.add(jl_password);
		jl_1.add(loginBt);
		jl_1.add(registerBt);

		this.add(jl_1);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 显示器屏幕大小
		Dimension screenSizeInfo = Toolkit.getDefaultToolkit().getScreenSize();
		int leftTopX = ((int) screenSizeInfo.getWidth() - this.getWidth()) / 2;
		int leftTopY = ((int) screenSizeInfo.getHeight() - this.getHeight()) / 2;
		// 设置显示的位置在屏幕中间
		this.setLocation(leftTopX, leftTopY);
		this.addWindowListener(new WindowAdapter() {	//当窗口关闭的时候
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				CloseClientRequest request = new CloseClientRequest(TankController.roomId, TankController.tankId);
				TankController.channel.writeAndFlush(request);
//				TankController.channel.close();
			}
		});
	}
}
