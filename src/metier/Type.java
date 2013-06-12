package metier;

import java.util.ArrayList;

//TODO bug : les mails ne sont pas supprimés de la base quand un contact est supprimé


public class Type
{
	private int num = NO_KEY;
	private Root root;
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
	Type(Root root, int num, String nom, String description) 
	{
		this.root = root;
		setNum(num);
		setNom(nom);
		setDescription(description);
	}
	
	public Type(Root root, String nom, String description) 
	{
		this(root, NO_KEY, nom, description);
	}

	//	
	public int getNbUtilisateurs() throws DataAccessException 
	{
		loadAllUtilisateurs();
		return utilisateurs.size();
	}

	public Utilisateur getUtilisateur(int index) throws DataAccessException 
	{
		loadAllUtilisateurs();
		return utilisateurs.get(index);
	}

	private boolean possedeUtilisateur(Utilisateur utilisateur) throws DataAccessException 
	{
		loadAllUtilisateurs();
		return utilisateurs.contains(utilisateur);
	}
	
	private void loadAllUtilisateurs() throws DataAccessException
	{
		if (utilisateurs == null)
		{
			utilisateurs = new ArrayList<Utilisateur>();
			root.loadUtilisateurs(this);
		}
	} 
	  
	public void addUtilisateur(Utilisateur utilisateur) throws DataAccessException 
	{
		loadAllUtilisateurs();
		if(!possedeUtilisateur(utilisateur))
		{
			utilisateurs.add(utilisateur);
			utilisateur.setType(this);
		}
	}

	public void removeUtilisateur(Utilisateur utilisateur) throws DataAccessException 
	{
		loadAllUtilisateurs();
		if(possedeUtilisateur(utilisateur))
		{
			utilisateurs.remove(utilisateur);
			utilisateur.setType(null);
		}
	}

	
}
