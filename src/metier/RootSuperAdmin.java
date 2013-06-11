package metier;

import java.sql.*;
import java.util.*;

import metier.DataAccessException;
import db.Connexion;

// TODO Ecrire les commentaires pour la documentation
// TODO Ecrire des test unitaires
// TODO permettre de gérer des listes de contacts,  créer une classe inscription de visibilité package 

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
	private ArrayList<Utilisateur> utilisateurs = null;
	private HashMap<Integer, Utilisateur> utilisateursByNum = new HashMap<Integer, Utilisateur> ();
	
	private Boolean allLiguesLoaded = false;
	private Boolean allTypesLoaded = false;
	
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
			System.out.println("La base de données es déjà  créée.");
		}
	}
		
	public RootSuperAdmin() throws ClassNotFoundException, DataAccessException
	{
		/*connexion = Connexion.makeConnexion();*/
		//initDB();
		//connexion = new Connexion();
		//ligues = null;
		mapper = new SQLMapper(this);
	}
	
	public RootSuperAdmin(Mapper mapper) throws ClassNotFoundException, DataAccessException
	{
		this.mapper = mapper;
	}
	
	
		
	void loadClubs(Ligue ligue) throws DataAccessException
	{
		mapper.loadClubs(ligue);
	}
	
	void loadTournois(Ligue ligue) throws DataAccessException
	{
		mapper.loadTournois(ligue);
	}
	
	void loadTournois(Categorie categorie) throws DataAccessException
	{
		mapper.loadTournois(categorie);
	}
	
	void loadEquipeInscriteTournois(Equipe equipe) throws DataAccessException
	{
		mapper.loadEquipeInscriteTournois(equipe);
	}
	
	void loadEquipeInscriteTournois(Tournoi tournoi) throws DataAccessException
	{
		mapper.loadEquipeInscriteTournois(tournoi);
	}
	
	void loadEtapesTournoi(Tournoi tournoi) throws DataAccessException
	{
		mapper.loadEtapesTournoi(tournoi);
	}
	
	void loadRencontres(EtapeTournoi etapeTournoi) throws DataAccessException
	{
		mapper.loadRencontres(etapeTournoi);
	}
	
	void loadCategorie(Ligue ligue) throws DataAccessException
	{
		mapper.loadCategories(ligue);
	}
	
	void loadEquipes(Club club) throws DataAccessException
	{
		mapper.loadEquipes(club);
	}

	void loadEquipes(Categorie categorie) throws DataAccessException
	{ 
		mapper.loadEquipes(categorie);
	}

	void loadUtilisateurs(Type type) throws DataAccessException
	{
		mapper.loadUtilisateurs(type);
		
	}

	void loadUtilisateurs(Club club) throws DataAccessException
	{
		mapper.loadUtilisateurs(club);
		
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
	
	void loadAllLigues() throws DataAccessException
	{
		if (!allLiguesLoaded)
		{
			ArrayList<Ligue> liguesLoaded = mapper.loadAllLigues();
			for(Ligue ligue : liguesLoaded)
			{
				int numLigue = ligue.getNum();
				if (liguesByNum.get(numLigue) == null)
				{
					ligues.add(ligue);
					liguesByNum.put(numLigue, ligue);
				}
			}
			allLiguesLoaded = true;
		}
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

	void loadAllTypes() throws DataAccessException
	{
		if (!allTypesLoaded)
		{
			ArrayList<Type> typesLoaded = mapper.loadAllTypes();
			for(Type type : typesLoaded)
			{
				int numType = type.getNum();
				if (typesByNum.get(numType) == null)
				{
					types.add(type);
					typesByNum.put(numType, type);
				}
			}
			allTypesLoaded = true;
		}
	}

	public Type loadType(int idType) throws DataAccessException
	{
		Type type = typesByNum.get(idType);
		if (type == null)
		{
			type = mapper.loadType(idType);
			types.add(type);
			typesByNum.put(idType, type);
		}
		return type;
	}
	
	public Utilisateur loadUtilisateur(int idUtilisateur) throws DataAccessException
	{
		Utilisateur utilisateur = utilisateursByNum.get(idUtilisateur);
		if (utilisateur == null)
		{
			utilisateur = mapper.loadUtilisateur(idUtilisateur);
			utilisateurs.add(utilisateur);
			utilisateursByNum.put(idUtilisateur, utilisateur);
		}
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
	
	public void delete(Type type) throws DataAccessException
	{
		mapper.delete(type);		
	}

	public void save(Ligue ligue) throws DataAccessException
	{
		if (ligue.getNum() == mapper.NO_KEY)
		{
			mapper.save(ligue);
			ligues.add(ligue);
			liguesByNum.put(ligue.getNum(), ligue);
		}
		else
			mapper.save(ligue);
	}
	
	public void save(Club club) throws DataAccessException
	{
		if (club.getNum() == mapper.NO_KEY)
		{
			mapper.save(club);
			clubs.add(club);
			clubsByNum.put(club.getNum(), club);
		}
		else
			mapper.save(club);
	}
	
	public void save(Utilisateur utilisateur) throws DataAccessException
	{
		if (utilisateur.getNum() == mapper.NO_KEY)
		{
			mapper.save(utilisateur);
			utilisateurs.add(utilisateur);
			utilisateursByNum.put(utilisateur.getNum(), utilisateur);
		}
		else
			mapper.save(utilisateur);
	}

	@Override
	void loadCategories(Ligue ligue) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

}