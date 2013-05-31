package metier;

import java.sql.SQLException;
import java.util.ArrayList;

public class Club
{
	private int num = NO_KEY;
	private Ligue ligue;
	private String nom = null;
	private String description = null;
	private ArrayList<Utilisateur> utilisateurs;
	private ArrayList<Equipe> equipes;
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
	
	public void setLigue(Ligue ligue) throws SQLException
	{
		if (this.ligue != ligue)
		{
			if (this.ligue != null)
				this.ligue.removeClub(this);
			this.ligue = ligue;
			if (this.ligue != null)
				ligue.addClub(this);
		}
	}
	
	public int getNumLigue()
	{
		return ligue.getNum();
	}
	
	Club(int num, String nom, Ligue ligue) throws SQLException
	{
		setNum(num);
		setNom(nom);
		setLigue(ligue);
	}

	public Club(String nom, Ligue ligue) throws SQLException
	{
		this(NO_KEY, nom, ligue);
	}
	
	// Utilisateur
	private boolean possedeUtilisateur(Utilisateur utilisateur) throws SQLException 
	{
		loadAllUtilisateursClub();
		return utilisateurs.contains(utilisateur);
	}
	
	private void loadAllUtilisateursClub() throws SQLException // A modifier
	{
		if (utilisateurs == null)
		{
			utilisateurs = new ArrayList<Utilisateur>();
			RootSuperAdmin rootSuperAdmin = getLigue().getRootSuperAdmin();
			rootSuperAdmin.loadUtilisateurs(this);
		}
	}

	public void addUtilisateur(Utilisateur utilisateur) throws SQLException 
	{
		loadAllUtilisateursClub();
		if(!possedeUtilisateur(utilisateur))
		{
			utilisateurs.add(utilisateur);
			utilisateur.setClub(this);
		}
	}

	public void removeUtilisateur(Utilisateur utilisateur) throws SQLException 
	{
		loadAllUtilisateursClub();
		if(possedeUtilisateur(utilisateur))
		{
			utilisateurs.remove(utilisateur);
			utilisateur.setClub(null);
		}
	}

	//Equipe
	private boolean possedeEquipe(Equipe equipe) throws SQLException 
	{
		loadAllEquipesClub();
		return equipes.contains(equipe);
	}
	
	private void loadAllEquipesClub() throws SQLException // A modifier
	{
		if (equipes == null)
		{
			equipes = new ArrayList<Equipe>();
			RootSuperAdmin rootSuperAdmin = getLigue().getRootSuperAdmin();
			rootSuperAdmin.loadEquipes(this);
		}
	}
	
	public void addEquipe(Equipe equipe) throws SQLException 
	{
		loadAllEquipesClub();
		if(!possedeEquipe(equipe))
		{
			equipes.add(equipe);
			equipe.setClub(this);
		}
	}

	public void removeEquipe(Equipe equipe) throws SQLException 
	{
		loadAllEquipesClub();
		if(possedeEquipe(equipe))
		{
			equipes.remove(equipe);
			equipe.setClub(null);
		}
	}
}
