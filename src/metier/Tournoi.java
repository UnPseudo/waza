package metier;

import java.sql.SQLException;
import java.util.ArrayList;

public class Tournoi
{
	private int num = NO_KEY;
	private Ligue ligue;
	private Categorie categorie;
	private String nom = null;
	private String description = null;
	private ArrayList<EtapeTournoi> etapesTournoi;
	private ArrayList<EquipeInscriteTournoi> equipeInscriteTournois;
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
	
	public String setDescription(String description)
	{
		return this.description = description;
	}
	
	//Ligue //Categorie
	public Ligue getLigue()
	{
		return ligue;
	}
	
	public void setLigue(Ligue ligue) throws SQLException
	{
		if (this.ligue != ligue)
		{
			if (this.ligue != null)
				this.ligue.removeTournoi(this);
			this.ligue = ligue;
			if (this.ligue != null)
				ligue.addTournoi(this);
		}
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
	
	public void setCategorie(Categorie categorie) throws SQLException
	{
		if (this.categorie != categorie)
		{
			if (this.categorie != null)
				this.categorie.removeTournoi(this);
			this.categorie = categorie;
			if (this.categorie != null)
				categorie.addTournoi(this);
		}
	}
	
	Tournoi(int num, String nom, String description, Ligue ligue, Categorie categorie) throws SQLException
	{
		setNum(num);
		setNom(nom);
		setDescription(description);
		setLigue(ligue);
		setCategorie(categorie);
	}

	public Tournoi(String nom, String description, Ligue ligue, Categorie categorie) throws SQLException
	{
		this(NO_KEY, nom, description, ligue, categorie);
	}
	
	//Etape tournoi
	public int getNbEtapesTournoi() throws SQLException 
	{
		loadAllEtapesTournoi();
		return etapesTournoi.size();
	}

	public EtapeTournoi getEtapeTournoi(int index) throws SQLException 
	{
		loadAllEtapesTournoi();
		return etapesTournoi.get(index);
	}
	
	private boolean possedeEtapeTournoi(EtapeTournoi etapeTournoi) throws SQLException 
	{
		loadAllEtapesTournoi();
		return etapesTournoi.contains(etapeTournoi);
	}
	
	private void loadAllEtapesTournoi() throws SQLException
	{
		if (etapesTournoi == null)
		{
			etapesTournoi = new ArrayList<EtapeTournoi>();
			RootSuperAdmin rootSuperAdmin = getLigue().getRootSuperAdmin();
			rootSuperAdmin.loadEtapeTournoi(this); //!!!!!!!!
		}
	}
	
	public void addEtapeTournoi(EtapeTournoi etapeTournoi) throws SQLException 
	{
		loadAllEtapesTournoi();
		if(!possedeEtapeTournoi(etapeTournoi))
		{
			etapesTournoi.add(etapeTournoi);
			etapeTournoi.setTournoi(this);
		}
	}

	public void removeEtapeTournoi(EtapeTournoi etapeTournoi) throws SQLException 
	{
		loadAllEtapesTournoi();
		if(possedeEtapeTournoi(etapeTournoi))
		{
			etapesTournoi.remove(etapeTournoi);
			etapeTournoi.setTournoi(null);
		}
	}
	
	// EquipeInscriteTournoi
	public int getNbEquipeInscriteTournois() throws SQLException 
	{
		loadAllEquipeInscriteTournois();
		return equipeInscriteTournois.size();
	}

	public EquipeInscriteTournoi getEquipeInscriteTournoi(int index) throws SQLException 
	{
		loadAllEquipeInscriteTournois();
		return equipeInscriteTournois.get(index);
	}
	
	private boolean possedeEquipeInscriteTournoi(EquipeInscriteTournoi equipeInscriteTournoi) throws SQLException 
	{
		loadAllEquipeInscriteTournois();
		return equipeInscriteTournois.contains(equipeInscriteTournoi);
	}
	
	private void loadAllEquipeInscriteTournois() throws SQLException
	{
		if (equipeInscriteTournois == null)
		{
			equipeInscriteTournois = new ArrayList<EquipeInscriteTournoi>();
			RootSuperAdmin rootSuperAdmin = getLigue().getRootSuperAdmin();
			rootSuperAdmin.loadEquipeInscriteTournois(this);
		}
	}
	
	public void addEquipeInscriteTournoi(EquipeInscriteTournoi equipeInscriteTournoi) throws SQLException 
	{
		loadAllEquipeInscriteTournois();
		if(!possedeEquipeInscriteTournoi(equipeInscriteTournoi))
		{
			equipeInscriteTournois.add(equipeInscriteTournoi);
			equipeInscriteTournoi.setTournoi(this);
		}
	}

	public void removeEquipeInscriteTournoi(EquipeInscriteTournoi equipeInscriteTournoi) throws SQLException 
	{
		loadAllEquipeInscriteTournois();
		if(possedeEquipeInscriteTournoi(equipeInscriteTournoi))
		{
			equipeInscriteTournois.remove(equipeInscriteTournoi);
			equipeInscriteTournoi.setTournoi(null);
		}
	}
}
