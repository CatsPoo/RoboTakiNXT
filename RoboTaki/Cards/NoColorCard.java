package Cards;

public class NoColorCard extends Card
{
	private Color CurrentColor;
	private SpecialCardType CardType;
	
	public NoColorCard(SpecialCardType type)
	{
		this.CurrentColor=null;
		this.CardType=type;
	}
	
	public void SetCurrentColor(Color c)
	{
		this.CurrentColor=c;
	}
	
	public void SetCardType(SpecialCardType type)
	{
		this.CardType=type;
	}
	
	public Color GetCurrentColor()
	{
		return this.CurrentColor;
	}
	
	public SpecialCardType GetCardType()
	{
		return CardType;
	}
	
	@Override
	public String toString()
	{
		return this.GetCardType().toString();
	}
}
