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
		if (this.tournoi != tournoi)
		{
			if (this.tournoi != null)
				this.tournoi.removeInscription(this);
			this.tournoi = tournoi;
			if (this.tournoi != null)
				tournoi.addInscription(this);
		}
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
		if (this.equipe != equipe)
		{
			if (this.equipe != null)
				this.equipe.removeInscription(this);
			this.equipe = equipe;
			if (this.equipe != null)
				equipe.addInscription(this);
		}
	}
	
	public int getNumEquipe()
	{
		return equipe.getNum();
	}
	
	public Inscription(Equipe equipe, Tournoi tournoi) throws DataAccessException
	{
		setEquipe(equipe);
		setTournoi(tournoi);
	}

}