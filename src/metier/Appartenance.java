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
		if (this.utilisateur != utilisateur)
		{
			if (this.utilisateur != null)
				this.utilisateur.removeAppartenance(this);
			this.utilisateur = utilisateur;
			if (this.utilisateur != null)
				utilisateur.addAppartenance(this);
		}
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
		if (this.equipe != equipe)
		{
			if (this.equipe != null)
				this.equipe.removeAppartenance(this);
			this.equipe = equipe;
			if (this.equipe != null)
				equipe.addAppartenance(this);
		}
	}
	
	public int getNumEquipe()
	{
		return equipe.getNum();
	}
	
	public Appartenance(Utilisateur utilisateur, Equipe equipe) throws DataAccessException
	{
		setUtilisateur(utilisateur);
		setEquipe(equipe);
	}

}