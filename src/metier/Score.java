package metier;

public class Score {
	private int num = NO_KEY;
	private Rencontre rencontre;
	private Equipe equipe;
	int points = 0;
	boolean publie = false;
	private final static int NO_KEY = -1;
	
	//
	public int getNum()
	{
		return num;
	}
	
	void setNum(int num)
	{ 
		if (this.num != NO_KEY)
			throw new RuntimeException("Cannot change DB ID");
		this.num = num;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void setPoints(int points)
	{
		this.points = points;
	}
	
	public boolean getPublie()
	{
		return publie;
	}
	
	public void setPublie(boolean publie)
	{
		this.publie = publie;
	}
	
	// Rencontre
	public Rencontre getRencontre()
	{
		return rencontre;
	}
	
	public void setRencontre(Rencontre rencontre) throws DataAccessException
	{
		this.rencontre = rencontre;
	}
	 
	public int getNumRencontre()
	{
		return rencontre.getNum();
	}
	
	//
	public Equipe getEquipe()
	{
		return equipe;
	}
	
	public void setEquipe(Equipe equipe) throws DataAccessException
	{
		this.equipe = equipe;
	}
	
	public int getNumEquipe()
	{
		return equipe.getNum();
	}
	
	Score(int num, Rencontre rencontre, Equipe equipe, int points, boolean publie) throws DataAccessException
	{
		setNum(num);
		setRencontre(rencontre);
		setEquipe(equipe);
		setPoints(points);
		setEquipe(equipe);
	}
	
	
	public Score(Rencontre rencontre, Equipe equipe, int points, boolean publie) throws DataAccessException
	{
		this(NO_KEY, rencontre, equipe, points, publie);
	}

}