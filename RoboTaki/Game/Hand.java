package Game;

import java.util.LinkedList;
import Cards.Card;

public class Hand {
	
	public static LinkedList<Card> DeckHand=new LinkedList<Card>();
	
	
	public static int CardCount()
	{
		int i=0;
		for (Card card : DeckHand) {
			i++;
		}
		return i;
	}
	
	public static void Add(Card card)
	{
		card.SetPosition(CardCount());
		DeckHand.addLast(card);
	}
	
	public static Card First()
	{
		return DeckHand.getFirst();
	}
	
	public static Card Remove()
	{
		Card temp=DeckHand.removeFirst();
		temp.SetPosition(-1);
		return temp;
	}
	
	public static void BringCardToTop()
	{
		for (Card card : DeckHand) {
			if(card.GetPosition()!=0) card.SetPosition(card.GetPosition()-1);
			else card.SetPosition(CardCount());
		}
		DeckHand.addLast(DeckHand.pollFirst());
	}
	
}
