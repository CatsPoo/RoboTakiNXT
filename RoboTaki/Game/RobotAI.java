package Game;

import java.util.LinkedList;

import org.jfree.util.BooleanList;
import org.omg.CORBA.NO_IMPLEMENT;

import Cards.Card;
import Cards.Color;
import Cards.NoColorCard;
import Cards.NumberCard;
import Cards.OneColorCard;
import Cards.SpecialCardType;
import Cards.SpecialOneColorCard;
import GUI.MainWindow.ScanCards;
import NXT.NXTControl;
import lejos.nxt.NXT;

public class RobotAI 
{	
	private Card LastCard= new NumberCard(Color.BLUE, 4);
	public RobotAI(String[] Cards)
	{
		for (String string : Cards) 
		{
			Hand.Add(ConvertStringToCard(string));
		}
		//PrintList(BestCards.FindCardsToReturn(AvailableCards.GetAllAvailableCards(new NumberCard(Color.Red, 7))));
	}
	
	public boolean CheckCardInTaki(String card,Color color)
	{
		Card c=ConvertStringToCard(card);
		if(c instanceof NoColorCard) return true;
		else if(c instanceof OneColorCard)
		{
			OneColorCard OC=(OneColorCard)c;
			if(OC.GetColor().equals(color)) return true;
		}
		return false;
	}
	
	public void SetLastCard(String c)
	{
		this.LastCard=ConvertStringToCard(c);
	}
	
	public  boolean CheckCard(String card)
	{
		System.out.println(card);
		Card c=ConvertStringToCard(card);
		
		if(c instanceof NoColorCard) return true;
		else if(c instanceof OneColorCard)
		{
			OneColorCard OC=(OneColorCard)c;
			if(LastCard instanceof NoColorCard)
			{
				NoColorCard LNO=(NoColorCard)LastCard;
				if(LNO.GetCurrentColor().equals(OC.GetColor())) return true;
			}
			else if(LastCard instanceof OneColorCard)
			{
				OneColorCard LOC=(OneColorCard)LastCard;
				if(OC.GetColor().equals(LOC.GetColor()))return true;
				
				if(OC instanceof NumberCard&&LOC instanceof NumberCard)
				{
					NumberCard LNC=(NumberCard)LOC;
					NumberCard NC=(NumberCard)OC;
					if(NC.GetNumber()==LNC.GetNumber())return true;
				}
				else if(OC instanceof SpecialOneColorCard&&LOC instanceof SpecialOneColorCard)
				{
					SpecialOneColorCard LSC=(SpecialOneColorCard)LOC;
					SpecialOneColorCard SC=(SpecialOneColorCard) OC;
					if(LSC.GetCardType().equals(SC.GetCardType()))return true;
				}
			}
		}
		return false;
	}
	
	public void PlayTurn(Card c)
	{	
		if(c!=null) 
		{
			this.LastCard=c;
		}
		else c=this.LastCard;
		
		LinkedList<Card> CardToReturn= BestCards.FindCardsToReturn(AvailableCards.GetAllAvailableCards(c));
		
		PrintList(CardToReturn);
		
		if(CardToReturn!=null&&!CardToReturn.isEmpty())
		{
			for (Card card : CardToReturn) {
				ReturnCard(card);
			}
		}
	}
	
	public void ReturnCard(Card c)
	{
		this.LastCard=c;
		while(c.GetPosition()!=0)
		{
			NXTControl.BringCardToTop();
			Hand.BringCardToTop();
		}
		NXTControl.PopCard();
		Hand.Remove();
	}
	
	private void PrintList(LinkedList<Card> l)
	{
		for (Card card : l) {
			System.out.println(card);
		}
	}
	public static Card ConvertStringToCard(String str) //get the card name as string and return the card object
	{
		if(str.indexOf(" ")!=-1)//if there is a space in the card name
		{
			String color=str.substring(str.indexOf(" ")+1,str.length()); //value is the second part (number value or speiciaal card type
			String value=str.substring(0,str.indexOf(" ")); //the color is the first part
			
			if(IsInteger(value)) //if the value is number
				return new NumberCard(Color.valueOf(color), Integer.parseInt(value));
			else //if the value is speicial card type
				return new SpecialOneColorCard(Color.valueOf(color), SpecialCardType.valueOf(value));
		}
		else //if the card is no color card
		{
			return new NoColorCard(SpecialCardType.valueOf(str));
		
		}
	}
	
	private static boolean IsInteger(String s) 
	{
	      boolean isValidInteger = false;
	      try
	      {
	         Integer.parseInt(s); //try to parse the string to int
	         // s is a valid integer
	         isValidInteger = true; //if seccces
	      }
	      catch (NumberFormatException ex) //if fail
	      {
	         // s is not an integer
	      }
	 
	      return isValidInteger;
	}
}
