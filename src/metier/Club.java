package metier;

import java.util.ArrayList;

public class Club
{
	private int num = NO_KEY;
	private Ligue ligue;
	private String nom = null;
	private String description = null;
	private ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
	private ArrayList<Equipe> equipes = new ArrayList<Equipe>();
	private final static int NO_KEY = -1;
	
	private Boolean allUtilisateursLoaded = false;
	private Boolean allEquipesLoaded = false;
	
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
	
	public String getNom()
	{
		return nom;
	}
	
	public String setNom(String nom)
	{
		return this.nom = nom;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	} 
	
	// Ligue
	public Ligue getLigue()
	{
		return ligue;
	}
	
	public void setLigue(Ligue ligue) throws DataAccessException
	{
		this.ligue = ligue;
	}
	
	public int getNumLigue()
	{
		return ligue.getNum();
	}
	
	Club(int num, String nom, String description, Ligue ligue) throws DataAccessException
	{
		setNum(num);
		setNom(nom);
		setDescription(description);
		setLigue(ligue);
	}

	public Club(String nom, String description, Ligue ligue) throws DataAccessException
	{
		this(NO_KEY, nom, description, ligue);
	}
	
	// Utilisateur
	private boolean possedeUtilisateur(Utilisateur utilisateur) throws DataAccessException 
	{ 
		loadAllUtilisateurs();
		return utilisateurs.contains(utilisateur);
	}
	
	private void loadAllUtilisateurs() throws DataAccessException
	{
		
		if (!allUtilisateursLoaded)
		{ 
			Root root = getLigue().getRoot();
			root.loadAllUtilisateurs(this);
			allUtilisateursLoaded = true;
		}
	}

	public void addUtilisateur(Utilisateur utilisateur) throws DataAccessException 
	{
		loadAllUtilisateurs();
		if(!possedeUtilisateur(utilisateur))
		{
			utilisateurs.add(utilisateur);
			utilisateur.setClub(this);
		}
	}

	public void removeUtilisateur(Utilisateur utilisateur) throws DataAccessException 
	{
		loadAllUtilisateurs();
		if(possedeUtilisateur(utilisateur))
		{
			utilisateurs.remove(utilisateur);
			utilisateur.setClub(null);
		}
	}

	//Equipe
	private boolean possedeEquipe(Equipe equipe) throws DataAccessException 
	{
		loadAllEquipes();
		return equipes.contains(equipe);
	}
	
	private void loadAllEquipes() throws DataAccessException // A modifier
	{
		if (!allEquipesLoaded)
		{
			Root root = getLigue().getRoot();
			root.loadAllEquipes(this);
			allEquipesLoaded = true;
		}
	}
	
	public void addEquipe(Equipe equipe) throws DataAccessException 
	{
		loadAllEquipes();
		if(!possedeEquipe(equipe))
		{
			equipes.add(equipe);
			equipe.setClub(this);
		}
	}

	public void removeEquipe(Equipe equipe) throws DataAccessException 
	{
		loadAllEquipes();
		if(possedeEquipe(equipe))
		{
			equipes.remove(equipe);
			equipe.setClub(null);
		}
	}
}
