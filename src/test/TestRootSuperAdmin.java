package test;

import java.util.Random;


import metier.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
 
public class TestRootSuperAdmin 
{
	private Root is;
	private Type type;
	private Ligue ligue;
	private Tournoi tournoi;
	private Categorie categorie;
	private Etape etape;
	private Rencontre rencontre;
	private Utilisateur utilisateur;
	private Club club;
	private Equipe equipe;
	private Score score;
	private Appartenance appartenance;
	private Inscription inscription;
	
	@Before
	public void setUp() throws ClassNotFoundException, DataAccessException
	{
		is= new Root();
		//type = new Type(is, "cahuette", "description type test");
		//type = new Type(is, "nom type test", "description type test");
		//ligue = new Ligue(is, "nom ligue test", "description ligue test");
		//club = new Club("nom", ligue);
		//utilisateur = new Utilisateur("nom", "prenom", 11111111, 11111111, "mail", "mdp", type, club);
	}
	
	@Test
 
	public void superTest() throws DataAccessException
	{
		is.loadType(1);
		is.loadLigue(1);
		is.loadTournoi(1);
		is.loadCategorie(1);
		is.loadEtape(1);
		is.loadRencontre(1);
		is.loadUtilisateur(1);
		is.loadClub(1);
		is.loadEquipe(1);
		ligue = new Ligue(is, "nom ligue test", "description ligue test");
		System.out.println(ligue.getNom());
		System.out.println(ligue.getDescription());
		is.save(ligue);
		System.out.println(ligue.getNom());
		System.out.println(ligue.getDescription());
		System.out.println("ligue ok");
		club = new Club("nom", ligue);
		is.save(club);
		System.out.println("club ok");
		type = new Type(is, "nom", "test");
		is.save(type);
		System.out.println("type ok");
		utilisateur = new Utilisateur(null, null, 0, 0, null, null, type, club);
		is.save(utilisateur);
		System.out.println("utilisateur ok");
		categorie = new Categorie(null, ligue);
		is.save(categorie);
		System.out.println("categorie ok");
		tournoi = new Tournoi(null, null, ligue, categorie);
		is.save(tournoi);
		System.out.println("tournoi ok");
		etape = new Etape(tournoi);
		is.save(etape);
		System.out.println("etape ok");
		rencontre = new Rencontre("ici", "0000-00-00", etape);
		is.save(rencontre);
		System.out.println("rencontre ok");
		equipe = new Equipe(null, categorie, club);
		is.save(equipe);
		System.out.println("equipe ok");
		score = new Score(rencontre, equipe, 0, false);
		is.save(score);
		System.out.println("score ok");
		appartenance = new Appartenance(utilisateur, equipe);
		is.save(appartenance);
		System.out.println("appartenance ok");
		inscription = new Inscription(equipe, tournoi);
		is.save(inscription);
		System.out.println("inscription ok");
		/*is.delete(score);
		is.delete(appartenance);
		is.delete(inscription);
		is.delete(equipe);
		is.delete(rencontre);
		is.delete(etape);
		is.delete(tournoi);
		is.delete(categorie);
		is.delete(tournoi);
		is.delete(utilisateur);
		is.delete(type);
		is.delete(club);
		is.delete(ligue);*/
		
		
	}
	
	/*
	public void testInsertType() throws DataAccessException
	{	
		type = new Type(is, "nom type test", "description type test");
		int nbAvantInsertion = is.getNbTypes();
		is.save(type);
		nbAvantInsertion++;
		assertTrue("le type n'a pas été ajouté", is.getNbTypes()== nbAvantInsertion);	
	}*/
	/*public void testDeleteType() throws DataAccessException
	{	
		int nbAvantInsertion = is.getNbTypes();
		is.save(type);
		nbAvantInsertion++;
		assertTrue("le type n'a pas été ajouté", is.getNbTypes()== nbAvantInsertion);
		nbAvantInsertion--;
		is.delete(type);
		assertTrue("le type n'a pas été ajouté", is.getNbTypes()== nbAvantInsertion);
		
		
	}*/
	/*
	public void testInsertLigue() throws DataAccessException
	{
		ligue = new Ligue(is, "nom ligue test", "description ligue test");
		int nbAvantInsertion = is.getNbLigues();
		is.save(ligue);
		nbAvantInsertion++;
		assertTrue("la ligue n'a pas été ajouté", is.getNbLigues()== nbAvantInsertion);
	}*/
	/*
	public void testInsertClub() throws DataAccessException
	{
		
		ligue = new Ligue(is, "nom ligue test", "description ligue test");
		is.save(ligue);
		Random r = new Random();
		int d = r.nextInt();
		String s = "" + d;
		Club club = new Club(s, ligue);
		int nbAvantInsertion = ligue.getNbClubs();
		is.save(club);
		nbAvantInsertion++;
		assertTrue("le club n'a pas été ajouté", ligue.getNbClubs()== nbAvantInsertion);
		boolean found = false;
		Club c ;
		for (int i = 0 ; !found && i < ligue.getNbClubs() ; i++)
		{
			c = ligue.getClub(i);
			found = c.getNom().equals(s);
		}
		assertTrue(s + " not found !", found);
	}*/
	
	
	/*
	public void testInsertUtilisateur() throws DataAccessException
	{
		ligue = new Ligue(is, "nom ligue test", "description ligue test");
		is.save(ligue);
		type = new Type(is, "nom", "test");
		is.save(type);
		club = new Club("nom", ligue);
		is.save(club);
		Random r = new Random();
		int d = r.nextInt();
		String s = "" + d;
		Utilisateur utilisateur = new Utilisateur(s , s, d, d, s, s, type, club);
		int nbAvantInsertion = type.getNbUtilisateurs();
		is.save(utilisateur);
		nbAvantInsertion++;
		assertTrue("l'utilisateur n'a pas été ajouté", type.getNbUtilisateurs()== nbAvantInsertion);
		boolean found = false;
		Utilisateur u ;
		for (int i = 0 ; !found && i < type.getNbUtilisateurs() ; i++)
		{
			u = type.getUtilisateur(i);
			found = u.getNom().equals(s);
		}
		assertTrue(s + " not found !", found);
	}
	*/
	/*public void testUpdateLigue() throws DataAccessException
	{
		
		int nbAvantInsertion = is.getNbLigues();
		is.save(ligue);
		nbAvantInsertion++;
		assertTrue("la ligue n'a pas été ajouté", is.getNbLigues()== nbAvantInsertion);
		
		
	}*/
		
}