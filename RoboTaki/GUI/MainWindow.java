package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.management.remote.JMXServiceURL;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.ui.L1R1ButtonPanel;
import org.jfree.ui.about.Library;

import Cards.Card;
import Cards.NoColorCard;
import Cards.OneColorCard;
import Cards.SpecialCardType;
import Cards.SpecialOneColorCard;
import Game.RobotAI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Robot;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.Currency;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.text.TabableView;

public class MainWindow {

	private CardLayout CLayout;
	private JPanel Body;
	private JFrame frame;
	private Thread T;
	private String LastCardScaned;
	boolean TakiMode=false;
	private Cards.Color CurrentColor;
	
	private RobotAI Game=new RobotAI(new String[]{"5 RED","3 BLUE","PLUS GREEN","SUPER","STOP RED","9 BLUE","CHANGE","TAKI RED"});;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//it the main frame
		frame = new JFrame();
		frame.setBounds(100, 100, 759, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Init the main panel
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout());
		
		//init the body panel and the card layout
		//set the body layout as card layout
		this.CLayout=new CardLayout();
		this.Body=new JPanel();
		this.Body.setLayout(this.CLayout);
		
		//desine the layout spaces
		panel.add(this.Body, BorderLayout.CENTER);
		panel.add(Box.createRigidArea(new Dimension(150, 0)), BorderLayout.WEST);
		panel.add(Box.createRigidArea(new Dimension(150, 0)), BorderLayout.EAST);
		panel.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.NORTH);
		
		//add pages to te card tayout
		//this.Body.add(this.AddOpenPage(),"OpenPage");
		//this.Body.add(this.AddScanCardPage(),"AddCard");
		this.Body.add(this.AddPlayerTurnWindow(),"PlayerTurn");
		this.Body.add(this.AddWindowToSelectColor(),"SelectColor");	
		this.Body.add(this.AddTakiWindow(),"Taki");
		this.Body.add(this.AddRobotTurnWindow(),"RobotTurn");
		
		
		this.CLayout.first(this.Body);
	}
	
	private JPanel AddScanCardPage()
	{	
		JPanel ScanPage=new JPanel();
		ScanPage.setLayout(null);
		
		JLabel message=new JLabel("Scan 8 Cards 0/8 Complete");
		message.setFont(new Font("Tahoma", Font.PLAIN, 18));
		message.setBounds(109, 127, 300, 30);
		
		ScanPage.add(message);
		this.Body.add(ScanPage);	
		
		JButton btnClickToContinue = new JButton("Click to continue");
		btnClickToContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnClickToContinue.setEnabled(false);
		btnClickToContinue.setBounds(165, 210, 123, 23);
		ScanPage.add(btnClickToContinue);
		
		return ScanPage;
	}
	
	
	
	private JPanel AddOpenPage()
	{
		JPanel OpenPage=new JPanel();
		OpenPage.setLayout(null);
		
		JLabel message=new JLabel("Click \" Start!\" to Begin new Taki game");
		message.setFont(new Font("Tahoma", Font.PLAIN, 18));
		message.setBounds(64, 11, 1000, 50);
		
		JButton Start=new JButton("Start!");
		Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
			 CLayout.next(Body);
			 Runnable r=new ScanCards(
					 8,
					 (JLabel)Body.getComponent(1).getComponentAt(109, 127),
					 (JButton)Body.getComponent(1).getComponentAt(165, 210)
					 );
			 T=new Thread(r);
			 T.start();
			}
		});
		Start.setBounds(171, 128, 100, 50);
		
		OpenPage.add(message);
		OpenPage.add(Start);
		
		return OpenPage;
	}
	
	private JPanel AddWindowToSelectColor()
	{
		int width=50,height=50; //size of the buttons
		JPanel SelectColor=new JPanel();
		SelectColor.setLayout(null);
		JLabel Messsage=new JLabel("Select Color");
		Messsage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton Red = new JButton("");
		
		Red.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentColor=Cards.Color.RED;
				if(LastCardScaned.equals("SUPER"))
				{
					CLayout.show(Body, "Taki");
					TakiMode=true;
					CurrentColor=Cards.Color.RED;
				}
				else
				{
					NoColorCard NCard=new NoColorCard(SpecialCardType.CHANGE);
					NCard.SetCurrentColor(Cards.Color.RED);
					Game.PlayTurn(NCard);
					CLayout.show(Body, "RobotTurn");
					TakiMode=false;
				}
			}
		});
		Red.setBackground(Color.RED);
		
		JButton Green = new JButton("");
		Green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				CurrentColor=Cards.Color.GREEN;
				if(LastCardScaned.equals("SUPER"))
				{
					CLayout.show(Body, "Taki");
					TakiMode=true;
					CurrentColor=Cards.Color.GREEN;
				}
				else
				{
					NoColorCard NCard=new NoColorCard(SpecialCardType.CHANGE);
					NCard.SetCurrentColor(Cards.Color.GREEN);
					Game.PlayTurn(NCard);
					CLayout.show(Body, "RobotTurn");
					TakiMode=false;
				}
			}
		});
		Green.setBackground(Color.GREEN);
		
		JButton Blue = new JButton("");
		Blue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentColor=Cards.Color.BLUE;
				if(LastCardScaned.equals("SUPER"))
				{
					CLayout.show(Body, "Taki");
					TakiMode=true;
					CurrentColor=Cards.Color.BLUE;
				}
				else
				{
					NoColorCard NCard=new NoColorCard(SpecialCardType.CHANGE);
					NCard.SetCurrentColor(Cards.Color.BLUE);
					Game.PlayTurn(NCard);
					CLayout.show(Body, "RobotTurn");
					TakiMode=false;
				}
			}
		});
		Blue.setBackground(Color.BLUE);
		
		JButton Yellow = new JButton("");
		Yellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentColor=Cards.Color.YELLOW;
				if(LastCardScaned.equals("SUPER"))
				{
					CLayout.show(Body, "Taki");
					TakiMode=true;
					CurrentColor=Cards.Color.YELLOW;
				}
				else
				{
					NoColorCard NCard=new NoColorCard(SpecialCardType.CHANGE);
					NCard.SetCurrentColor(Cards.Color.YELLOW);
					Game.PlayTurn(NCard);
					CLayout.show(Body, "RobotTurn");
					TakiMode=false;
				}
			}
		});
		Yellow.setBackground(Color.YELLOW);
				
		Messsage.setBounds(164, 0, 104, 50);
		Red.setBounds(this.frame.getWidth()/4, Messsage.getHeight(), width, height);
		Blue.setBounds((this.frame.getWidth()/4)-(width/2), 55+Messsage.getHeight(), width, height);
		Green.setBounds((this.frame.getWidth()/4)+(width/2),55+Messsage.getHeight(), width, height);
		Yellow.setBounds((this.frame.getWidth()/4), 110+Messsage.getHeight(), width, height);
		
		SelectColor.add(Messsage);
		SelectColor.add(Red);
		SelectColor.add(Blue);
		SelectColor.add(Green);
		SelectColor.add(Yellow);
		
		return SelectColor;
	}
	
	private JPanel AddTakiWindow()
	{
		boolean cont=true;
		JPanel Taki=new JPanel();
		Taki.setLayout(null);
		
		JLabel message=new JLabel("Scan all your cards you want to put, (already scand 0)");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Tahoma", Font.PLAIN, 18));
		message.setBounds(0, 11, 444, 100);
		
		JButton btnClickToContinue = new JButton("End Turn");
		btnClickToContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				T.stop();
				
				Card Last=RobotAI.ConvertStringToCard(LastCardScaned);
				if(Last instanceof NoColorCard)
				{
					NoColorCard Ncard=(NoColorCard)Last;
					Ncard.SetCurrentColor(CurrentColor);
					Game.PlayTurn(Ncard);
				}
				else Game.PlayTurn(RobotAI.ConvertStringToCard(LastCardScaned));
				RobotTurn();
			}
		});
		btnClickToContinue.setBounds(165, 210, 123, 23);
		
		Taki.add(message);
		Taki.add(btnClickToContinue);
		
		return Taki;
	}
	
	private JPanel AddPlayerTurnWindow()
	{
		JPanel PlayerTurn=new JPanel();
		PlayerTurn.setLayout(null);
		
		JLabel message=new JLabel("<html>Now is your turn!<br>Scan card or click \"take\" to take card from the pack</html> ");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Tahoma", Font.PLAIN, 18));
		message.setBounds(10, 11, 423, 100);
		
		JButton btnClickToContinue = new JButton("Take");
		btnClickToContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				T.stop();
				Game.PlayTurn(null);
			}
		});
		btnClickToContinue.setBounds(165, 210, 123, 23);
		
		PlayerTurn.add(btnClickToContinue);
		PlayerTurn.add(message);
		
		Runnable r=new ScanCards(1,message,btnClickToContinue);
		T=new Thread(r);
		T.start();
		
		return PlayerTurn;
	}
	
	private JPanel AddRobotTurnWindow()
	{
		JPanel RobotTurn=new JPanel();
		RobotTurn.setLayout(null);
		
		JLabel message=new JLabel("Wait for robot move");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Tahoma", Font.PLAIN, 18));
		message.setBounds(10, 11, 423, 100);
		
		RobotTurn.add(message);

		return RobotTurn;
	}
	
	private boolean CheckIfNeedMoreCard(String card)
	{
		Card c=RobotAI.ConvertStringToCard(card);
		if(c instanceof SpecialOneColorCard)
		{
			SpecialOneColorCard SCard=(SpecialOneColorCard)c;
			if(SCard.GetCardType()==SpecialCardType.PLUS||SCard.GetCardType()==SpecialCardType.STOP)return true;
		}
		return false;
	}
	
	private boolean CheckIfNeedToSelectColor(String card)
	{
		Card c=RobotAI.ConvertStringToCard(card);
		if(c instanceof NoColorCard) return true;
		return false;
	}
	
	private boolean CheckForTaki(String card)
	{
		Card c=RobotAI.ConvertStringToCard(card);
		if(c instanceof SpecialOneColorCard&&((SpecialOneColorCard)c).GetCardType().equals(SpecialCardType.TAKI)) 
			return true;
		return false;
	}
	
	private void RobotTurn()
	{
		System.out.println("RobotTurn");
		CLayout.show(Body, "RobotTurn");
	}
	
	
	
	// trhead to scan 8 cards and create new dech hand
	public class ScanCards implements Runnable {

		JLabel message;
		JButton Btn;
		int NumberOfCards;
		public ScanCards(int numberOfCards,JLabel message,JButton btn)
		{
			this.NumberOfCards=numberOfCards;
			this.message=message;
			this.Btn=btn;
		}
		
		public ScanCards()
		{
			this.NumberOfCards=1;
			this.message=null;
			this.Btn=null;
		}

		   public void run() {
			   int CardCount=0;
				String LastCard=CameraConnection.GetCard();
				String[] Cards=new String[this.NumberOfCards];
				
				while(CardCount<this.NumberOfCards)
				{
					String currentCard=CameraConnection.GetCard();
					if(!currentCard.equals(LastCard))
					{
						Cards[CardCount]=currentCard.substring(0,currentCard.length()-1);
						LastCard=currentCard;
						CardCount++;
						if(this.NumberOfCards==8)
						{
							message.setText("Scan 8 Cards "+CardCount+"/8 Complete");
							
						}
					}
				}
				this.Btn.setEnabled(true);
				if(this.NumberOfCards==8)
				{
					Game=new RobotAI(Cards);
					T.stop();
				}
				else //scan only one card (regulat turn
				{
					if(!TakiMode&&Game.CheckCard(Cards[0]))
					{
						LastCardScaned=Cards[0];
						Game.SetLastCard(Cards[0]);
						if(CheckIfNeedMoreCard(Cards[0])) //if need to put one more card like in case of stop or plus
						{
							message.setText("Scan one more card or click \"Take\" if you take from the pack");
							run();
						}
						else if(CheckIfNeedToSelectColor(Cards[0]))
						{
							CLayout.show(Body, "SelectColor");
						}
						else if(CheckForTaki(Cards[0]))
						{
							TakiMode=true;
							CLayout.show(Body, "Taki");
						}
						else
						{
							RobotTurn();
							Game.PlayTurn(RobotAI.ConvertStringToCard(Cards[0]));
						}
						T.stop();
					}
					else if(TakiMode&&Game.CheckCardInTaki(LastCard, CurrentColor))
					{
						System.out.println("TT");
						int count=Integer.parseInt(message.getText().substring(message.getText().length()-2,1));
						count++;
						message.setText("Scan all your cards you want to put, (already scand "+count+")");
					}
					else
					{
						message.setText("Incorrect card scan another");
						run();
					}
				}
				

		   }
		}
}
