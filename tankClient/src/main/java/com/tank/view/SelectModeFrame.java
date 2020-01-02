package com.tank.view;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.tank.entity.Chapter;
import com.tank.request.ChapterRequest;
import com.tank.request.CloseClientRequest;
import com.tank.request.CreateRoomRequest;
import com.tank.request.EnterRoomRequest;
import com.tank.request.ExitRoomRequest;
import com.tank.request.JudgeStorageRequest;
import com.tank.request.StartGameRequest;
import com.tank.request.StartGameRequest.GameMode;
import com.tank.view.controller.TankController;

/**
 * 选择模式的界面，人机模式，玩家对战模式的界面
 * 
 * @author Administrator
 *
 */
public class SelectModeFrame extends JFrame {
	private RoomPanel roomPanel; // 房间面板，展示该房间内的所有玩家
	private CreateRoomPanel createRoomPanel; // "创建房间"或者"进入房间"的面板
	private ModePanel modePanel; // 模式选择面板
	private TankPanel tankPanel; // 绘画游戏界面的面板
	private ChapterPanel chapterPanel; // 关卡选择界面

	/**
	 * 将界面切换到选择模式的面板
	 */
	public void enterModePanel() {
		if (tankPanel != null) { // 游戏结束后，从游戏面板进入模式选择面板
			this.remove(tankPanel);
			this.remove(chapterPanel);
		}
		this.remove(createRoomPanel);
		this.add(modePanel);
		this.validate();
		this.repaint();
	}

	/**
	 * 进入选择关卡的界面
	 */
	public void enterSelectChapterPanel(List<Chapter> chapters) {
		this.remove(modePanel);
		chapterPanel.refreshPanel(chapters);
		this.add(chapterPanel);
		this.validate();
		this.repaint();
	}

	/**
	 * 进入玩家对战界面
	 */
	public void enterPVPPanel() {

	}

	/**
	 * 创建房间或者进入房间的面板
	 */
	public void enterCreateRoomPanel() {
		this.remove(modePanel);
		this.remove(roomPanel);
		createRoomPanel.clearRoomId(); // 清除显示房间号的文本框
		this.add(createRoomPanel);
		this.validate();
		this.repaint();
	}

	/**
	 * 切换至tankPanel面板
	 */
	public void enterGame() {
		System.out.println("enterGame");
		this.remove(roomPanel);
		this.removeKeyListener(tankPanel);
		tankPanel = new TankPanel();
		this.addKeyListener(tankPanel);
		this.add(tankPanel);
		this.requestFocus(); // JFrame需要获取焦点才可以监听键盘事件
		tankPanel.repaint();
		this.validate();
		this.repaint();
	}

	/**
	 * 开始游戏
	 */
	public void startGame() {
		StartGameRequest reequest = new StartGameRequest(TankController.roomId);
		reequest.setGameMode(GameMode.PVP);
		TankController.channel.writeAndFlush(reequest);
	}

	/**
	 * 更新该房间内的玩家名单
	 * 
	 * @param ids
	 */
	public void updateRoomPanel(List<String> ids, final String roomId) {
		SelectModeFrame.this.remove(createRoomPanel);
		SelectModeFrame.this.add(roomPanel);
		roomPanel.setPlayerIdText(roomId, ids);
		roomPanel.validate();
		roomPanel.repaint();
		this.validate();
		this.repaint();
	}

	public SelectModeFrame() {
		roomPanel = new RoomPanel();
		createRoomPanel = new CreateRoomPanel();
		modePanel = new ModePanel();
		chapterPanel=new ChapterPanel();
		this.add(modePanel);
		this.setSize(610, 630);
		this.setTitle("MyTankGame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

		// 显示器屏幕大小
		Dimension screenSizeInfo = Toolkit.getDefaultToolkit().getScreenSize();
		int leftTopX = ((int) screenSizeInfo.getWidth() - this.getWidth()) / 2;
		int leftTopY = ((int) screenSizeInfo.getHeight() - this.getHeight()) / 2;
		// 设置显示的位置在屏幕中间
		this.setLocation(leftTopX, leftTopY);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.out.println(TankController.roomId);
				CloseClientRequest request = new CloseClientRequest(TankController.roomId, TankController.tankId);
				TankController.channel.writeAndFlush(request);
				// TankController.channel.close();
			}
		});
	}

	private class RoomPanel extends JPanel {
		private JButton startGameBt; // 开始游戏的按钮
		private JButton back;
		private JPanel roomInfoPanel; // 显示房间号和玩家id的面板
		private JTextArea roomInfoText;

		public RoomPanel() {
			startGameBt = new JButton("开始游戏");
			startGameBt.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					startGame();
				}
			});
			back = new JButton("返回");
			back.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// 退出该房间，给服务端发送请求
					TankController.channel
							.writeAndFlush(new ExitRoomRequest(TankController.roomId, TankController.tankId));
					TankController.roomId = null; // 将房间号置为null
					enterCreateRoomPanel();
				}
			});
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel panel1 = new JPanel();
			panel1.add(back);
			panel1.add(startGameBt);
			roomInfoPanel = new JPanel();
			roomInfoText = new JTextArea(50, 50);
			roomInfoPanel.add(roomInfoText);
			this.add(panel1);
			this.add(roomInfoPanel);
		}

		public void setPlayerIdText(String roomId, List<String> ids) {
			roomInfoText.setText("");
			roomInfoText.append("房间号：" + roomId + "\n");
			roomInfoText.append("\n房间内玩家：\n");
			for (String id : ids) {
				TextField text = new TextField();
				text.setEditable(false);
				text.setText(id);
				roomInfoText.append(id + "\n");
			}
		}

	}

	/**
	 * 
	 * 创建房间或者选择房间的面板
	 * 
	 * @author Administrator
	 *
	 */
	private class CreateRoomPanel extends JPanel {
		private JButton createRoomBt; // 创建新的房间
		private JLabel roomIdLabel;
		private JTextField roomIdText;
		private JButton enterRoomBt; // 进入房间的按钮
		private JButton back; // 返回界面

		/**
		 * 清除展示房间号的文本框的内容
		 */
		public void clearRoomId() {
			roomIdText.setText("");
		}

		public CreateRoomPanel() {
			createRoomBt = new JButton("创建新的房间");
			createRoomBt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CreateRoomRequest request = new CreateRoomRequest(TankController.tankId);
					TankController.channel.writeAndFlush(request);
				}
			});
			back = new JButton("返回");
			back.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					enterModePanel();
				}
			});
			roomIdLabel = new JLabel("输入房间号");
			roomIdText = new JTextField();
			roomIdText.setColumns(20);
			enterRoomBt = new JButton("进入房间");
			enterRoomBt.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (roomIdText.getText().trim().equals("")) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(createRoomPanel, "请输入房间号", "请输入房间号", JOptionPane.ERROR_MESSAGE);
					} else {
						EnterRoomRequest request = new EnterRoomRequest(roomIdText.getText(), TankController.tankId);
						TankController.channel.writeAndFlush(request);
					}
				}
			});
			JPanel panel1 = new JPanel();
			panel1.add(back);
			panel1.add(createRoomBt);
			JPanel panel2 = new JPanel();
			panel2.add(roomIdLabel);
			panel2.add(roomIdText);
			panel2.add(enterRoomBt);
			// this.add(back);
			// this.add(createRoomBt);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(panel1);
			this.add(panel2);
			// this.add(roomIdLabel);
			// this.add(roomIdText);
			// this.add(enterRoomBt);
		}
	}

	/**
	 * 游戏模式选择面板
	 *
	 */
	private class ModePanel extends JPanel {
		private JButton pvc;// 人机按钮
		private JButton pvp;// 玩家对战按钮

		public ModePanel() {
			pvc = new JButton("人机对战");
			pvc.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					//请求关卡信息
					TankController.channel.writeAndFlush(new ChapterRequest());
				}
			});
			pvp = new JButton("玩家对战");
			pvp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) { // 请求一个房间号
					enterCreateRoomPanel();
				}
			});
			this.add(pvc);
			this.add(pvp);
		}
	}

	// 选择关卡的界面
	private class ChapterPanel extends JPanel {
		public void refreshPanel(List<Chapter> chapters) {
			this.removeAll();
			for (Chapter chapter : chapters) {
				final int chapterId=chapter.getId();
				JButton bt = new JButton(chapter.getName());
				bt.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JudgeStorageRequest request=new JudgeStorageRequest();
						request.setTankId(TankController.tankId);
						request.setMapId(chapterId);
						TankController.channel.writeAndFlush(request);
					}
				});
				ChapterPanel.this.add(bt);
			}
		}
	}

	public RoomPanel getRoomPanel() {
		return roomPanel;
	}

	public void setRoomPanel(RoomPanel roomPanel) {
		this.roomPanel = roomPanel;
	}

	public CreateRoomPanel getCreateRoomPanel() {
		return createRoomPanel;
	}

	public void setCreateRoomPanel(CreateRoomPanel createRoomPanel) {
		this.createRoomPanel = createRoomPanel;
	}

	public ModePanel getModePanel() {
		return modePanel;
	}

	public void setModePanel(ModePanel modePanel) {
		this.modePanel = modePanel;
	}

	public TankPanel getTankPanel() {
		return tankPanel;
	}

	public void setTankPanel(TankPanel tankPanel) {
		this.tankPanel = tankPanel;
	}

	public ChapterPanel getChapterPanel() {
		return chapterPanel;
	}

	public void setChapterPanel(ChapterPanel chapterPanel) {
		this.chapterPanel = chapterPanel;
	}
	

}
