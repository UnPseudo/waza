package metier;

import java.util.ArrayList;

public class Equipe
{
	private int num = NO_KEY;
	private Club club;
	private Categorie categorie;
	private String nom = null;
	private ArrayList<Inscription> inscriptions = new ArrayList<Inscription>();
	private ArrayList<Appartenance> appartenances = new ArrayList<Appartenance>();
	private ArrayList<Score> scores = new ArrayList<Score>();
	private final static int NO_KEY = -1;
	
	private boolean allInscriptionsLoaded = false;
	private boolean allAppartenancesLoaded = false;
	private boolean allScoresLoaded = false;
	
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
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	//Categorie //Club
	public Categorie getCategorie()
	{
		return categorie;
	}
	
	public void setCategorie(Categorie categorie) throws DataAccessException
	{
		this.categorie = categorie;
	}
	
	public int getNumCategorie()
	{
		return categorie.getNum();
	}
	
	public Club getClub()
	{
		return club;
	}
	
	public void setClub(Club club) throws DataAccessException
	{
		this.club = club;
	}
	
	public int getNumClub()
	{
		return club.getNum();
	}
	
	Equipe(int num, String nom, Categorie categorie, Club club) throws DataAccessException
	{
		setNum(num);
		setNom(nom);
		setCategorie(categorie);
		setClub(club);
	}
	
	public Equipe(String nom, Categorie categorie, Club club) throws DataAccessException
	{ 
		this(NO_KEY, nom, categorie, club);
	}

	// Inscription
		public int getNbInscriptions() throws DataAccessException 
		{
			loadAllInscriptions();
			return inscriptions.size();
		}

		public Inscription getInscription(int index) throws DataAccessException 
		{
			loadAllInscriptions();
			return inscriptions.get(index);
		}
		
		private boolean possedeInscription(Inscription inscription) throws DataAccessException 
		{
			loadAllInscriptions();
			return inscriptions.contains(inscription);
		}
		
		private void loadAllInscriptions() throws DataAccessException
		{
			if (!allInscriptionsLoaded)
			{ 
				Root root = getClub().getLigue().getRoot();
				root.loadAllInscriptions(this);
				allInscriptionsLoaded = true;
			}
		}
		
		public void addInscription(Inscription inscription) throws DataAccessException 
		{ 
			loadAllInscriptions();
			if(!possedeInscription(inscription))
			{
				inscriptions.add(inscription);
				inscription.setEquipe(this);
			}
		}

		public void removeInscription(Inscription inscription) throws DataAccessException 
		{
			loadAllInscriptions();
			if(possedeInscription(inscription))
			{
				inscriptions.remove(inscription);
				inscription.setEquipe(null);
			}
		}
		
		// Appartenance
		
	public int getNbAppartenance() throws DataAccessException 
	{
		loadAllAppartenances();
		return appartenances.size();
	}

	public Appartenance getAppartenance(int index) throws DataAccessException
	{
		loadAllAppartenances();
		return appartenances.get(index);
	}
	
	private boolean possedeAppartenance(Appartenance appartenance) throws DataAccessException 
	{
		loadAllAppartenances();
		return appartenances.contains(appartenance);
	}
	
	private void loadAllAppartenances() throws DataAccessException
	{
		if (!allAppartenancesLoaded)
		{
			Root root = getClub().getLigue().getRoot();
			root.loadAllAppartenances(this);
			allAppartenancesLoaded = true;
		}
	} 
	
	public void addAppartenance(Appartenance appartenance) throws DataAccessException 
	{
		loadAllAppartenances();
		if(!possedeAppartenance(appartenance))
		{
			appartenances.add(appartenance);
			appartenance.setEquipe(this);
		}
	}

	public void removeAppartenance(Appartenance appartenance) throws DataAccessException 
	{
		loadAllAppartenances();
		if(possedeAppartenance(appartenance))
		{
			appartenances.remove(appartenance);
			appartenance.setEquipe(null);
		}
	}
	
	// Score
	
	public int getNbScore() throws DataAccessException 
	{
		loadAllScores();
		return scores.size();
	}

	public Score getScore(int index) throws DataAccessException
	{
		loadAllScores();
		return scores.get(index);
	}
	
	private boolean possedeScore(Score score) throws DataAccessException 
	{
		loadAllScores();
		return scores.contains(score);
	}
	
	private void loadAllScores() throws DataAccessException
	{
		if (!allScoresLoaded)
		{
			Root root = getClub().getLigue().getRoot();
			root.loadAllScores(this);
			allScoresLoaded = true;
		}
	}
	
	public void addScore(Score score) throws DataAccessException 
	{
		loadAllScores();
		if(!possedeScore(score))
		{
			scores.add(score);
			score.setEquipe(this);
		}
	}

	public void removeScore(Score score) throws DataAccessException 
	{
		loadAllScores();
		if(possedeScore(score))
		{
			scores.remove(score);
			score.setEquipe(null);
		}
	}

}
