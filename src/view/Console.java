package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner; 

import poubelle.RootSuperAdmin;

import metier.Ligue;

public class Console {

	public static int saisieInt()
	{
		Scanner sc = new Scanner(System.in); 
		System.out.println("Saisie :"); 
		return sc.nextInt(); 
	}
	
	public static String saisieStr()
	{
		Scanner sc = new Scanner(System.in); 
		System.out.println("Saisie :"); 
		return sc.next(); 
	}
	
	public static void menuPrincipal() throws ClassNotFoundException, SQLException, IOException
	{
		System.out.println("1-menu SuperAdmin"); 
		int rep= saisieInt();
		if (rep == 1)
		{
			menuPrincipalSuperAdmin();
		}
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuPrincipalSuperAdmin() throws ClassNotFoundException, SQLException, IOException
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
			menuPrincipalSuperAdmin();
		}
	}
	
	public static void menuUtilisateur()
	{
		System.out.println("1-Ajout Utilisateur"); 
		System.out.println("2-Modif Utilisateur");
		System.out.println("3-Suppr Utilisateur");
		int rep= saisieInt();
	}
	
	public static void formAddLigue() throws ClassNotFoundException, SQLException, IOException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		RootSuperAdmin rootSuperAdmin = new RootSuperAdmin();
		Ligue ligue = new Ligue(rootSuperAdmin, nom);
		try
		{
			rootSuperAdmin.add(ligue);
		}
		catch (SQLException e)
		{
			System.out.println("fail");
		}
	}
	
	public static void menuLigue() throws ClassNotFoundException, SQLException, IOException
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
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
	{
		Console s = new Console();
		Console.menuPrincipalSuperAdmin();
	}
}