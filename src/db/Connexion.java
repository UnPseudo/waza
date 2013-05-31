package db;

import java.sql.*;

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
  */
 public void sqlBatch(String[] script) throws SQLException
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
 
 /**
  * Méthode pour préparer une insertion d'enregistrement dans la BDD
  * Génère les clés primaire de la BDD et lève une exception en cas d'erreur de clé
  * @param req
  * @return
  * @throws SQLException
  */
 public int sqlInsert(String req) throws SQLException
 {
  if (DEBUG_MODE)
   System.out.println(req);
  Statement s = connect.createStatement();
  s.executeUpdate(req, 1) ;
  ResultSet rs = s.getGeneratedKeys();
  if (rs.next())
   return rs.getInt(1);
  throw new SQLException("no value inserted");
 }
 
 /**
  * Méthode pour préparer une selection d'enregistrement
  * @param req
  * @return
  * @throws SQLException
  */
 public ResultSet sqlSelect(String req) throws SQLException
 {
  if (DEBUG_MODE)
   System.out.println(req);
  Statement s = connect.createStatement();
  return s.executeQuery(req);
 }
 
 /**
  * Méthode pour préparer une mise à jour ou une suppression
  * @param req
  * @throws SQLException
  */
 public void sqlUpdate(String req) throws SQLException
 {
  if(DEBUG_MODE)
   System.out.println(req);
  transmission.executeUpdate(req);
 }

}