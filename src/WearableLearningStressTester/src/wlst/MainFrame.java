package wlst;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import wlst.BackendServer;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import wl.shared.json.packets.ButtonPacket;
import wl.shared.json.packets.data.ButtonData;

import javax.swing.JScrollPane;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtGameInstanceId;
	private JTextField txtTitle;
	private JTextField txtTeamCount;
	private JTextField txtPlayersPerTeam;
	private JTextField txtServerip;
	private JTextField txtPortnumber;
	private XMLParser parser;
	private ServerInfo serverInfo;
	private GameInfo gameInfo;
	private List<PlayerInfo> playerInfo;
	private List<GameStateInfo> gameStateInfo;
	private JTable table;
	private int backendCount = 0;
	private GameTester tester;
	private JTextField txtCounter;
	private LocalDateTime old = null;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
	private FileWriter writer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					frame.setTitle("Wearable Learning Game Engine Stress Tester");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
//		try {
//			writer = new FileWriter("Data.csv");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		        for(PlayerInfo info : playerInfo) {
		        	ByteBuffer buffer = ByteBuffer.allocate(2048);
		    		buffer.putInt(2);
		    		buffer.putInt(gameInfo.getGameId());
		    		info.getBackendServer().putString(info.getPlayerName(), buffer);
		    		info.getBackendServer().write(buffer);
		    		info.getBackendServer().disconnect();
		    		try {
		    			Thread.sleep(100);
		    		} catch (Exception e) {
		    			
		    		}
		        }
//		        try {
//					writer.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		    }
		});
		
		JMenuItem mntmOpenData = new JMenuItem("Open Data");
		mntmOpenData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(MainFrame.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					parser = new XMLParser(file);
					serverInfo = parser.parseServerInfo();
					gameInfo = parser.parseGameInfo();
					playerInfo = parser.parsePlayerInfo();
					gameStateInfo = parser.parseGameStateInfo();
					txtGameInstanceId.setText(String.valueOf(gameInfo.getGameId()));
					txtTitle.setText(String.valueOf(gameInfo.getTitle()));
					txtTeamCount.setText(String.valueOf(gameInfo.getTeamCount()));
					txtPlayersPerTeam.setText(String.valueOf(gameInfo.getPlayersPerTeam()));
					txtServerip.setText(String.valueOf(serverInfo.getServerIp()));
					txtPortnumber.setText(String.valueOf(serverInfo.getPortNumber()));
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					int count = 0;
					for(PlayerInfo info : playerInfo) {
						model.addRow(new Object[]{count++, info.getPlayerName(), info.getTeamNumber(), info.getTeamPlayerNumber(), -1, "", "Disconnected"});
					}
				}
			}
		});
		mnFile.add(mntmOpenData);
		
		JMenuItem mntmSaveData = new JMenuItem("Save Data");
		mnFile.add(mntmSaveData);
		
		JRadioButtonMenuItem rdbtnmntmExit = new JRadioButtonMenuItem("Exit");
		mnFile.add(rdbtnmntmExit);
		
		JMenu mnTest = new JMenu("Test");
		menuBar.add(mnTest);
		
		JMenuItem mntmConnectTest = new JMenuItem("Connect Test");
		mntmConnectTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				old = LocalDateTime.now();
				worker.execute();
				for(PlayerInfo info : playerInfo) {
					BackendServer backend  = new BackendServer(serverInfo.getServerIp(), serverInfo.getPortNumber(), MainFrame.this, backendCount++);
					ByteBuffer buffer = ByteBuffer.allocate(2048);
					buffer.putInt(1);
					backend.putString(info.getPlayerName(), buffer);
					backend.putString("Team " + info.getTeamNumber(), buffer);
					buffer.putInt(gameInfo.getGameId());
					backend.write(buffer);
					backend.read();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setValueAt("Connected", playerInfo.indexOf(info), 6);
					info.setBackendServer(backend);
		    		try {
		    			Thread.sleep(100);
		    		} catch (Exception e) {
		    			
		    		}
				}
			}
		});
		mnTest.add(mntmConnectTest);
		
		JMenuItem mntmDisconnectTest = new JMenuItem("Disconnect Test");
		mntmDisconnectTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(PlayerInfo info : playerInfo) {
		        	ByteBuffer buffer = ByteBuffer.allocate(2048);
		    		buffer.putInt(2);
		    		buffer.putInt(gameInfo.getGameId());
		    		info.getBackendServer().putString(info.getPlayerName(), buffer);
		    		info.getBackendServer().write(buffer);
		    		info.getBackendServer().disconnect();
		    		DefaultTableModel model = (DefaultTableModel) table.getModel();
		    		model.setValueAt("", playerInfo.indexOf(info), 5);
					model.setValueAt("Disconnected", playerInfo.indexOf(info), 6);
		    		try {
		    			Thread.sleep(100);
		    		} catch (Exception e) {
		    			
		    		}
		        }
			}
		});
		mnTest.add(mntmDisconnectTest);
		
		JMenuItem mntmGameTest = new JMenuItem("Game Test");
		mntmGameTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tester = new GameTester(playerInfo);
				tester.start();
			}
		});
		
		JMenuItem mntmReconnectTest = new JMenuItem("Reconnect Test");
		mnTest.add(mntmReconnectTest);
		mnTest.add(mntmGameTest);
		
		JMenuItem mntmStopGameTest = new JMenuItem("Stop Game Test");
		mntmStopGameTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tester.quit();
			}
		});
		mnTest.add(mntmStopGameTest);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblServerIp = new JLabel("server ip");
		GridBagConstraints gbc_lblServerIp = new GridBagConstraints();
		gbc_lblServerIp.anchor = GridBagConstraints.EAST;
		gbc_lblServerIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerIp.gridx = 0;
		gbc_lblServerIp.gridy = 0;
		contentPane.add(lblServerIp, gbc_lblServerIp);
		
		txtServerip = new JTextField();
		GridBagConstraints gbc_txtServerip = new GridBagConstraints();
		gbc_txtServerip.insets = new Insets(0, 0, 5, 5);
		gbc_txtServerip.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtServerip.gridx = 1;
		gbc_txtServerip.gridy = 0;
		contentPane.add(txtServerip, gbc_txtServerip);
		txtServerip.setColumns(10);
		
		JLabel lblPort = new JLabel("portNumber");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 2;
		gbc_lblPort.gridy = 0;
		contentPane.add(lblPort, gbc_lblPort);
		
		txtPortnumber = new JTextField();
		GridBagConstraints gbc_txtPortnumber = new GridBagConstraints();
		gbc_txtPortnumber.insets = new Insets(0, 0, 5, 5);
		gbc_txtPortnumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPortnumber.gridx = 3;
		gbc_txtPortnumber.gridy = 0;
		contentPane.add(txtPortnumber, gbc_txtPortnumber);
		txtPortnumber.setColumns(10);
		
		JLabel lblElapsedTime = new JLabel("elapsed time");
		GridBagConstraints gbc_lblElapsedTime = new GridBagConstraints();
		gbc_lblElapsedTime.anchor = GridBagConstraints.EAST;
		gbc_lblElapsedTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblElapsedTime.gridx = 4;
		gbc_lblElapsedTime.gridy = 0;
		contentPane.add(lblElapsedTime, gbc_lblElapsedTime);
		
		txtCounter = new JTextField();
		txtCounter.setText("0");
		txtCounter.setEditable(false);
		GridBagConstraints gbc_txtCounter = new GridBagConstraints();
		gbc_txtCounter.insets = new Insets(0, 0, 5, 5);
		gbc_txtCounter.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCounter.gridx = 5;
		gbc_txtCounter.gridy = 0;
		contentPane.add(txtCounter, gbc_txtCounter);
		txtCounter.setColumns(10);
		
		JLabel lblLabel = new JLabel("gameInstanceId");
		GridBagConstraints gbc_lblLabel = new GridBagConstraints();
		gbc_lblLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabel.anchor = GridBagConstraints.EAST;
		gbc_lblLabel.gridx = 0;
		gbc_lblLabel.gridy = 1;
		contentPane.add(lblLabel, gbc_lblLabel);
		
		txtGameInstanceId = new JTextField();
		GridBagConstraints gbc_txtGameid = new GridBagConstraints();
		gbc_txtGameid.insets = new Insets(0, 0, 5, 5);
		gbc_txtGameid.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGameid.gridx = 1;
		gbc_txtGameid.gridy = 1;
		contentPane.add(txtGameInstanceId, gbc_txtGameid);
		txtGameInstanceId.setColumns(10);
		
		JLabel lblLabel_1 = new JLabel("title");
		GridBagConstraints gbc_lblLabel_1 = new GridBagConstraints();
		gbc_lblLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblLabel_1.gridx = 2;
		gbc_lblLabel_1.gridy = 1;
		contentPane.add(lblLabel_1, gbc_lblLabel_1);
		
		txtTitle = new JTextField();
		GridBagConstraints gbc_txtTitle = new GridBagConstraints();
		gbc_txtTitle.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitle.gridx = 3;
		gbc_txtTitle.gridy = 1;
		contentPane.add(txtTitle, gbc_txtTitle);
		txtTitle.setColumns(10);
		
		JLabel lblTeamcount = new JLabel("teamCount");
		GridBagConstraints gbc_lblTeamcount = new GridBagConstraints();
		gbc_lblTeamcount.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeamcount.anchor = GridBagConstraints.EAST;
		gbc_lblTeamcount.gridx = 4;
		gbc_lblTeamcount.gridy = 1;
		contentPane.add(lblTeamcount, gbc_lblTeamcount);
		
		txtTeamCount = new JTextField();
		GridBagConstraints gbc_txtTeamCount = new GridBagConstraints();
		gbc_txtTeamCount.insets = new Insets(0, 0, 5, 5);
		gbc_txtTeamCount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTeamCount.gridx = 5;
		gbc_txtTeamCount.gridy = 1;
		contentPane.add(txtTeamCount, gbc_txtTeamCount);
		txtTeamCount.setColumns(10);
		
		JLabel lblPlayersperteam = new JLabel("playersPerTeam");
		GridBagConstraints gbc_lblPlayersperteam = new GridBagConstraints();
		gbc_lblPlayersperteam.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayersperteam.anchor = GridBagConstraints.EAST;
		gbc_lblPlayersperteam.gridx = 6;
		gbc_lblPlayersperteam.gridy = 1;
		contentPane.add(lblPlayersperteam, gbc_lblPlayersperteam);
		
		txtPlayersPerTeam = new JTextField();
		GridBagConstraints gbc_txtPlayersPerTeam = new GridBagConstraints();
		gbc_txtPlayersPerTeam.insets = new Insets(0, 0, 5, 0);
		gbc_txtPlayersPerTeam.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPlayersPerTeam.gridx = 7;
		gbc_txtPlayersPerTeam.gridy = 1;
		contentPane.add(txtPlayersPerTeam, gbc_txtPlayersPerTeam);
		txtPlayersPerTeam.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setEnabled(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Tester", "Student Name", "Team Number", "Player Number", "Game State Count", "Text", "Status"
			}
		));
		scrollPane.setViewportView(table);
	}
	
	//Background task for loading images.
    SwingWorker worker = new SwingWorker<Void, Void>() {
        @Override
        public Void doInBackground() {
        	int counter = 0;
        	while(true) {
        		counter++;
        		txtCounter.setText(String.valueOf(counter / 10));
        		try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
    };
    
    public void Log() {
		LocalDateTime now = LocalDateTime.now();
		long diff = Math.abs(now.getNano() - old.getNano());
		old = now;
		try {
			writer.write(dtf.format(now) + "," + String.valueOf(diff / 1000000) +"\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public JTable getTable() {
		return this.table;
	}

}
