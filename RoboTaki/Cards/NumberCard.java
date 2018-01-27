package Cards;

public class NumberCard extends OneColorCard
{
	private int Number;
	
	public NumberCard(Color c,int number)
	{
		super(c);
		this.Number=number;
	}
	
	public void SetNumber(int number)
	{
		this.Number=number;
	}
	
	public int GetNumber()
	{
		return this.Number;
	}
	
	@Override
	public String toString()
	{
		return super.GetColor().toString()+" "+this.GetNumber();
	}
}
