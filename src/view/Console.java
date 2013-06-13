package view;

import java.util.Scanner; 

import metier.*;

public class Console {

	private static Root root;
	public static int saisieInt()
	{
		Scanner sc = new Scanner(System.in); 
		System.out.println(""); 
		return sc.nextInt(); 
	}
	
	public static String saisieStr()
	{
		Scanner sc = new Scanner(System.in); 
		System.out.println("Saisie :"); 
		return sc.next(); 
	}
	
	public static void menuPrincipal() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-Ajouter"); 
		System.out.println("2-Modifier");
		System.out.println("3-Supprimer");
		System.out.println("4-Afficher");
		int rep= saisieInt();
		if (rep == 1)
			menuAjouter();
		else if (rep == 2)
			menuModifier();
		//else if (rep == 3)
			//menuSupprimer();
		//else if (rep == 2)
			//menuAfficher());
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuAjouter() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-Ajouter Type");
		int rep= saisieInt();
		if (rep == 0)
			menuPrincipal();
		if (rep == 1)
			menuAjouterType();
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuAjouterType() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("description:");
		String description= saisieStr();
		//Root root= new Root();
		Type type = new Type(root, nom, description);
		root.save(type);
		System.out.println("Type ajouté");
		menuAjouter();
	}
	
	public static void menuModifier() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-Modifier Type");
		System.out.println("2-Modifier Ligue");
		System.out.println("3-Modifier Club");
		
		int rep= saisieInt();
		if (rep == 0)
			menuPrincipal();
		else if (rep == 1)
			menuModifierType();
		else if (rep == 2)
			menuModifierLigue();
		else if (rep == 3)
			menuModifierClub();
		else if (rep == 4)
			menuModifierUtilisateur();
		else if (rep == 5)
			menuModifierEquipe();
		else if (rep == 6)
			menuModifierUtilisateur();
		else if (rep == 7)
			menuModifierTournoi();
		else if (rep == 8)
			menuModifierEtape();
		else if (rep == 9)
			menuModifierRencontre();
		else if (rep == 10)
			menuModifierScore();
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuModifierType() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id a modifier:");
		int id= saisieInt();
		Type type = root.loadType(id);
		System.out.println(type.getNum());
		System.out.println(type.getNom());
		System.out.println(type.getDescription());
		
		System.out.println("1-Modifier Type Nom");
		System.out.println("2-Modifier Type Description");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			type.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			type.setDescription(str);
		}
		else
		{
			System.out.println("erreur");
			menuModifierType();
		}
		root.save(type);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierLigue() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Ligue a modifier:");
		int id= saisieInt();
		Ligue ligue = root.loadLigue(id);
		System.out.println(ligue.getNum());
		System.out.println(ligue.getNom());
		System.out.println(ligue.getDescription());
		
		System.out.println("1-Modifier Ligue Nom");
		System.out.println("2-Modifier Ligue Description");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			ligue.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			ligue.setDescription(str);
		}
		else
		{
			System.out.println("erreur");
			menuModifierLigue();
		}
		root.save(ligue);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierClub() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Club a modifier:");
		int id= saisieInt();
		Club club = root.loadClub(id);
		System.out.println("id: " + club.getNum());
		System.out.println("nom: " + club.getNom());
		System.out.println("description: " + club.getDescription());
		System.out.println("ligue_id: " + club.getNumLigue());
		
		System.out.println("1-Modifier Club Nom");
		System.out.println("2-Modifier Club Description");
		System.out.println("3-Modifier Club Ligue_id");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			club.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			club.setDescription(str);
		}
		else if (rep == 3)
		{
			System.out.println("Nouvelle Ligue_id:");
			int i= saisieInt();
			club.setLigue(root.loadLigue(i));
		}
		else
		{
			System.out.println("erreur");
			menuModifierClub();
		}
		root.save(club);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierUtilisateur() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Utilisateur a modifier:");
		int id= saisieInt();
		Club club = root.loadClub(id);
		System.out.println("id: " + club.getNum());
		System.out.println("nom: " + club.getNom());
		System.out.println("description: " + club.getDescription());
		System.out.println("ligue_id: " + club.getNumLigue());
		
		System.out.println("1-Modifier Club Nom");
		System.out.println("2-Modifier Club Description");
		System.out.println("3-Modifier Club Ligue_id");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			club.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			club.setDescription(str);
		}
		else if (rep == 3)
		{
			System.out.println("Nouvelle Ligue_id:");
			int i= saisieInt();
			club.setLigue(root.loadLigue(i));
		}
		else
		{
			System.out.println("erreur");
			menuModifierClub();
		}
		root.save(club);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	/*
	public static void menuPrincipal() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-menu Utilisateur"); 
		System.out.println("2-menu Ligue");
		int rep= saisieInt();
		if (rep == 1)
		{
			menuUtilisateur();
		}
		if (rep == 2)
		{
			menuLigue();
		}
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuUtilisateur()
	{
		System.out.println("1-Ajout Utilisateur"); 
		System.out.println("2-Modif Utilisateur");
		System.out.println("3-Suppr Utilisateur");
		int rep= saisieInt();
	}
	
	public static void formAddLigue() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("description:");
		String description= saisieStr();
		Root root = new Root();
		Ligue ligue = new Ligue(root, nom, description);
		root.save(ligue);
	}
	
	public static void menuLigue() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-Ajout Ligue");
		System.out.println("2-Modif Ligue"); 
		System.out.println("3-Suppr Ligue"); 
		int rep= saisieInt();
		if (rep == 1)
		{
			formAddLigue();
		}
		else
		{
			System.out.println("erreur");
			menuLigue();
		}
	}*/
	Console() throws ClassNotFoundException, DataAccessException
	{
		root = new Root();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, DataAccessException
	{
		Console s = new Console();
		s.menuPrincipal();
	}
}