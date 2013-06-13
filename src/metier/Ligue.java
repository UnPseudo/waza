package metier;

import java.util.ArrayList;

//TODO bug : les mails ne sont pas supprimés de la base quand un contact est supprimé


public class Ligue 
{
	private int num = NO_KEY;
	private Root root;
	private String nom = null;
	private String description = null;
	private ArrayList<Club> clubs = new ArrayList<Club>();
	private ArrayList<Tournoi> tournois = new ArrayList<Tournoi>();
	private ArrayList<Categorie> categories = new ArrayList<Categorie>();
	private final static int NO_KEY = -1;
 
	private Boolean allClubsLoaded = false;
	private Boolean allTournoisLoaded = false;
	private Boolean allCategoriesLoaded = false;
	
	//
	void setNum(int num) 
	{
		if (this.num != NO_KEY)
			throw new RuntimeException("Cannot change DB ID");
		this.num = num;
	}
	
	public int getNum() 
	{
		return num;
	}
	
	public String getNom() 
	{
		return nom;
	}
	
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	//
	public Root getRoot()
	{
		return root;
	}

	Ligue(Root root, int num, String nom, String description) 
	{
		this.root = root;
		setNum(num);
		setNom(nom);
		setDescription(description);
	}
	
	public Ligue(Root root, String nom, String description) 
	{
		this(root, NO_KEY, nom, description);
	}
	
	// Club
	public int getNbClubs() throws DataAccessException 
	{
		loadAllClubs();
		return clubs.size();
	}

	public Club getClub(int index) throws DataAccessException 
	{
		loadAllClubs();
		return clubs.get(index);
	}
	
	private boolean possedeClub(Club club) throws DataAccessException 
	{
		loadAllClubs();
		return clubs.contains(club);
	}
	
	private void loadAllClubs() throws DataAccessException
	{	
		if (!allClubsLoaded)
		{ 
			root.loadAllClubs(this);
			allClubsLoaded = true;
		}
	}
	 
	public void addClub(Club club) throws DataAccessException 
	{
		loadAllClubs();
		if(!possedeClub(club))
		{
			clubs.add(club);
			club.setLigue(this);
		}
	}

	public void removeClub(Club club) throws DataAccessException 
	{
		loadAllClubs();
		if(possedeClub(club))
		{
			clubs.remove(club);
			club.setLigue(null);
		}
	}
	
	//Categorie
	public int getNbCategorie() throws DataAccessException 
	{
		loadAllCategories();
		return categories.size();
	}

	public Categorie getCategorie(int index) throws DataAccessException
	{
		loadAllCategories();
		return categories.get(index);
	}
	
	private boolean possedeCategorie(Categorie categorie) throws DataAccessException 
	{
		loadAllCategories();
		return categories.contains(categorie);
	}
	
	private void loadAllCategories() throws DataAccessException
	{
		if (!allCategoriesLoaded)
		{
			root.loadAllCategories(this);
			allCategoriesLoaded = true;
		}
		
	} 
	
	public void addCategorie(Categorie categorie) throws DataAccessException 
	{
		loadAllCategories();
		if(!possedeCategorie(categorie))
		{
			categories.add(categorie);
			categorie.setLigue(this);
		}
	}

	public void removeCategorie(Categorie categorie) throws DataAccessException 
	{
		loadAllTournois();
		if(possedeCategorie(categorie))
		{
			categories.remove(categorie);
			categorie.setLigue(null);
		}
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
		if (!allTournoisLoaded)
		{
			root.loadAllTournois(this);
			allTournoisLoaded = true;
		}
	}
	
	public void addTournoi(Tournoi tournoi) throws DataAccessException 
	{
		loadAllTournois();
		if(!possedeTournoi(tournoi))
		{
			tournois.add(tournoi);
			tournoi.setLigue(this);
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
	
}
