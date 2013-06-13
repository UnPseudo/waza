package metier;

import java.util.ArrayList;

public class Tournoi
{
	private int num = NO_KEY;
	private Ligue ligue;
	private Categorie categorie;
	private String nom = null;
	private String description = null;
	private ArrayList<Etape> etapes = new ArrayList<Etape>();
	private ArrayList<Inscription> inscriptions = new ArrayList<Inscription>();
	private final static int NO_KEY = -1;
	
	private boolean allEtapesLoaded = false;
	private boolean allInscriptionsLoaded = false;
	
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
	
	public String setDescription(String description)
	{
		return this.description = description;
	}
	
	//Ligue //Categorie
	public Ligue getLigue()
	{
		return ligue;
	}
	
	public void setLigue(Ligue ligue) throws DataAccessException
	{
		this.ligue = ligue;
		/*if (this.ligue != ligue)
		{
			if (this.ligue != null)
				this.ligue.removeTournoi(this);
			this.ligue = ligue;
			if (this.ligue != null)
				ligue.addTournoi(this);
		}*/
	}
	
	public int getNumLigue()
	{
		return ligue.getNum();
	}
	
	public int getNumCategorie()
	{
		return categorie.getNum();
	}
	
	public Categorie getCategorie()
	{
		return categorie;
	}
	
	public void setCategorie(Categorie categorie) throws DataAccessException
	{
		this.categorie = categorie;
		/*if (this.categorie != categorie)
		{
			if (this.categorie != null)
				this.categorie.removeTournoi(this);
			this.categorie = categorie;
			if (this.categorie != null)
				categorie.addTournoi(this);
		}*/
	}
	
	Tournoi(int num, String nom, String description, Ligue ligue, Categorie categorie) throws DataAccessException
	{
		setNum(num);
		setNom(nom);
		setDescription(description);
		setLigue(ligue);
		setCategorie(categorie);
	}

	public Tournoi(String nom, String description, Ligue ligue, Categorie categorie) throws DataAccessException
	{
		this(NO_KEY, nom, description, ligue, categorie);
	}
	
	//Etape
	public int getNbEtapes() throws DataAccessException 
	{
		loadAllEtapes();
		return etapes.size();
	}

	public Etape getEtape(int index) throws DataAccessException 
	{
		loadAllEtapes();
		return etapes.get(index);
	}
	
	private boolean possedeEtape(Etape etape) throws DataAccessException 
	{
		loadAllEtapes();
		return etapes.contains(etape);
	}
	
	private void loadAllEtapes() throws DataAccessException
	{
		if (!allEtapesLoaded)
		{
			Root root = getLigue().getRoot();
			root.loadAllEtapes(this);
			allEtapesLoaded = true;
		}
	}
	 
	public void addEtape(Etape etape) throws DataAccessException 
	{
		loadAllEtapes();
		if(!possedeEtape(etape))
		{
			etapes.add(etape);
			etape.setTournoi(this);
		}
	}

	public void removeEtape(Etape etape) throws DataAccessException 
	{
		loadAllEtapes();
		if(possedeEtape(etape))
		{
			etapes.remove(etape);
			etape.setTournoi(null);
		}
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
			Root root = getLigue().getRoot();
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
			inscription.setTournoi(this);
		}
	}

	public void removeInscription(Inscription inscription) throws DataAccessException 
	{
		loadAllInscriptions();
		if(possedeInscription(inscription))
		{
			inscriptions.remove(inscription);
			inscription.setTournoi(null);
		}
	}
}
