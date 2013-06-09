package metier;

import java.sql.*;
import java.util.*;

import metier.DataAccessException;
import db.Connexion;

// TODO Ecrire les commentaires pour la documentation
// TODO Ecrire des test unitaires
// TODO permettre de g�rer des listes de contacts,  cr�er une classe inscription de visibilit� package 

public class RootSuperAdmin extends AbstractRoot
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
	private HashMap<Integer, Ligue> liguesByNum = new HashMap<Integer, Ligue> ();
	private ArrayList<Type> types = null;
	private HashMap<Integer, Type> typesByNum = new HashMap<Integer, Type> ();
	private ArrayList<Club> clubs = null;
	private HashMap<Integer, Club> clubsByNum = new HashMap<Integer, Club> ();
	private ArrayList<Rencontre> rencontres = null;
	private HashMap<Integer, Rencontre> rencontresByNum = new HashMap<Integer, Rencontre> ();
	private ArrayList<EtapeTournoi> etapesTournoi = null;
	private HashMap<Integer, EtapeTournoi> etapesTournoiByNum = new HashMap<Integer, EtapeTournoi> ();
	private ArrayList<Tournoi> tournois = null;
	private HashMap<Integer, Tournoi> tournoisByNum = new HashMap<Integer, Tournoi> ();
	private ArrayList<Categorie> categories = null;
	private HashMap<Integer, Categorie> categoriesByNum = new HashMap<Integer, Categorie> ();
	private ArrayList<Equipe> equipes = null;
	private HashMap<Integer, Equipe> equipesByNum = new HashMap<Integer, Equipe> ();
	
	
	public void addLigue(Ligue ligue) throws DataAccessException
	{
		int id = connexion.sqlInsert("insert into ligue(nom, description) values ('" +
				ligue.getNom() + "', '" + ligue.getDescription() + "')");
		ligue.setNum(id);
		ligues.add(ligue);
	}
	
	public void updateLigue(Ligue ligue) throws DataAccessException
	{
		connexion.sqlUpdate("update ligue set nom = '" + ligue.getNom() + "' " +
											"description = '" + ligue.getDescription() + "' " +
				"where numContact = " + ligue.getNum());
	}
	
	public void deleteLigue(Ligue ligue) throws DataAccessException
	{
		while(ligue.getNbClubs() != 0)
		{
			Club club = ligue.getClub(0);
			deleteClub(club);
		}
		connexion.sqlUpdate("delete from ligue where id = " + ligue.getNum());		
		ligues.remove(ligue);
	}
	
	public void addClub(Club club) throws DataAccessException
	{
		int id = connexion.sqlInsert("insert into club(nom, description, ligue_id) values ('" +
				club.getNom() + "', '" + club.getDescription() + "', '" + club.getNumLigue() + "')");
		club.setNum(id);
	}
	
	public void updateClub(Club club) throws DataAccessException
	{
		connexion.sqlUpdate("update club set nom = '" + club.getNom() + "' " +
										",description = '" + club.getDescription() + "' " +
				"where id = " + club.getNum());
	}
	
	public void deleteClub(Club club) throws DataAccessException
	{
		club.setLigue(null);
		connexion.sqlUpdate("delete from club where id = " + club.getNum());		
	}
	//TODO J'en �tais a la de mes �dit et j'y suis toujours
	public void addTournoi(Tournoi tournoi) throws DataAccessException
	{
		int id = connexion.sqlInsert("insert into tournoi(nom, description, categorie_id, ligue_id) values ('" +
				tournoi.getNom() + "', '" + tournoi.getDescription() + "', '"/* + tournoi.getNumCategorie()*/ + "', '" + tournoi.getNumLigue() + "')");
		tournoi.setNum(id);
	}
	
	public void updateTournoi(Tournoi tournoi) throws DataAccessException
	{
		connexion.sqlUpdate("update tournoi set nom = '" + tournoi.getNom() + "',description = '" + tournoi.getDescription() + "' " +
				"where id = " + tournoi.getNum());
	}
	
	public void deleteTournoi(Tournoi tournoi) throws DataAccessException
	{
		tournoi.setLigue(null);
		connexion.sqlUpdate("delete from tournoi where id = " + tournoi.getNum());		
	}
	
	public void addEtapeTournoi(EtapeTournoi etapeTournoi) throws DataAccessException
	{
		int id = connexion.sqlInsert("insert into etapeTournoi(tournoi_id) values ('" +
				etapeTournoi.getNumTournoi() + "')");
		etapeTournoi.setNum(id);
	}
	
	public void updateEtapeTournoi(EtapeTournoi etapeTournoi) throws DataAccessException
	{
		connexion.sqlUpdate("update etapeTournoi set tournoi_id = '" + etapeTournoi.getNumTournoi() + "' " +
				"where id = " + etapeTournoi.getNum());
	}
	
	public void deleteEtapeTournoi(EtapeTournoi etapeTournoi) throws DataAccessException
	{
		etapeTournoi.setTournoi(null);
		connexion.sqlUpdate("delete from etapeTournoi where id = " + etapeTournoi.getNum());		
	}
	
	public void addCategorie(Categorie categorie) throws DataAccessException
	{
		int id = connexion.sqlInsert("insert into etapeTournoi(tournoi_id) values ('" +
				categorie.getNumLigue() + "')");
		categorie.setNum(id);
	}
	
	public void updateCategorie(Categorie categorie) throws DataAccessException
	{
		connexion.sqlUpdate("update etapeTournoi set tournoi_id = '" + categorie.getNumLigue() + "' " +
				"where id = " + categorie.getNum());
	}
	
	public void deleteCategorie(Categorie categorie) throws DataAccessException
	{
		categorie.setLigue(null);
		connexion.sqlUpdate("delete from etapeTournoi where id = " + categorie.getNum());		
	}
	
	public void resetDB() throws DataAccessException
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
		catch(DataAccessException e)
		{
			System.out.println("La base de donn�es es d�j� cr��e.");
		}
	}
		
	public RootSuperAdmin() throws ClassNotFoundException, DataAccessException
	{
		/*connexion = Connexion.makeConnexion();*/
		//initDB();
		connexion = new Connexion();
		ligues = null;
	}
		
	void loadClub(Ligue ligue) throws DataAccessException
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
	
	void loadTournoi(Ligue ligue) throws DataAccessException
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
	
	void loadTournoi(Categorie categorie) throws DataAccessException
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
	
	void loadEquipeInscriteTournois(Equipe equipe) throws DataAccessException
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
	
	void loadEquipeInscriteTournois(Tournoi tournoi) throws DataAccessException
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
	
	void loadEtapeTournoi(Tournoi tournoi) throws DataAccessException
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
	
	void loadRencontre(EtapeTournoi etapeTournoi) throws DataAccessException
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
	
	void loadCategorie(Ligue ligue) throws DataAccessException
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
	
	void loadEquipe(Categorie categorie, Club club) throws DataAccessException
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
	
	private void loadAllLigues() throws DataAccessException
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
			catch(DataAccessException e)
			{
				ligues = null;
				throw e;
			}
		}
	}
	
	public int getNbLigues() throws DataAccessException
	{
		loadAllLigues();
		return ligues.size();		
	}
	
	public Ligue getLigueByIndex(int index) throws DataAccessException
	{
		loadAllLigues();
		return ligues.get(index);
	}
	
	public void addType(Type type) throws DataAccessException
	{
		int id = connexion.sqlInsert("insert into type(nom) values ('" +
				type.getNom() + "')");
		type.setNum(id);
		types.add(type);
	}
	
	public void updateType(Type type) throws DataAccessException
	{
		connexion.sqlUpdate("update type set nom = '" + type.getNom() +
				"where id = " + type.getNum());
	}
	
	public void deleteType(Type type) throws DataAccessException
	{
		while(type.getNbUtilisateurs() != 0)
		{
			Utilisateur utilisateur = type.getUtilisateur(0);
			deleteUtilisateur(utilisateur);
		}
		connexion.sqlUpdate("delete from type where id = " + type.getNum());		
		types.remove(type);
	}
	
	public void addUtilisateur(Utilisateur utilisateur) throws DataAccessException
	{
		int id = connexion.sqlInsert("insert into utilisateur(nom, type_id) values ('" +
				utilisateur.getNom() + "', '" + utilisateur.getNumType() + "')");
		utilisateur.setNum(id);
	}
	
	public void updateUtilisateur(Utilisateur utilisateur) throws DataAccessException
	{
		connexion.sqlUpdate("update utilisateur set nom = '" + utilisateur.getNom() + "' " +
				"where id = " + utilisateur.getNum());
	}
	
	public void deleteUtilisateur(Utilisateur utilisateur) throws DataAccessException
	{
		utilisateur.setType(null);
		connexion.sqlUpdate("delete from utilisateur where id = " + utilisateur.getNum());		
	}

	public Type loadType(int idType) throws DataAccessException
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
	
	public Utilisateur loadUtilisateur(int idUtilisateur) throws DataAccessException
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
	
	public Ligue loadLigue(int idLigue) throws DataAccessException
	{
		Ligue ligue = liguesByNum.get(idLigue);
		if (ligue == null)
		{
			ligue = mapper.loadLigue(idLigue);
			ligues.add(ligue);
			liguesByNum.put(idLigue, ligue);
		}
		return ligue;
	}
	
	public Categorie loadCategorie(int idCategorie) throws DataAccessException
	{
		Categorie categorie = categoriesByNum.get(idCategorie);
		if (categorie == null)
		{
			categorie = mapper.loadCategorie(idCategorie);
			categories.add(categorie);
			categoriesByNum.put(idCategorie, categorie);
		}
		return categorie;
	}
	
	public Club loadClub(int idClub) throws DataAccessException
	{
		Club club = clubsByNum.get(idClub);
		if (club == null)
		{
			club = mapper.loadClub(idClub);
			clubs.add(club);
			clubsByNum.put(idClub, club);
		}
		return club;
	}
	
	void loadEquipes(Club club) throws DataAccessException
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

	public Equipe loadEquipe(int idEquipe) throws DataAccessException
	{
		Equipe equipe = equipesByNum.get(idEquipe);
		if (equipe == null)
		{
			equipe = mapper.loadEquipe(idEquipe);
			equipes.add(equipe);
			equipesByNum.put(idEquipe, equipe);
		}
		return equipe;
	}
	
	void loadEquipes(Categorie categorie) throws DataAccessException
	{
		
	}
	
	public Tournoi loadTournoi(int idTournoi) throws DataAccessException
	{
		Tournoi tournoi = tournoisByNum.get(idTournoi);
		if (tournoi == null)
		{
			tournoi = mapper.loadTournoi(idTournoi);
			tournois.add(tournoi);
			tournoisByNum.put(idTournoi, tournoi);
		}
		return tournoi;
	}
	
	public EtapeTournoi loadEtapeTournoi(int idEtapeTournoi) throws DataAccessException
	{
		EtapeTournoi etapeTournoi = etapesTournoiByNum.get(idEtapeTournoi);
		if (etapeTournoi == null)
		{
			etapeTournoi = mapper.loadEtapeTournoi(idEtapeTournoi);
			etapesTournoi.add(etapeTournoi);
			etapesTournoiByNum.put(idEtapeTournoi, etapeTournoi);
		}
		return etapeTournoi;
	}
	
	public Rencontre loadRencontre(int idRencontre) throws DataAccessException
	{
		Rencontre rencontre = rencontresByNum.get(idRencontre);
		if (rencontre == null)
		{
			rencontre = mapper.loadRencontre(idRencontre);
			rencontres.add(rencontre);
			rencontresByNum.put(idRencontre, rencontre);
		}
		return rencontre;
	}
	
	void loadUtilisateurs(Type type) throws DataAccessException
	{
		mapper.loadUtilisateurs(type);
		
	}
	
	void loadUtilisateurs(Club club) throws DataAccessException
	{
		mapper.loadUtilisateurs(club);
		
	}
	
	void loadAllTypes() throws DataAccessException
	{
		if (types == null)
			types = mapper.loadAllTypes();
	}
	
	public int getNbTypes() throws DataAccessException
	{
		loadAllTypes();
		return types.size();		
	}
	
	public Type getTypeByIndex(int index) throws DataAccessException
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