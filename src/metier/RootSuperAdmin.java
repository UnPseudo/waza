package metier;

import java.sql.*;
import java.util.*;

import Contacts.src.metier.Contact;
import Contacts.src.metier.ContactMapper;
import Contacts.src.metier.HashMap;
import Contacts.src.metier.Integer;

import db.Connexion;

// TODO Ecrire les commentaires pour la documentation
// TODO Ecrire des test unitaires
// TODO permettre de gérer des listes de contacts,  créer une classe inscription de visibilité package 

public class RootSuperAdmin
{
	//private view.Console console;
	private int num = NO_KEY;
	private final static int NO_KEY = -1;
	private static final String[] RESET_SCRIPT = new String[] 
			{
			"drop table etape;  "+ 
			"drop table score;  "+
			"drop table rencontre;  "+
			"drop table equipe_participe_tournoi;  "+
			"drop table elimiation_directe;  "+
			"drop table ronde;  "+
			"drop table etape_tournoi;  "+
			"drop table tournoi;  "+
			"drop table utilisateur_appartient_equipe;  "+
			"drop table equipe;  "+
			"drop table categorie;  "+
			"drop table utilisateur;  "+
			"drop table type_utilisateur; "+
			"drop table club;  "+
			"drop table ligue; "};
	//TODO ajouter les foreign keys
	private static final String[] INIT_SCRIPT = new String[] 
			{
			"CREATE TABLE ligue(" +
			"	id int primary key auto_increment," +
			"	nom varchar(64)," +
			"	description text" +
			"	);" +
				
			"CREATE TABLE club(" +
			"	id int primary key auto_increment," +
			"	nom varchar(64)," +
			"	description text," +
			"	ligue_id int," +
			"	FOREIGN KEY (ligue_id) REFERENCES ligue(id)" +
			"	);" +
			
			"CREATE TABLE type_utilisateur(" +
			"	id int primary key auto_increment," +
			"	nom varchar(64)," +
			"	description text" +
			"	);" +
				
			"CREATE TABLE utilisateur(" +
			"	id int primary key auto_increment," +
			"	nom varchar(64)," +
			"	prenom varchar(64)," +
			"	mail varchar(128)," +
			"	mdp varchar(64)," +
			"	adresse varchar(128)," +
			"	code_postale varchar(5)," +
			"	ville varchar(64)," +
			"	tel_fixe varchar(10)," +
			"	tel_portable varchar(10)" +
			"	);" +
				
			"CREATE TABLE categorie(" +
			"	id int primary key auto_increment," +
			"	nom varchar(64)," +
			"	description text," +
			"	ligue_id int," +
			"	FOREIGN KEY (ligue_id) REFERENCES ligue(id)" +
			"	);" +
				
			"CREATE TABLE equipe(" +
			"	id int primary key auto_increment," + 
			"	nom varchar(64)," + 
			"	club_id int," +
			"	categorie_id int," +
			"	FOREIGN KEY (club_id) REFERENCES club(id)," +
			"	FOREIGN KEY (categorie_id) REFERENCES categorie(id)" +
			"	);" +
				
			"CREATE TABLE utilisateur_appartient_equipe(" +
			"	utilisateur_id int," + 
			"	equipe_id int," +
			"	FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id)," +
			"	FOREIGN KEY (equipe_id) REFERENCES equipe(id)," +
			"	PRIMARY KEY (utilisateur_id, equipe_id)" +
			"	);" +
				
			"CREATE TABLE tournoi(" +
			"	id int primary key auto_increment," + 
			"	nom varchar(64)," +
			"	date_debut date," +
			"	date_fin date," +
			"	description text," +
			"	ligue_id int," +
			"	categorie_id int," +
			"	FOREIGN KEY (ligue_id) REFERENCES ligue(id)," +
			"	FOREIGN KEY (categorie_id) REFERENCES categorie(id)" +
			"	);" +
				
			"CREATE TABLE etape_tournoi(" +
			"	id int primary key auto_increment," +
			"	tournoi_id int," +
			"	FOREIGN KEY (tournoi_id) REFERENCES tournoi(id)" +
			"	);" +
				
			"CREATE TABLE ronde(" +
			"	id int primary key auto_increment," +
			"	etape_tournoi_id int," +
			"	FOREIGN KEY (etape_tournoi_id) REFERENCES etape_tournoi(id)" +
			"	);" +
				
			"CREATE TABLE elimination_direct(" +
			"	id int primary key auto_increment," +
			"	etape_tournoi_id int," +
			"	FOREIGN KEY (etape_tournoi_id) REFERENCES etape_tournoi(id)" +
			"	);" +
				
			"CREATE TABLE equipe_participe_tournoi(" +
			"	equipe_id int," +
			"	tournoi_id int," +
			"	FOREIGN KEY (equipe_id) REFERENCES equipe(id)," +
			"	FOREIGN KEY (tournoi_id) REFERENCES tournoi(id)," +
			"	PRIMARY KEY (equipe_id, tournoi_id)" +
			"	);" +
			
			"CREATE TABLE rencontre(" +
			"	id int primary key auto_increment," +
			"	etape_tournoi_id int," +
			"	FOREIGN KEY (etape_tournoi_id) REFERENCES etape_tournoi(id)" +
			"	);" +
				
			"CREATE TABLE score(" +
			"	equipe_id int," +
			"	rencontre_id int," +
			"	points int," +
			"	temps time," +
			"	FOREIGN KEY (equipe_id) REFERENCES equipe(id)," +
			"	FOREIGN KEY (rencontre_id) REFERENCES rencontre(id)," +
			"	PRIMARY KEY (equipe_id, rencontre_id)" +
			"	);" +
				
			"CREATE TABLE etape(" +
			"	equipe_id int," +
			"	rencontre_id int," +
			"	num_etape int," +
			"	points int," +
			"	temps time," +
			"	FOREIGN KEY (equipe_id) REFERENCES score(equipe_id)," +
			"	FOREIGN KEY (rencontre_id) REFERENCES score(rencontre_id)," +
			"	PRIMARY KEY (equipe_id, rencontre_id)" +
			"	);"
			};
	private Mapper mapper;
	
	Connexion connexion;
	
	private ArrayList<Ligue> ligues = null;
	private ArrayList<Type> types = null;
	private HashMap<Integer, Ligue> liguesByNum = new HashMap<Integer, Ligue> ();
	private HashMap<Integer, Type> typesByNum = new HashMap<Integer, Type> ();
	
	public void addLigue(Ligue ligue) throws SQLException
	{
		int id = connexion.sqlInsert("insert into ligue(nom, description) values ('" +
				ligue.getNom() + "', '" + ligue.getDescription() + "')");
		ligue.setNum(id);
		ligues.add(ligue);
	}
	
	public void updateLigue(Ligue ligue) throws SQLException
	{
		connexion.sqlUpdate("update ligue set nom = '" + ligue.getNom() + "' " +
											"description = '" + ligue.getDescription() + "' " +
				"where numContact = " + ligue.getNum());
	}
	
	public void deleteLigue(Ligue ligue) throws SQLException
	{
		while(ligue.getNbClubs() != 0)
		{
			Club club = ligue.getClub(0);
			deleteClub(club);
		}
		connexion.sqlUpdate("delete from ligue where id = " + ligue.getNum());		
		ligues.remove(ligue);
	}
	
	public void addClub(Club club) throws SQLException
	{
		int id = connexion.sqlInsert("insert into club(nom, description, ligue_id) values ('" +
				club.getNom() + "', '" + club.getDescription() + "', '" + club.getNumLigue() + "')");
		club.setNum(id);
	}
	
	public void updateClub(Club club) throws SQLException
	{
		connexion.sqlUpdate("update club set nom = '" + club.getNom() + "' " +
										",description = '" + club.getDescription() + "' " +
				"where id = " + club.getNum());
	}
	
	public void deleteClub(Club club) throws SQLException
	{
		club.setLigue(null);
		connexion.sqlUpdate("delete from club where id = " + club.getNum());		
	}
	//TODO J'en étais a la de mes édit et j'y suis toujours
	public void addTournoi(Tournoi tournoi) throws SQLException
	{
		int id = connexion.sqlInsert("insert into tournoi(nom, description, categorie_id, ligue_id) values ('" +
				tournoi.getNom() + "', '" + tournoi.getDescription() + "', '"/* + tournoi.getNumCategorie()*/ + "', '" + tournoi.getNumLigue() + "')");
		tournoi.setNum(id);
	}
	
	public void updateTournoi(Tournoi tournoi) throws SQLException
	{
		connexion.sqlUpdate("update tournoi set nom = '" + tournoi.getNom() + "',description = '" + tournoi.getDescription() + "' " +
				"where id = " + tournoi.getNum());
	}
	
	public void deleteTournoi(Tournoi tournoi) throws SQLException
	{
		tournoi.setLigue(null);
		connexion.sqlUpdate("delete from tournoi where id = " + tournoi.getNum());		
	}
	
	public void addEtapeTournoi(EtapeTournoi etapeTournoi) throws SQLException
	{
		int id = connexion.sqlInsert("insert into etapeTournoi(tournoi_id) values ('" +
				etapeTournoi.getNumTournoi() + "')");
		etapeTournoi.setNum(id);
	}
	
	public void updateEtapeTournoi(EtapeTournoi etapeTournoi) throws SQLException
	{
		connexion.sqlUpdate("update etapeTournoi set tournoi_id = '" + etapeTournoi.getNumTournoi() + "' " +
				"where id = " + etapeTournoi.getNum());
	}
	
	public void deleteEtapeTournoi(EtapeTournoi etapeTournoi) throws SQLException
	{
		etapeTournoi.setTournoi(null);
		connexion.sqlUpdate("delete from etapeTournoi where id = " + etapeTournoi.getNum());		
	}
	
	public void addCategorie(Categorie categorie) throws SQLException
	{
		int id = connexion.sqlInsert("insert into etapeTournoi(tournoi_id) values ('" +
				categorie.getNumLigue() + "')");
		categorie.setNum(id);
	}
	
	public void updateCategorie(Categorie categorie) throws SQLException
	{
		connexion.sqlUpdate("update etapeTournoi set tournoi_id = '" + categorie.getNumLigue() + "' " +
				"where id = " + categorie.getNum());
	}
	
	public void deleteCategorie(Categorie categorie) throws SQLException
	{
		categorie.setLigue(null);
		connexion.sqlUpdate("delete from etapeTournoi where id = " + categorie.getNum());		
	}
	
	public void resetDB() throws SQLException
	{
		connexion.sqlBatch(RESET_SCRIPT);
		connexion.sqlBatch(INIT_SCRIPT);
	}
	
	public void initDB() 
	{
		try
		{
			connexion.sqlBatch(INIT_SCRIPT);
		}
		catch(SQLException e)
		{
			System.out.println("La base de données es déjà  créée.");
		}
	}
		
	public RootSuperAdmin() throws ClassNotFoundException, SQLException
	{
		/*connexion = Connexion.makeConnexion();*/
		//initDB();
		connexion = new Connexion();
		ligues = null;
	}
		
	void loadClub(Ligue ligue) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from club " +
				"where id = " + ligue.getNum() + 
				" order by id");
		while(rs.next())
		{
			new Club(
					rs.getInt("id"), 
					rs.getString("nom"), 
					ligue);
		}
	}
	
	void loadTournoi(Ligue ligue) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from tournoi " +
				"where id = " + ligue.getNum() + 
				" order by id");
		while(rs.next())
		{
			new Tournoi(
					rs.getInt("id"), 
					rs.getString("nom"), 
					rs.getString("description"),
					ligue,
					loadCategorie(rs.getInt("categorie_id")));
		}
	}
	
	void loadTournoi(Categorie categorie) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from tournoi " +
				"where id = " + categorie.getNum() + 
				" order by id");
		while(rs.next())
		{
			new Tournoi(
					rs.getInt("id"), 
					rs.getString("nom"), 
					rs.getString("description"),
					loadLigue(rs.getInt("ligue_id")),
					categorie);
		}
	}
	
	void loadEquipeInscriteTournois(Equipe equipe) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from tournoi " +
				"where equipe_id = " + equipe.getNum() + 
				" order by id");
		while(rs.next())
		{
			new EquipeInscriteTournoi(
					equipe,
					loadTournoi(rs.getInt("tournoi_id")));
		}
	}
	
	void loadEquipeInscriteTournois(Tournoi tournoi) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from tournoi " +
				"where tournoi_id = " + tournoi.getNum() + 
				" order by id");
		while(rs.next())
		{
			new EquipeInscriteTournoi(
					loadEquipe(rs.getInt("equipe_id")),
					tournoi);
		}
	}
	
	void loadEtapeTournoi(Tournoi tournoi) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from etapeTournoi " +
				"where id = " + tournoi.getNum() +
				"order by id");
		while(rs.next())
		{
			new EtapeTournoi(
					rs.getInt("id"),
					tournoi);
		}
	}
	
	void loadRencontre(EtapeTournoi etapeTournoi) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from rencontre " +
				"where id = " + etapeTournoi.getNum() +
				"order by id");
		while(rs.next())
		{
			new Rencontre(
					rs.getInt("id"),
					rs.getString("lieu"),
					rs.getString("date"),
					etapeTournoi);
		}
	}
	
	void loadCategorie(Ligue ligue) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from categorie " +
				"where id = " + ligue.getNum() + 
				" order by id");
		while(rs.next())
		{
			new Categorie(
					rs.getInt("id"), 
					rs.getString("nom"),
					ligue);
		}
	}
	
	void loadEquipe(Categorie categorie, Club club) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from equipe " +
				"where id = " + categorie.getNum() + 
				" order by id");
		while(rs.next())
		{
			new Equipe(
					rs.getInt("id"), 
					rs.getString("nom"),
					categorie,
					club);
		}
	}
	
	private void loadAllLigues() throws SQLException
	{
		if (ligues == null)
		{
			ligues = new ArrayList<Ligue>();
			try
			{
				ResultSet rs = connexion.sqlSelect("select * from ligue order by id");
				while(rs.next())
				{
					Ligue ligue = new Ligue(
							this,
							rs.getInt("num"), 
							rs.getString("nom"));
					ligues.add(ligue);
				}
			}
			catch(SQLException e)
			{
				ligues = null;
				throw e;
			}
		}
	}
	
	public int getNbLigues() throws SQLException
	{
		loadAllLigues();
		return ligues.size();		
	}
	
	public Ligue getLigueByIndex(int index) throws SQLException
	{
		loadAllLigues();
		return ligues.get(index);
	}
	
	public void addType(Type type) throws SQLException
	{
		int id = connexion.sqlInsert("insert into type(nom) values ('" +
				type.getNom() + "')");
		type.setNum(id);
		types.add(type);
	}
	
	public void updateType(Type type) throws SQLException
	{
		connexion.sqlUpdate("update type set nom = '" + type.getNom() +
				"where id = " + type.getNum());
	}
	
	public void deleteType(Type type) throws SQLException
	{
		while(type.getNbUtilisateurs() != 0)
		{
			Utilisateur utilisateur = type.getUtilisateur(0);
			deleteUtilisateur(utilisateur);
		}
		connexion.sqlUpdate("delete from type where id = " + type.getNum());		
		types.remove(type);
	}
	
	public void addUtilisateur(Utilisateur utilisateur) throws SQLException
	{
		int id = connexion.sqlInsert("insert into utilisateur(nom, type_id) values ('" +
				utilisateur.getNom() + "', '" + utilisateur.getNumType() + "')");
		utilisateur.setNum(id);
	}
	
	public void updateUtilisateur(Utilisateur utilisateur) throws SQLException
	{
		connexion.sqlUpdate("update utilisateur set nom = '" + utilisateur.getNom() + "' " +
				"where id = " + utilisateur.getNum());
	}
	
	public void deleteUtilisateur(Utilisateur utilisateur) throws SQLException
	{
		utilisateur.setType(null);
		connexion.sqlUpdate("delete from utilisateur where id = " + utilisateur.getNum());		
	}

	public Type loadType(int idType) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from type " +
				"where id = " + idType);
		Type type = new Type(
				this,
				rs.getInt("id"), 
				rs.getString("nom"),
				rs.getString("description"));
		return type;
	}
	
	public Utilisateur loadUtilisateur(int idUtilisateur) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from utilisateur " +
				"where id = " + idUtilisateur);
		Utilisateur utilisateur = new Utilisateur(
				rs.getInt("id"), 
				rs.getString("nom"),
				rs.getString("prenom"),
				rs.getInt("telFixe"),
				rs.getInt("telPortable"),
				rs.getString("mail"),
				rs.getString("mdp"),
				loadType(rs.getInt("type_id")),
				loadClub(rs.getInt("club_id")));
		return utilisateur;
	}
	
	
	public Ligue loadLigue(int idLigue) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from ligue " +
				"where id = " + idLigue);
		Ligue ligue = new Ligue(
				this,
				rs.getInt("id"), 
				rs.getString("nom"));
		return ligue;
	}
	
	public Categorie loadCategorie(int idCategorie) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from categorie " +
				"where id = " + idCategorie);
		Categorie categorie = new Categorie(
				rs.getInt("id"), 
				rs.getString("nom"),
				loadLigue(rs.getInt("ligue_id")));
		return categorie;
	}
	
	public Club loadClub(int idClub) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from club " +
				"where id = " + idClub + 
				" order by id");
		Club club = new Club(
				rs.getInt("id"), 
				rs.getString("nom"), 
				loadLigue(rs.getInt("ligue_id")));
		return club;
	}
	
	void loadEquipes(Club club) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from utilisateur " +
				"where categorie_id = " + club.getNum() +
				"order by id");
		while(rs.next())
		{
			new Equipe(
					rs.getInt("id"),
					rs.getString("nom"),
					loadCategorie(rs.getInt("categorie_id")),
					club);
		}
	}

	public Equipe loadEquipe(int idEquipe) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from equipe " +
				"where id = " + idEquipe + 
				" order by id");
		Equipe equipe = new Equipe(
				rs.getInt("id"), 
				rs.getString("nom"), 
				loadCategorie(rs.getInt("categorie_id")),
				loadClub(rs.getInt("club_id")));
		return equipe;
	}
	
	void loadEquipes(Categorie categorie) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from utilisateur " +
				"where categorie_id = " + categorie.getNum() +
				"order by id");
		while(rs.next())
		{
			new Equipe(
					rs.getInt("id"),
					rs.getString("nom"),
					categorie,
					loadClub(rs.getInt("club_id")));
		}
	}
	
	public Tournoi loadTournoi(int idTournoi) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from tournoi " +
				"where id = " + idTournoi);
		Tournoi tournoi = new Tournoi(
				rs.getInt("id"), 
				rs.getString("nom"),
				rs.getString("description"),
				loadLigue(rs.getInt("ligue_id")),
				loadCategorie(rs.getInt("categorie_id")));
		return tournoi;
	}
	
	public EtapeTournoi loadEtapeTournoi(int idEtapeTournoi) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from etape_tournoi " +
				"where id = " + idEtapeTournoi);
		EtapeTournoi etapeTournoi = new EtapeTournoi(
				rs.getInt("id"), 
				rs.getInt("type_etape"),
				loadTournoi(rs.getInt("tournoi_id")));
		return etapeTournoi;
	}
	
	public Rencontre loadRencontre(int idRencontre) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from rencontre" +
				"where id = " + idRencontre);
		Rencontre rencontre = new Rencontre(
				rs.getInt("id"), 
				rs.getString("lieu"),
				rs.getString("date"),
				loadEtapeTournoi(rs.getInt("etape_tournoi_id")));
		return rencontre;
	}
	
	void loadUtilisateurs(Type type) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from utilisateur " +
				"where type_id = " + type.getNum() +
				"order by id");
		while(rs.next())
		{
			new Utilisateur(
					rs.getInt("id"),
					rs.getString("nom"),
					rs.getString("prenom"),
					rs.getInt("tel_fixe"),
					rs.getInt("tel_portable"),
					rs.getString("mail"),
					rs.getString("mdp"),
					type,
					loadClub(rs.getInt("club_id")));
		}
	}
	
	void loadUtilisateurs(Club club) throws SQLException
	{
		ResultSet rs = connexion.sqlSelect("select * " +
				"from utilisateur " +
				"where club_id = " + club.getNum() +
				"order by id");
		while(rs.next())
		{
			new Utilisateur(
					rs.getInt("id"),
					rs.getString("nom"),
					rs.getString("prenom"),
					rs.getInt("tel_fixe"),
					rs.getInt("tel_portable"),
					rs.getString("mail"),
					rs.getString("mdp"),
					loadType(rs.getInt("type_id")),
					club);
		}
	}
	
	private void loadAllTypes() throws SQLException
	{
		if (types == null)
		{
			types = new ArrayList<Type>();
			try
			{
				ResultSet rs = connexion.sqlSelect("select * from type order by id");
				while(rs.next())
				{
					Type type = new Type(
							this,
							rs.getInt("num"), 
							rs.getString("nom"),
							rs.getString("description"));
					types.add(type);
				}
			}
			catch(SQLException e)
			{
				ligues = null;
				throw e;
			}
		}
	}
	
	public int getNbTypes() throws SQLException
	{
		loadAllTypes();
		return types.size();		
	}
	
	public Type getTypeByIndex(int index) throws SQLException
	{
		loadAllTypes();
		return types.get(index);
	}

	public void save(Type type) throws DataAccessException
	{
		if (type.getNum() == mapper.NO_KEY)
		{
			mapper.save(type);
			types.add(type);
			typesByNum.put(type.getNum(), type);
		}
		else
			mapper.save(type);
	}
}