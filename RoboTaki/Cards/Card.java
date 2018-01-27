package Cards;

public abstract class Card {
	private int Position;
	
	public Card()
	{
		this.Position=-1;
	}
	
	public void SetPosition(int pos)
	{
		this.Position=pos;
	}
	
	public int GetPosition()
	{
		return this.Position;
	}
}
