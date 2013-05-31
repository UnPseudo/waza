package metier;

import java.sql.SQLException;
import java.util.ArrayList;

//TODO bug : les mails ne sont pas supprimés de la base quand un contact est supprimé


public class Type
{
	private int num = NO_KEY;
	private RootSuperAdmin rootSuperAdmin;
	private String nom;
	private String description;
	private ArrayList<Utilisateur> utilisateurs;
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
	Type(RootSuperAdmin rootSuperAdmin, int num, String nom, String description) 
	{
		this.rootSuperAdmin = rootSuperAdmin;
		setNum(num);
		setNom(nom);
		setDescription(description);
	}
	
	public Type(RootSuperAdmin rootSuperAdmin, String nom, String description) 
	{
		this(rootSuperAdmin, NO_KEY, nom, description);
	}

	//	
	public int getNbUtilisateurs() throws SQLException 
	{
		loadAllUtilisateurs();
		return utilisateurs.size();
	}

	public Utilisateur getUtilisateur(int index) throws SQLException 
	{
		loadAllUtilisateurs();
		return utilisateurs.get(index);
	}

	private boolean possedeUtilisateur(Utilisateur utilisateur) throws SQLException 
	{
		loadAllUtilisateurs();
		return utilisateurs.contains(utilisateur);
	}
	
	private void loadAllUtilisateurs() throws SQLException
	{
		if (utilisateurs == null)
		{
			utilisateurs = new ArrayList<Utilisateur>();
			rootSuperAdmin.loadUtilisateurs(this);
		}
	}
	
	public void addUtilisateur(Utilisateur utilisateur) throws SQLException 
	{
		loadAllUtilisateurs();
		if(!possedeUtilisateur(utilisateur))
		{
			utilisateurs.add(utilisateur);
			utilisateur.setType(this);
		}
	}

	public void removeUtilisateur(Utilisateur utilisateur) throws SQLException 
	{
		loadAllUtilisateurs();
		if(possedeUtilisateur(utilisateur))
		{
			utilisateurs.remove(utilisateur);
			utilisateur.setType(null);
		}
	}

	
}
