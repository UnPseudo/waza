package metier;

import java.util.ArrayList;

public class Categorie 
{
	private int num = NO_KEY;
	private Ligue ligue;
	private String nom = null;
	private String description = null;
	private ArrayList<Equipe> equipes;
	private ArrayList<Tournoi> tournois; 
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
		if (this.ligue != ligue)
		{
			if (this.ligue != null)
				this.ligue.removeCategorie(this);
			this.ligue = ligue;
			if (this.ligue != null)
				ligue.addCategorie(this);
		}
	}
	
	public int getNumLigue()
	{
		return ligue.getNum();
	}
	
	Categorie(int num, String nom, Ligue ligue) throws DataAccessException
	{
		setNum(num);
		setNom(nom);
		setLigue(ligue);
	}

	public Categorie(String nom, Ligue ligue) throws DataAccessException
	{
		this(NO_KEY, nom, ligue);
	}

	// Tournoi
	public int getNbTournoi() throws DataAccessException 
	{
		loadAllTournois();
		return tournois.size();
	}

	public Tournoi getTournoi(int index) throws DataAccessException // je comprend pas
	{
		loadAllTournois();
		return tournois.get(index);
	}
	
	private boolean possedeTournoi(Tournoi tournoi) throws DataAccessException 
	{
		loadAllTournois();
		return tournois.contains(tournoi);
	}
	
	private void loadAllTournois() throws DataAccessException
	{
		if (tournois == null)
		{
			tournois = new ArrayList<Tournoi>();
			Root root = getLigue().getRoot();
			root.loadTournois(this);
		}
	}
	
	public void addTournoi(Tournoi tournoi) throws DataAccessException 
	{
		loadAllTournois();
		if(!possedeTournoi(tournoi))
		{
			tournois.add(tournoi);
			tournoi.setCategorie(this);
		}
	}

	public void removeTournoi(Tournoi tournoi) throws DataAccessException 
	{
		loadAllTournois();
		if(possedeTournoi(tournoi))
		{
			tournois.remove(tournoi);
			tournoi.setLigue(null);
		}
	}
	
	// Equipe
	private boolean possedeEquipe(Equipe equipe) throws DataAccessException 
	{
		loadAllEquipesCategorie();
		return equipes.contains(equipe);
	}

	private void loadAllEquipesCategorie() throws DataAccessException
	{
		if (equipes == null)
		{
			equipes = new ArrayList<Equipe>();
			Root root = getLigue().getRoot();
			root.loadEquipes(this);
		}
	} 
	
	public void addEquipe(Equipe equipe) throws DataAccessException 
	{
		loadAllEquipesCategorie();
		if(!possedeEquipe(equipe))
		{
			equipes.add(equipe);
			equipe.setCategorie(this);
		}
	}

	public void removeEquipe(Equipe equipe) throws DataAccessException 
	{
		loadAllEquipesCategorie();
		if(possedeEquipe(equipe))
		{
			equipes.remove(equipe);
			equipe.setCategorie(null);
		}
	}
	
	
}
