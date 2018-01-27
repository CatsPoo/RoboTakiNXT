package Cards;

public class SpecialOneColorCard extends OneColorCard
{
	private SpecialCardType CardType;
	
	public SpecialOneColorCard(Color c,SpecialCardType type)
	{
		super(c);
		this.CardType=type;
	}
	
	public void SetCardType(SpecialCardType type)
	{
		this.CardType=type;
	}
	
	public SpecialCardType GetCardType()
	{
		return this.CardType;
	}
	
	@Override
	public String toString()
	{
		return super.GetColor()+" "+this.GetCardType();
	}
}
