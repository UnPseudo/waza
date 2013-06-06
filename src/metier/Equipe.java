package metier;

import java.util.ArrayList;

public class Equipe
{
	private int num = NO_KEY;
	private Club club;
	private Categorie categorie;
	private String nom = null;
	private ArrayList<EquipeInscriteTournoi> equipeInscriteTournois;
	private final static int NO_KEY = -1;
	
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
		if (this.categorie != categorie)
		{
			if (this.categorie != null)
				this.categorie.removeEquipe(this);
			this.categorie = categorie;
			if (this.categorie != null)
				categorie.addEquipe(this);
		}
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
		if (this.club != club)
		{
			if (this.club != null)
				this.club.removeEquipe(this);
			this.club = club;
			if (this.club != null)
				club.addEquipe(this);
		}
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

	// EquipeInscriteTournoi
		public int getNbEquipeInscriteTournois() throws DataAccessException 
		{
			loadAllEquipeInscriteTournois();
			return equipeInscriteTournois.size();
		}

		public EquipeInscriteTournoi getEquipeInscriteTournoi(int index) throws DataAccessException 
		{
			loadAllEquipeInscriteTournois();
			return equipeInscriteTournois.get(index);
		}
		
		private boolean possedeEquipeInscriteTournoi(EquipeInscriteTournoi equipeInscriteTournoi) throws DataAccessException 
		{
			loadAllEquipeInscriteTournois();
			return equipeInscriteTournois.contains(equipeInscriteTournoi);
		}
		
		private void loadAllEquipeInscriteTournois() throws DataAccessException
		{
			if (equipeInscriteTournois == null)
			{
				equipeInscriteTournois = new ArrayList<EquipeInscriteTournoi>();
				RootSuperAdmin rootSuperAdmin = getClub().getLigue().getRootSuperAdmin();
				rootSuperAdmin.loadEquipeInscriteTournois(this);
			}
		}
		
		public void addEquipeInscriteTournoi(EquipeInscriteTournoi equipeInscriteTournoi) throws DataAccessException 
		{
			loadAllEquipeInscriteTournois();
			if(!possedeEquipeInscriteTournoi(equipeInscriteTournoi))
			{
				equipeInscriteTournois.add(equipeInscriteTournoi);
				equipeInscriteTournoi.setEquipe(this);
			}
		}

		public void removeEquipeInscriteTournoi(EquipeInscriteTournoi equipeInscriteTournoi) throws DataAccessException 
		{
			loadAllEquipeInscriteTournois();
			if(possedeEquipeInscriteTournoi(equipeInscriteTournoi))
			{
				equipeInscriteTournois.remove(equipeInscriteTournoi);
				equipeInscriteTournoi.setEquipe(null);
			}
		}
		
	
	
}
