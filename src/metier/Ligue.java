package metier;

import java.sql.SQLException;
import java.util.ArrayList;

//TODO bug : les mails ne sont pas supprimés de la base quand un contact est supprimé


public class Ligue 
{
	private int num = NO_KEY;
	private RootSuperAdmin rootSuperAdmin;
	private String nom = null;
	private String description = null;
	private ArrayList<Club> clubs;
	private ArrayList<Tournoi> tournois;
	private ArrayList<Categorie> categories;
	private final static int NO_KEY = -1;

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
	public RootSuperAdmin getRootSuperAdmin()
	{
		return rootSuperAdmin;
	}

	Ligue(RootSuperAdmin rootSuperAdmin, int num, String nom) 
	{
		this.rootSuperAdmin = rootSuperAdmin;
		setNum(num);
		setNom(nom);;
	}
	
	public Ligue(RootSuperAdmin rootSuperAdmin, String nom) 
	{
		this(rootSuperAdmin, NO_KEY, nom);
	}
	
	// Club
	public int getNbClubs() throws SQLException 
	{
		loadAllClubs();
		return clubs.size();
	}

	public Club getClub(int index) throws SQLException 
	{
		loadAllClubs();
		return clubs.get(index);
	}
	
	private boolean possedeClub(Club club) throws SQLException 
	{
		loadAllClubs();
		return clubs.contains(club);
	}
	
	private void loadAllClubs() throws SQLException
	{
		if (clubs == null)
		{
			clubs = new ArrayList<Club>();
			rootSuperAdmin.loadClub(this);
		}
	}
	
	public void addClub(Club club) throws SQLException 
	{
		loadAllClubs();
		if(!possedeClub(club))
		{
			clubs.add(club);
			club.setLigue(this);
		}
	}

	public void removeClub(Club club) throws SQLException 
	{
		loadAllClubs();
		if(possedeClub(club))
		{
			clubs.remove(club);
			club.setLigue(null);
		}
	}
	
	//Categorie
	public int getNbCategorie() throws SQLException 
	{
		loadAllCategories();
		return categories.size();
	}

	public Categorie getCategorie(int index) throws SQLException
	{
		loadAllCategories();
		return categories.get(index);
	}
	
	private boolean possedeCategorie(Categorie categorie) throws SQLException 
	{
		loadAllCategories();
		return categories.contains(categorie);
	}
	
	private void loadAllCategories() throws SQLException
	{
		if (categories == null)
		{
			categories = new ArrayList<Categorie>();
			rootSuperAdmin.loadCategorie(this);
		}
	}
	
	public void addCategorie(Categorie categorie) throws SQLException 
	{
		loadAllCategories();
		if(!possedeCategorie(categorie))
		{
			categories.add(categorie);
			categorie.setLigue(this);
		}
	}

	public void removeCategorie(Categorie categorie) throws SQLException 
	{
		loadAllTournois();
		if(possedeCategorie(categorie))
		{
			categories.remove(categorie);
			categorie.setLigue(null);
		}
	}

	// Tournoi
	public int getNbTournoi() throws SQLException 
	{
		loadAllTournois();
		return tournois.size();
	}

	public Tournoi getTournoi(int index) throws SQLException // je comprend pas
	{
		loadAllTournois();
		return tournois.get(index);
	}
	
	private boolean possedeTournoi(Tournoi tournoi) throws SQLException 
	{
		loadAllTournois();
		return tournois.contains(tournoi);
	}
	
	private void loadAllTournois() throws SQLException
	{
		if (tournois == null)
		{
			tournois = new ArrayList<Tournoi>();
			rootSuperAdmin.loadTournoi(this);
		}
	}
	
	public void addTournoi(Tournoi tournoi) throws SQLException 
	{
		loadAllTournois();
		if(!possedeTournoi(tournoi))
		{
			tournois.add(tournoi);
			tournoi.setLigue(this);
		}
	}

	public void removeTournoi(Tournoi tournoi) throws SQLException 
	{
		loadAllTournois();
		if(possedeTournoi(tournoi))
		{
			tournois.remove(tournoi);
			tournoi.setLigue(null);
		}
	}
	
}
