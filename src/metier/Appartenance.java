package metier;

public class Appartenance {
	private int num = NO_KEY;
	private Utilisateur utilisateur;
	private Equipe equipe;
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
	public Utilisateur getUtilisateur()
	{
		return utilisateur;
	}
	
	public void setUtilisateur(Utilisateur utilisateur) throws DataAccessException
	{
		this.utilisateur = utilisateur;
	}
	 
	public int getNumUtilisateur()
	{
		return utilisateur.getNum();
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
	
	
	Appartenance(int num, Utilisateur utilisateur, Equipe equipe) throws DataAccessException
	{
		setNum(num);
		setUtilisateur(utilisateur);
		setEquipe(equipe);
	}
	
	public Appartenance(Utilisateur utilisateur, Equipe equipe) throws DataAccessException
	{
		this(NO_KEY, utilisateur, equipe);
	}

}