package Game;


import java.util.Currency;
import java.util.LinkedList;

import org.jfree.util.BooleanList;

import Cards.Card;
import Cards.Color;
import Cards.NoColorCard;
import Cards.NumberCard;
import Cards.OneColorCard;
import Cards.SpecialCardType;
import Cards.SpecialOneColorCard;

public class BestCards 
{
	public static LinkedList<Card> FindCardsToReturn(LinkedList<Card> availableCards)
	{
		if(availableCards.isEmpty()) return new LinkedList<Card>();
		else if (HaveSpecialOneColorCardWithCards(availableCards, SpecialCardType.TAKI) != null) return ReturnCardByTaki(availableCards);
        else if (HaveSpecialOneColorCardWithCards(availableCards, SpecialCardType.PLUS) != null) return ReturnCardsByPlus(availableCards);
        else if (ReturnFirst(availableCards) != null) return ReturnFirst(availableCards);
        else return ReturnFirst(availableCards);
	}
	
	private static SpecialOneColorCard HaveSpecialOneColorCardWithCards(LinkedList<Card> availableCards, SpecialCardType type)
    {
        for (Card c : availableCards)
        {
            if (c instanceof SpecialOneColorCard && ((SpecialOneColorCard)c).GetCardType() == type && NumberOfCardsByColor(((SpecialOneColorCard)c).GetColor()) > 1) return (SpecialOneColorCard)c;
        }
        return null;
    }
	
	private static int NumberOfCardsByColor(Color color)
    {
        int count = 0;
        for(Card c : Hand.DeckHand)
        {
            if (c instanceof OneColorCard && ((OneColorCard)c).GetColor() == color) count++;
        }
        return count;
    }
	
	private static LinkedList<Card> ReturnCardByTaki(LinkedList<Card> availableCards)
    {
		LinkedList<Card> CardsToReturn = new LinkedList<Card>();

        SpecialOneColorCard Taki = HaveSpecialOneColorCardWithCards(availableCards, SpecialCardType.TAKI);
        CardsToReturn.addLast(Taki);

        for (Card Current : availableCards)
        {
            if (Current == Taki) continue;
            if (Current instanceof NumberCard && ((NumberCard)Current).GetColor() == Taki.GetColor()) CardsToReturn.addLast(Current);
        }

        for (Card Current : availableCards)
        {
            if (Current == Taki) continue;
            if (Current instanceof SpecialOneColorCard && ((SpecialOneColorCard)Current).GetColor() == Taki.GetColor()) CardsToReturn.addLast(Current);
        }
        return CardsToReturn;
    }
	
	private static LinkedList<Card> ReturnCardsByPlus(LinkedList<Card> availableCards)
    {
		LinkedList<Card> CardsToReturn = new LinkedList<Card>();

        SpecialOneColorCard Plus = HaveSpecialOneColorCardWithCards(availableCards, SpecialCardType.PLUS);
        CardsToReturn.addLast(Plus);

        for (Card Current : availableCards)
        {
            if (Current == Plus) continue;
            if (Current instanceof SpecialOneColorCard && ((SpecialOneColorCard)Current).GetCardType() == SpecialCardType.PLUS && ((SpecialOneColorCard)Current).GetColor() == Plus.GetColor()) CardsToReturn.addLast(Current);
        }

        for (Card Current : availableCards)
        {
            if (Current == Plus) continue;
            if (Current instanceof NumberCard && ((NumberCard)Current).GetColor() == Plus.GetColor())
            {
                CardsToReturn.addLast(Current);
                return CardsToReturn;
            }
        }

        for (Card Current : availableCards)
        {
            if (Current == Plus) continue;
            if (Current instanceof SpecialOneColorCard && ((SpecialOneColorCard)Current).GetColor() == Plus.GetColor() && ((SpecialOneColorCard)Current).GetCardType() != SpecialCardType.PLUS)
            {
                CardsToReturn.addLast(Current);;
                return CardsToReturn;
            }
        }
        return CardsToReturn;
    }
	
	private static <T> LinkedList<Card> ReturnFirst(LinkedList<Card> availableCards)
    {
		LinkedList<Card> CardsToReturn = new LinkedList<Card>();
		T type;
        for (Card current : availableCards)
        {
            if (current instanceof NoColorCard)
            {
                NoColorCard NoCard = (NoColorCard)current;
                NoCard.SetCurrentColor(GetMaxColor());
                CardsToReturn.addLast(current);
                if (NoCard.GetCardType() == SpecialCardType.SWITCH) return CardsToReturn;
                else
                {
                    for(Card c : Hand.DeckHand)
                    {
                        if (c instanceof NumberCard && ((NumberCard)c).GetColor() == NoCard.GetCurrentColor()) CardsToReturn.addLast(c);
                    }
                    for (Card c : Hand.DeckHand)
                    {
                        if (c instanceof SpecialOneColorCard && ((SpecialOneColorCard)c).GetColor() == NoCard.GetCurrentColor()) CardsToReturn.addLast(c);
                    }
                    return CardsToReturn;
                }
            }
            else
            {
            	boolean cast=false;
            	try
            	{
            		type=(T)current;
            		cast=true;
            	}
            	catch (ClassCastException e)
            	{
            		
            	}
            	if(cast)
            	{
            		CardsToReturn.addLast(current);
                	return CardsToReturn;
            	}
            }
        }
        return null;
    }
	
	 private static Color GetMaxColor()
	    {
	        int Red = 0, Green = 0, Yellow = 0, Blue = 0;
	        for (Card current : Hand.DeckHand)
	        {
	            if (current instanceof OneColorCard)
	            {
	                if (((OneColorCard)current).GetColor() == Color.BLUE) Blue++;
	                else if (((OneColorCard)current).GetColor() == Color.GREEN) Green++;
	                else if (((OneColorCard)current).GetColor() == Color.RED) Red++;
	                else if (((OneColorCard)current).GetColor() == Color.YELLOW) Yellow++;
	            }
	        }
	        if (Blue >= Red && Blue >= Yellow && Blue >= Green) return Color.BLUE;
	        else if (Yellow >= Red && Yellow >= Blue && Yellow >= Green) return Color.YELLOW;
	        else if (Green >= Red && Green >= Blue && Green >= Yellow) return Color.GREEN;
	        else return Color.RED;
	    }
}
