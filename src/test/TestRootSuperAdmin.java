package test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;


import metier.DataAccessException;
import metier.Ligue;
import metier.Root;
import metier.Type;
import metier.Utilisateur;
import metier.Club;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRootSuperAdmin 
{
	private Root is;
	private Type type;
	private Ligue ligue;
	private Utilisateur utilisateur;
	private Club club;
	
	@Before
	public void setUp() throws ClassNotFoundException, DataAccessException
	{
		is= new Root();
		//type = new Type(is, "nom type test", "description type test");
		//ligue = new Ligue(is, "nom ligue test", "description ligue test");
		//club = new Club("nom", ligue);
		//utilisateur = new Utilisateur("nom", "prenom", 11111111, 11111111, "mail", "mdp", type, club);
	}
	
	@Test
 

	
	/*
	public void testInsertType() throws DataAccessException
	{	
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
		int nbAvantInsertion = is.getNbLigues();
		is.save(ligue);
		nbAvantInsertion++;
		assertTrue("la ligue n'a pas été ajouté", is.getNbLigues()== nbAvantInsertion);
	}*/
	
	/*public void testInsertClub() throws DataAccessException
	{
		Ligue ligue = new Ligue(is, "nom ligue test", "description ligue test");
		is.save(ligue);
		club = new Club("nom", ligue);
		int nbAvantInsertion = ligue.getNbClubs();
		is.save(club);
		nbAvantInsertion++;
		assertTrue("l'utilisateur n'a pas été ajouté", ligue.getNbClubs()== nbAvantInsertion);
	}*/
	
	
	
	public void testInsertUtilisateur() throws DataAccessException
	{
		type = new Type(is, "nom type test", "description type test");
		club = new Club("nom", ligue);
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
	
	/*public void testUpdateLigue() throws DataAccessException
	{
		
		int nbAvantInsertion = is.getNbLigues();
		is.save(ligue);
		nbAvantInsertion++;
		assertTrue("la ligue n'a pas été ajouté", is.getNbLigues()== nbAvantInsertion);
		
		
	}*/
		
}