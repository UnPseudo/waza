package db;

import java.sql.*;

import metier.DataAccessException;

/** Classe assurant la connexion à la base de donnée (couche BDD)*/

public class Connexion
{
 /* 
  * Connect permet de charger le driver et assurer la connexion
  * Statement permet de créer l'espace pour stocker une requête SQL
  * ResultSet est ce qui permet de stocker certaines requêtes
  * URL = accès à la BDD
  * le debug_mode est pour afficher les informations dans la console
  */
  
 Connection connect;
 Statement transmission;
 ResultSet result;
 String url;
 private final static boolean DEBUG_MODE = true;

 /**
  * Constructeur de la classe connect pour établir la connexion à la BDD
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
  * Méthode pour générer le script de création de table
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
  * Méthode pour préparer une insertion d'enregistrement dans la BDD
  * Génère les clés primaire de la BDD et lève une exception en cas d'erreur de clé
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
  * Méthode pour préparer une selection d'enregistrement
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
  * Méthode pour préparer une mise à jour ou une suppression
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