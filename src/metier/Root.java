package metier;

import java.sql.*;
import java.util.*;

import poubelle.EtapeTournoi;

import metier.DataAccessException;
import db.Connexion;

// TODO Ecrire les commentaires pour la documentation
// TODO Ecrire des test unitaires
// TODO permettre de gérer des listes de contacts,  créer une classe inscription de visibilité package 

public class Root
{
	//private view.Console console;
	private Mapper mapper;
	
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
	
	private ArrayList<Ligue> ligues = new ArrayList<Ligue>();
	private HashMap<Integer, Ligue> liguesByNum = new HashMap<Integer, Ligue> ();
	ArrayList<Type> types = new ArrayList<Type>();
	private HashMap<Integer, Type> typesByNum = new HashMap<Integer, Type> ();
	private ArrayList<Club> clubs = new ArrayList<Club>();
	private HashMap<Integer, Club> clubsByNum = new HashMap<Integer, Club> ();
	private ArrayList<Rencontre> rencontres = new ArrayList<Rencontre>();
	private HashMap<Integer, Rencontre> rencontresByNum = new HashMap<Integer, Rencontre> ();
	private ArrayList<Etape> etapes = new ArrayList<Etape>();
	private HashMap<Integer, Etape> etapesByNum = new HashMap<Integer, Etape> ();
	private ArrayList<Tournoi> tournois = new ArrayList<Tournoi>();
	private HashMap<Integer, Tournoi> tournoisByNum = new HashMap<Integer, Tournoi> ();
	private ArrayList<Categorie> categories = new ArrayList<Categorie>();
	private HashMap<Integer, Categorie> categoriesByNum = new HashMap<Integer, Categorie> ();
	private ArrayList<Equipe> equipes = new ArrayList<Equipe>();
	private HashMap<Integer, Equipe> equipesByNum = new HashMap<Integer, Equipe> ();
	private ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
	private HashMap<Integer, Utilisateur> utilisateursByNum = new HashMap<Integer, Utilisateur> ();
	private ArrayList<Inscription> inscriptions = new ArrayList<Inscription>();
	private HashMap<Integer, Inscription> inscriptionsByNum = new HashMap<Integer, Inscription> ();
	private ArrayList<Appartenance> appartenances = new ArrayList<Appartenance>();
	private HashMap<Integer, Appartenance> appartenancesByNum = new HashMap<Integer, Appartenance> ();
	private ArrayList<Score> scores = new ArrayList<Score>();
	private HashMap<Integer, Score> scoresByNum = new HashMap<Integer, Score> ();
	
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
		 
	public Root() throws ClassNotFoundException, DataAccessException
	{
		/*connexion = Connexion.makeConnexion();*/
		//initDB();
		//connexion = new Connexion();
		//ligues = null;
		mapper = new SQLMapper(this);
	}
	
	public Root(Mapper mapper) throws ClassNotFoundException, DataAccessException
	{
		this.mapper = mapper;
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

	void loadAllClubs(Ligue ligue) throws DataAccessException
	{
		ArrayList<Club> clubsLoaded = mapper.loadAllClubs(ligue);
		for(Club club : clubsLoaded)
		{
			int numClub = club.getNum();
			if (clubsByNum.get(numClub) == null)
			{
				clubs.add(club);
				clubsByNum.put(numClub, club);
			}
		}
	}
	
	void loadAllUtilisateurs(Club club) throws DataAccessException
	{
		ArrayList<Utilisateur> utilisateursLoaded = mapper.loadAllUtilisateurs(club);
		for(Utilisateur utilisateur : utilisateursLoaded)
		{
			int numUtilisateur = utilisateur.getNum();
			if (utilisateursByNum.get(numUtilisateur) == null)
			{
				utilisateurs.add(utilisateur);
				utilisateursByNum.put(numUtilisateur, utilisateur);
			}
		}
	}
	
	void loadAllUtilisateurs(Type type) throws DataAccessException
	{
		ArrayList<Utilisateur> utilisateursLoaded = mapper.loadAllUtilisateurs(type);
		for(Utilisateur utilisateur : utilisateursLoaded)
		{
			int numUtilisateur = utilisateur.getNum();
			if (utilisateursByNum.get(numUtilisateur) == null)
			{
				utilisateurs.add(utilisateur);
				utilisateursByNum.put(numUtilisateur, utilisateur);
			}
		}
	}
	
	void loadAllTournois(Categorie categorie) throws DataAccessException
	{
		ArrayList<Tournoi> tournoisLoaded = mapper.loadAllTournois(categorie);
		for(Tournoi tournoi : tournoisLoaded)
		{
			int numTournoi = tournoi.getNum();
			if (tournoisByNum.get(numTournoi) == null)
			{
				tournois.add(tournoi);
				tournoisByNum.put(numTournoi, tournoi);
			}
		}
	}
	
	void loadAllTournois(Ligue ligue) throws DataAccessException
	{
		ArrayList<Tournoi> tournoisLoaded = mapper.loadAllTournois(ligue);
		for(Tournoi tournoi : tournoisLoaded)
		{
			int numTournoi = tournoi.getNum();
			if (tournoisByNum.get(numTournoi) == null)
			{
				tournois.add(tournoi);
				tournoisByNum.put(numTournoi, tournoi);
			}
		}
	}
	
	void loadAllEtapes(Tournoi tournoi) throws DataAccessException
	{
		ArrayList<Etape> etapesLoaded = mapper.loadAllEtapes(tournoi);
		for(Etape etape : etapesLoaded)
		{
			int numEtape = etape.getNum();
			if (etapesByNum.get(numEtape) == null)
			{
				etapes.add(etape);
				etapesByNum.put(numEtape, etape);
			}
		}
	}
	
	void loadAllEquipes(Club club) throws DataAccessException
	{
		ArrayList<Equipe> equipesLoaded = mapper.loadAllEquipes(club);
		for(Equipe equipe : equipesLoaded)
		{
			int numEquipe = equipe.getNum();
			if (equipesByNum.get(numEquipe) == null)
			{
				equipes.add(equipe);
				equipesByNum.put(numEquipe, equipe);
			}
		}
	}
	
	void loadAllEquipes(Categorie categorie) throws DataAccessException
	{
		ArrayList<Equipe> equipesLoaded = mapper.loadAllEquipes(categorie);
		for(Equipe equipe : equipesLoaded)
		{
			int numEquipe = equipe.getNum();
			if (equipesByNum.get(numEquipe) == null)
			{
				equipes.add(equipe);
				equipesByNum.put(numEquipe, equipe);
			}
		}
	}
	
	
	void loadAllInscriptions(Equipe equipe) throws DataAccessException
	{
		ArrayList<Inscription> inscriptionsLoaded = mapper.loadAllInscriptions(equipe);
		for(Inscription inscription : inscriptionsLoaded)
		{
			int numInscription = inscription.getNum();
			if (inscriptionsByNum.get(numInscription) == null)
			{
				inscriptions.add(inscription);
				inscriptionsByNum.put(numInscription, inscription);
			}
		}
	}
	
	void loadAllInscriptions(Tournoi tournoi) throws DataAccessException
	{
		ArrayList<Inscription> inscriptionsLoaded = mapper.loadAllInscriptions(tournoi);
		for(Inscription inscription : inscriptionsLoaded)
		{
			int numInscription = inscription.getNum();
			if (inscriptionsByNum.get(numInscription) == null)
			{
				inscriptions.add(inscription);
				inscriptionsByNum.put(numInscription, inscription);
			}
		}
	}
	
	void loadAllAppartenances(Equipe equipe) throws DataAccessException
	{
		ArrayList<Appartenance> appartenancesLoaded = mapper.loadAllAppartenances(equipe);
		for(Appartenance appartenance : appartenancesLoaded)
		{
			int numAppartenance = appartenance.getNum();
			if (appartenancesByNum.get(numAppartenance) == null)
			{
				appartenances.add(appartenance);
				appartenancesByNum.put(numAppartenance, appartenance);
			}
		}
	}
	
	void loadAllAppartenances(Utilisateur utilisateur) throws DataAccessException
	{
		ArrayList<Appartenance> appartenancesLoaded = mapper.loadAllAppartenances(utilisateur);
		for(Appartenance appartenance : appartenancesLoaded)
		{
			int numAppartenance = appartenance.getNum();
			if (appartenancesByNum.get(numAppartenance) == null)
			{
				appartenances.add(appartenance);
				appartenancesByNum.put(numAppartenance, appartenance);
			}
		}
	}
	
	void loadAllScores(Equipe equipe) throws DataAccessException
	{
		ArrayList<Score> scoresLoaded = mapper.loadAllScores(equipe);
		for(Score score : scoresLoaded)
		{
			int numScore = score.getNum();
			if (scoresByNum.get(numScore) == null)
			{
				scores.add(score);
				scoresByNum.put(numScore, score);
			}
		}
	}
	
	void loadAllScores(Rencontre rencontre) throws DataAccessException
	{
		ArrayList<Score> scoresLoaded = mapper.loadAllScores(rencontre);
		for(Score score : scoresLoaded)
		{
			int numScore = score.getNum();
			if (scoresByNum.get(numScore) == null)
			{
				scores.add(score);
				scoresByNum.put(numScore, score);
			}
		}
	}
	
	void loadAllRencontres(Etape etape) throws DataAccessException
	{
		ArrayList<Rencontre> rencontresLoaded = mapper.loadAllRencontres(etape);
		for(Rencontre rencontre : rencontresLoaded)
		{
			int numRencontre = rencontre.getNum();
			if (rencontresByNum.get(numRencontre) == null)
			{
				rencontres.add(rencontre);
				rencontresByNum.put(numRencontre, rencontre);
			}
		}
	}
	
	void loadAllCategories(Ligue ligue) throws DataAccessException
	{
		ArrayList<Categorie> categoriesLoaded = mapper.loadAllCategories(ligue);
		for(Categorie categorie : categoriesLoaded)
		{
			int numCategorie = categorie.getNum();
			if (categoriesByNum.get(numCategorie) == null)
			{
				categories.add(categorie);
				categoriesByNum.put(numCategorie, categorie);
			}
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
	
	public Etape loadEtape(int idEtape) throws DataAccessException
	{
		Etape etape = etapesByNum.get(idEtape);
		if (etape == null)
		{
			etape = mapper.loadEtape(idEtape);
			etapes.add(etape);
			etapesByNum.put(idEtape, etape);
		}
		return etape;
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
	
	public void save(Tournoi tournoi) throws DataAccessException
	{
		if (tournoi.getNum() == mapper.NO_KEY)
		{
			mapper.save(tournoi);
			tournois.add(tournoi);
			tournoisByNum.put(tournoi.getNum(), tournoi);
		}
		else
			mapper.save(tournoi);
	}
	
	public void save(Etape etape) throws DataAccessException
	{
		if (etape.getNum() == mapper.NO_KEY)
		{
			mapper.save(etape);
			etapes.add(etape);
			etapesByNum.put(etape.getNum(), etape);
		}
		else
			mapper.save(etape);
	}
	
	public void save(Rencontre rencontre) throws DataAccessException
	{
		if (rencontre.getNum() == mapper.NO_KEY)
		{
			mapper.save(rencontre);
			rencontres.add(rencontre);
			rencontresByNum.put(rencontre.getNum(), rencontre);
		}
		else
			mapper.save(rencontre);
	}
	
	public void save(Categorie categorie) throws DataAccessException
	{
		if (categorie.getNum() == mapper.NO_KEY)
		{
			mapper.save(categorie);
			categories.add(categorie);
			categoriesByNum.put(categorie.getNum(), categorie);
		}
		else
			mapper.save(categorie);
	}
	
	public void save(Equipe equipe) throws DataAccessException
	{
		if (equipe.getNum() == mapper.NO_KEY)
		{
			mapper.save(equipe);
			equipes.add(equipe);
			equipesByNum.put(equipe.getNum(), equipe);
		}
		else
			mapper.save(equipe);
	}
	
	public void save(Score score) throws DataAccessException
	{
		if (score.getNum() == mapper.NO_KEY)
		{
			mapper.save(score);
			scores.add(score);
			scoresByNum.put(score.getNum(), score);
		}
		else
			mapper.save(score);
	}
	
	public void save(Appartenance appartenance) throws DataAccessException
	{
		if (appartenance.getNum() == mapper.NO_KEY)
		{
			mapper.save(appartenance);
			appartenances.add(appartenance);
			appartenancesByNum.put(appartenance.getNum(), appartenance);
		}
		else
			mapper.save(appartenance);
	}

	public void save(Inscription inscription) throws DataAccessException
	{
		if (inscription.getNum() == mapper.NO_KEY)
		{
			mapper.save(inscription);
			inscriptions.add(inscription);
			inscriptionsByNum.put(inscription.getNum(), inscription);
		}
		else
			mapper.save(inscription);
	}
	
}