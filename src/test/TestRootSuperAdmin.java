package test;

import java.io.IOException;
import java.sql.SQLException;


import metier.DataAccessException;
import metier.Ligue;
import metier.RootSuperAdmin;
import metier.Type;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRootSuperAdmin 
{
	private Type type;
	private RootSuperAdmin is;
	@Before
	public void setUp() throws ClassNotFoundException, DataAccessException
	{
		is= new RootSuperAdmin();
		type = new Type(is, "membre", "Joueur de la M2L");
	}
	
	@Test
 

	/*public void testInsertLigue() throws SQLException
	{
		Ligue tenis = new Ligue(is, "Ligue de tenis de Lorraine");
		is.addLigue(tenis);
		is.getNbLigues();
		assertTrue("La ligue a bien été ajouté", is.getNbLigues()==1);
		
	}*/
	
	public void testInsertType() throws DataAccessException
	{
		
		int nbTypeAvantInsertion = is.getNbTypes();
		is.save(type);
		nbTypeAvantInsertion++;
		assertTrue("le type n'a pas été ajouté", is.getNbTypes()== nbTypeAvantInsertion);
		
		
	}
		
}