package db;

import java.sql.*;

import metier.DataAccessException;

/** Classe assurant la connexion � la base de donn�e (couche BDD)*/

public class Connexion
{
 /* 
  * Connect permet de charger le driver et assurer la connexion
  * Statement permet de cr�er l'espace pour stocker une requ�te SQL
  * ResultSet est ce qui permet de stocker certaines requ�tes
  * URL = acc�s � la BDD
  * le debug_mode est pour afficher les informations dans la console
  */
  
 Connection connect;
 Statement transmission;
 ResultSet result;
 String url;
 private final static boolean DEBUG_MODE = false;

 /**
  * Constructeur de la classe connect pour �tablir la connexion � la BDD
  */
 public Connexion()
 {
  url= "jdbc:mysql://localhost/gestion_rencontres";
  try
  {
   Class.forName("com.mysql.jdbc.Driver").newInstance();
   connect = DriverManager.getConnection(url,"root","");
   System.out.println("Driver OK");
   System.out.println("Connexion effective");
   transmission = connect.createStatement();
   
  }
  catch (Exception e){
   System.out.println(e);
  }
 }
 
 /**
  * M�thode pour g�n�rer le script de cr�ation de table
  * @param script
 * @throws SQLException 
  * @throws DataAccessException;
  */
	public void sqlBatch(String[] script) throws DataAccessException 
	{	
		try
	  	{
			Statement s = connect.createStatement();
			for (String str : script)
			{
				if (DEBUG_MODE)
					System.out.println(str + ";");
				s.addBatch(str);
			}
			s.executeBatch();
	  	}
		catch (Exception e) 
		{
			throw new DataAccessException(e);
		}
	}
 /**
  * M�thode pour pr�parer une insertion d'enregistrement dans la BDD
  * G�n�re les cl�s primaire de la BDD et l�ve une exception en cas d'erreur de cl�
  * @param req
  * @return
  * @throws DataAccessException;
  */
	public int sqlInsert(String req, String... values) throws DataAccessException
	{
		try
		  {
			PreparedStatement s = connect.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0 ; i < values.length ; i++)
				s.setString(i+1, values[i]);
			s.executeUpdate() ;
			ResultSet rs = s.getGeneratedKeys();
			int id = -1;
			if (rs.next())
			{
				id = rs.getInt(1) ; 
				if (DEBUG_MODE)
					System.out.println("generated id = " + id);
				
			}
			else
				if (DEBUG_MODE)
					System.out.println("no id generated.");
			return id;
		  }
		  catch (Exception e) 
			{
				throw new DataAccessException(e);
			}
	}
 
 /**
  * M�thode pour pr�parer une selection d'enregistrement
  * @param req
  * @return
  * @throws DataAccessException;
  */
	public ResultSet sqlSelect(String req, String... values) throws DataAccessException
	{
		try
		  {
			PreparedStatement s = connect.prepareStatement(req);
			for (int i = 0 ; i < values.length ; i++)
				s.setString(i+1, values[i]);
			return s.executeQuery();
		  }
		  catch (Exception e) 
			{
				throw new DataAccessException(e);
			}
	}
 
 /**
  * M�thode pour pr�parer une mise � jour ou une suppression
  * @param req
  * @throws DataAccessException;
  */
 public void sqlUpdate(String req, String... values) throws DataAccessException
 {
  if(DEBUG_MODE)
   System.out.println(req);
  
  try
  {
	  PreparedStatement s = connect.prepareStatement(req);
		for (int i = 0 ; i < values.length ; i++)
			s.setString(i+1, values[i]);
		s.executeUpdate();
  }
  catch (Exception e) 
	{
		throw new DataAccessException(e);
	}
 }


}