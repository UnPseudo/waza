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
		if (this.rencontre != rencontre)
		{
			if (this.rencontre != null)
				this.rencontre.removeScore(this);
			this.rencontre = rencontre;
			if (this.rencontre != null)
				rencontre.addScore(this);
		}
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
		if (this.equipe != equipe)
		{
			if (this.equipe != null)
				this.equipe.removeScore(this);
			this.equipe = equipe;
			if (this.equipe != null)
				equipe.addScore(this);
		}
	}
	
	public int getNumEquipe()
	{
		return equipe.getNum();
	}
	
	public Score(Rencontre rencontre, Equipe equipe, int points, boolean publie) throws DataAccessException
	{
		setRencontre(rencontre);
		setEquipe(equipe);
		setPoints(points);
		setEquipe(equipe);
	}

}