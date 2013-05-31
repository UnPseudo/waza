package db;

import java.sql.*;

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
 private final static boolean DEBUG_MODE = true;

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
  * M�thode pour pr�parer une insertion d'enregistrement dans la BDD
  * G�n�re les cl�s primaire de la BDD et l�ve une exception en cas d'erreur de cl�
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
  * M�thode pour pr�parer une selection d'enregistrement
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
  * M�thode pour pr�parer une mise � jour ou une suppression
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