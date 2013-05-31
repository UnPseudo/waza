package test;

import java.io.IOException;
import java.sql.SQLException;


import metier.Ligue;
import metier.RootSuperAdmin;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRootSuperAdmin 
{

	private RootSuperAdmin is;
	@Before
	public void setUp() throws ClassNotFoundException, SQLException
	{
		is= new RootSuperAdmin();
	}
	
	@Test


	public void testInsertLigue() throws SQLException
	{
		Ligue tenis = new Ligue(is, "Ligue de tenis de Lorraine");
		is.addLigue(tenis);
		is.getNbLigues();
		assertTrue("La ligue a bien été ajouté", is.getNbLigues()==1);
		
	}
		
}