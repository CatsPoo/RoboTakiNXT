package Game;

import java.util.LinkedList;
import Cards.Card;
import Cards.NoColorCard;
import Cards.NumberCard;
import Cards.OneColorCard;
import Cards.SpecialCardType;
import Cards.SpecialOneColorCard;

public class AvailableCards 
{
	public static LinkedList<Card> GetAllAvailableCards(Card CurrentCard) 
	{
		if(CurrentCard instanceof NumberCard) //if the card is number card
		{
			if(((NumberCard)CurrentCard).GetNumber()==2) return GetAvailableCardsBy2Plus(); //if the card is 2 plus
			else return GetAvailableCardsByNumberCard((NumberCard)CurrentCard);
		}
		
		else if(CurrentCard instanceof SpecialOneColorCard) //if the card is speicial one color card
		{
			//if(((SpecialOneColorCard)CurrentCard).GetCardType()==SpecialCardType.Stop) return null;
			return GetAvailableCardBySpecialOneColorCard((SpecialOneColorCard)CurrentCard);
		}
		
		else if(CurrentCard instanceof NoColorCard)//if the card is super taki or change color
		{
			return GetAvailableCardsByNoColorCard((NoColorCard)CurrentCard);
		}
		return null;
	}
	
	private static LinkedList<Card> GetAvailableCardsByNumberCard(NumberCard nCard) //find available card for number card
	{
		LinkedList<Card> availableCards=new LinkedList<>();
		for (Card c : Hand.DeckHand) {
			if (c instanceof NoColorCard) availableCards.addLast(c); //if the card is super taki or change color
            else if (c instanceof NumberCard && ((NumberCard)c).GetNumber() == nCard.GetNumber()) availableCards.addLast(c); //if the numbers of bothcard are the same
            else if (c instanceof OneColorCard && ((OneColorCard)c).GetColor() == nCard.GetColor()) availableCards.addLast(c); //if the color of both card are both
		}
		return availableCards;
	}
	
	private static LinkedList<Card> GetAvailableCardsBy2Plus() //find if have another 2 plus to put on the current 2 plus
	{
		LinkedList<Card> availableCards=new LinkedList<>();
		for(Card c : Hand.DeckHand)
		{
			if(c instanceof NumberCard&&((NumberCard)c).GetNumber()==2) //if the current is 2 plus
			{
				availableCards.addLast(c);
				return availableCards; //return list with one card
			}
		}
		return null;
	}
	
	private static LinkedList<Card> GetAvailableCardBySpecialOneColorCard(SpecialOneColorCard sCard) //find the available card for speicial color
	{
		LinkedList<Card> availableCards=new LinkedList<>();
		for(Card c : Hand.DeckHand)
		{
			if (c instanceof NoColorCard) availableCards.addLast(c);//if the current card is supet taki or change color
            else if (c instanceof SpecialOneColorCard && ((SpecialOneColorCard)c).GetCardType() == sCard.GetCardType()) availableCards.addLast(c); 
            else if (c instanceof OneColorCard && ((OneColorCard)c).GetColor() == sCard.GetColor()) availableCards.addLast(c);
		}
		return availableCards;
		
	}
	
	private static LinkedList<Card> GetAvailableCardsByNoColorCard(NoColorCard card)
	{
		LinkedList<Card> availableCards=new LinkedList<>();
		for(Card c : Hand.DeckHand)
		{
			 if (c instanceof NoColorCard) availableCards.addLast(c);
	         else if (c instanceof OneColorCard && ((OneColorCard)c).GetColor() == card.GetCurrentColor()) availableCards.addLast(c);
		}
		return availableCards;
	}
}
