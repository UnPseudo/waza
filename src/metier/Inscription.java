package metier;

public class Inscription {
	private int num = NO_KEY;
	private Equipe equipe;
	private Tournoi tournoi;
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
	
	//
	public Tournoi getTournoi()
	{
		return tournoi;
	}
	
	public void setTournoi(Tournoi tournoi) throws DataAccessException
	{
		this.tournoi = tournoi;
	}
	 
	public int getNumTournoi()
	{
		return tournoi.getNum();
	}
	
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
	
	Inscription(int num, Equipe equipe, Tournoi tournoi) throws DataAccessException
	{
		setNum(num);
		setEquipe(equipe);
		setTournoi(tournoi);
	}
	
	public Inscription(Equipe equipe, Tournoi tournoi) throws DataAccessException
	{
		this(NO_KEY, equipe, tournoi);
	}

}