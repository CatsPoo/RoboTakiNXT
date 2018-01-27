package Cards;

public class OneColorCard extends Card
{
	private Color color;
	
	public OneColorCard(Color c)
	{
		this.color=c;
	}
	
	public void SetColor(Color c)
	{
		this.color=c;
	}
	
	public Color GetColor()
	{
		return this.color;
	}
}
