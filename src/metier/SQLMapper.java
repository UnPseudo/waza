package metier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Connexion;

class SQLMapper implements Mapper {
	Root root;
	Connexion connexion;

	
	public String boolToStr(boolean value) 
	{
		if (value == true)
			return "1";
		else
			return "0";
		
	}
	
	public void save(Type type) throws DataAccessException {
			if (type.getNum() == NO_KEY) {
				int id;
				id = connexion.sqlInsert(
						"insert into type(nom, description) values (?, ?)",
						type.getNom(), type.getDescription());
				type.setNum(id);
			} else {
				connexion
						.sqlUpdate(
								"update type set nom = ?, description  = ? where id = ?",
								type.getNom(), type.getDescription(),
								"" + type.getNum());
			}
	} 
	
	public void save(Ligue ligue) throws DataAccessException 
	{
		if (ligue.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into ligue(nom, description) values (?, ?)",
					ligue.getNom(), ligue.getDescription());
			ligue.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update ligue set nom = ?, description  = ? where id = ?",
							ligue.getNom(), ligue.getDescription(),
							"" + ligue.getNum());
		}

	} 

	public void save(Club club) throws DataAccessException
	{
		save(club.getLigue());
		if (club.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into club(nom, description, ligue_id) values (?, ?, ?)",
					club.getNom(), club.getDescription(), String.valueOf(club.getNumLigue()));
			club.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update club set nom = ?, description  = ?, ligue_id = ? where id = ?",
							club.getNom(), club.getDescription(), String.valueOf(club.getNumLigue()),
							"" + club.getNum());
		}
	}
	
	public void save(Utilisateur utilisateur) throws DataAccessException
	{
		save(utilisateur.getType());
		save(utilisateur.getClub());
		if (utilisateur.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into utilisateur(nom, prenom, tel_fixe, tel_portable, mail, mdp, type_id, club_id) values (?, ?, ?, ?, ?, ?, ?, ?)",
					utilisateur.getNom(), utilisateur.getPrenom(), String.valueOf(utilisateur.getTelFixe()), 
					String.valueOf(utilisateur.getTelPortable()), utilisateur.getMail(), utilisateur.getMdp(), 
					String.valueOf(utilisateur.getNumType()), String.valueOf(utilisateur.getNumClub()));
			utilisateur.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update utilisateur set nom = ?, prenom  = ?, tel_fixe  = ?, tel_portable  = ?, mail  = ?, mdp  = ?, type_id  = ?, club_id  = ? where id = ?",
							utilisateur.getNom(), utilisateur.getPrenom(), String.valueOf(utilisateur.getTelFixe()), 
							String.valueOf(utilisateur.getTelPortable()), utilisateur.getMail(), utilisateur.getMdp(), 
							String.valueOf(utilisateur.getNumType()), String.valueOf(utilisateur.getNumClub()),
							"" + utilisateur.getNum());
		}
	}
	
	public void save(Categorie categorie) throws DataAccessException
	{
		save(categorie.getLigue());
		if (categorie.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into categorie(nom, ligue_id) values (?, ?)",
					categorie.getNom(), String.valueOf(categorie.getNumLigue()));
			categorie.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update categorie set nom = ?, ligue_id = ? where id = ?",
							categorie.getNom(), String.valueOf(categorie.getNumLigue()), 
							"" + categorie.getNum());
		}
	}
	
	public void save(Tournoi tournoi) throws DataAccessException
	{
		save(tournoi.getLigue());
		save(tournoi.getCategorie());
		if (tournoi.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into tournoi(nom, description, ligue_id, categorie_id) values (?, ?, ?, ?)",
					tournoi.getNom(), tournoi.getDescription(), String.valueOf(tournoi.getNumLigue()), String.valueOf(tournoi.getNumCategorie()));
			tournoi.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update tournoi set nom = ?, description  = ?, ligue_id = ?, categorie_id= ? where id = ?",
							tournoi.getNom(), tournoi.getDescription(), String.valueOf(tournoi.getNumLigue()), String.valueOf(tournoi.getNumCategorie()),
							"" + tournoi.getNum());
		}
	}
	
	public void save(Etape etape) throws DataAccessException
	{
		save(etape.getTournoi());
		if (etape.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into etape (tournoi_id) values (?)",
					String.valueOf(etape.getNumTournoi()));
			etape.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update etape set tournoi_id = ? where id = ?",
							String.valueOf(etape.getNumTournoi()),
							"" + etape.getNum());
		}
	}
	
	public void save(Rencontre rencontre) throws DataAccessException
	{
		save(rencontre.getEtape());
		if (rencontre.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into rencontre(lieu, date, etape_id) values (?, ?, ?)",
					rencontre.getLieu(), rencontre.getDate(), String.valueOf(rencontre.getNumEtape()));
			rencontre.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update rencontre set lieu = ?, date = ?, etape_id = ? where id = ?",
							rencontre.getLieu(), rencontre.getDate(), String.valueOf(rencontre.getNumEtape()),
							"" + rencontre.getNum());
		}
	}
	
	public void save(Equipe equipe) throws DataAccessException
	{
		save(equipe.getClub());
		save(equipe.getCategorie());
		if (equipe.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into equipe(nom, categorie_id, club_id) values (?, ?, ?)",
					equipe.getNom(), String.valueOf(equipe.getNumCategorie()), String.valueOf(equipe.getNumClub()));
			equipe.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update equipe set nom = ?, categorie_id = ?, club_id = ? where id = ?",
							equipe.getNom(), String.valueOf(equipe.getNumCategorie()), String.valueOf(equipe.getNumClub()),
							"" + equipe.getNum());
		}
	}
	
	public void save(Score score) throws DataAccessException
	{
		save(score.getEquipe());
		save(score.getRencontre());
		if (score.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into score(rencontre_id, equipe_id, points, publie) values (?, ?, ?, ?)",
					String.valueOf(score.getNumRencontre()), String.valueOf(score.getNumEquipe()), String.valueOf(score.getPoints()), boolToStr(score.getPublie()));
			score.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update score set points = ?, publie = ? where id = ?",
							String.valueOf(score.getPoints()), boolToStr(score.getPublie()),
							"" + score.getNum());
		}
	}
	
	public void save(Appartenance appartenance) throws DataAccessException
	{
		save(appartenance.getEquipe());
		save(appartenance.getUtilisateur());
		if (appartenance.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into appartenance(utilisateur_id, equipe_id) values (?, ?)",
					String.valueOf(appartenance.getNumUtilisateur()), String.valueOf(appartenance.getNumEquipe()));
			appartenance.setNum(id);
		} 
	}
	
	public void save(Inscription inscription) throws DataAccessException
	{
		save(inscription.getEquipe());
		save(inscription.getTournoi());
		if (inscription.getNum() == NO_KEY) {
			int id;
			id = connexion.sqlInsert(
					"insert into inscription(equipe_id, tournoi_id) values (?, ?)",
					String.valueOf(inscription.getNumEquipe()), String.valueOf(inscription.getNumTournoi()));
			inscription.setNum(id);
		} 
	}
	
	
	public ArrayList<Type> loadAllTypes() throws DataAccessException {
		ArrayList<Type> types = new ArrayList<Type>();
	
		try {
			ResultSet rs = connexion.sqlSelect("select * from type_utilisateur order by id");
			while (rs.next()) {
				Type type = new Type(
						root, 
						rs.getInt("id"), 
						rs.getString("nom"),
						rs.getString("description"));
				types.add(type);
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return types;
	}
	
	public ArrayList<Ligue> loadAllLigues() throws DataAccessException {
		ArrayList<Ligue> ligues = new ArrayList<Ligue>();
	
		try {
			ResultSet rs = connexion.sqlSelect("select * from ligue order by id");
			while (rs.next()) {
				Ligue ligue = new Ligue(
						root, 
						rs.getInt("id"),
						rs.getString("nom"),
						rs.getString("description"));
				ligues.add(ligue);
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return ligues;
	}
	
	public ArrayList<Club> loadAllClubs(Ligue ligue) throws DataAccessException {
		ArrayList<Club> clubs = new ArrayList<Club>();
	
		try {
			
			ResultSet rs = connexion.sqlSelect("select * from club where ligue_id = ? order by id",
					String.valueOf(ligue.getNum())
					);
			while (rs.next()) {
				Club club = new Club(
						rs.getInt("id"),
						rs.getString("nom"),
						ligue);
				clubs.add(club);
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return clubs;
	}
	
	public ArrayList<Utilisateur> loadAllUtilisateurs(Club club) throws DataAccessException {
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
	
		try {
			ResultSet rs = connexion.sqlSelect("select * from utilisateur where club_id = ? order by id",
					String.valueOf(club.getNum())
					);
			while (rs.next()) {
				Utilisateur utilisateur = new Utilisateur(
						rs.getInt("id"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getInt("tel_fixe"),
						rs.getInt("tel_portable"),
						rs.getString("mail"),
						rs.getString("mdp"),
						loadType(rs.getInt("type_id")),
						club);
				utilisateurs.add(utilisateur);
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return utilisateurs;
	}
	
	
	public ArrayList<Utilisateur> loadAllUtilisateurs(Type type) throws DataAccessException
	{
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from utilisateur where type_id = ? order by id",
					String.valueOf(type.getNum())
					);
			while(rs.next())
			{
				utilisateurs.add ( new Utilisateur(
				rs.getInt("id"),
				rs.getString("nom"),
				rs.getString("prenom"),
				rs.getInt("tel_fixe"),
				rs.getInt("tel_portable"),
				rs.getString("mail"),
				rs.getString("mdp"),
				type,
				loadClub(rs.getInt("club_id"))));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return utilisateurs;
	}

	public ArrayList<Equipe> loadAllEquipes(Categorie categorie) throws DataAccessException
	{
		ArrayList<Equipe> equipes = new ArrayList<Equipe>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from equipe where categorie_id = ? order by id",
					String.valueOf(categorie.getNum())
					);
			while(rs.next())
			{
				equipes.add ( new Equipe(
				rs.getInt("id"),
				rs.getString("nom"),
				categorie,
				loadClub(rs.getInt("club_id"))
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return equipes;
	}
	
	public ArrayList<Equipe> loadAllEquipes(Club club) throws DataAccessException
	{
		ArrayList<Equipe> equipes = new ArrayList<Equipe>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from equipe where club_id = ? order by id",
					String.valueOf(club.getNum())
					);
			while(rs.next())
			{
				equipes.add ( new Equipe(
				rs.getInt("id"),
				rs.getString("nom"),
				loadCategorie(rs.getInt("categorie_id")),
				club
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return equipes;
	}

	public ArrayList<Tournoi> loadAllTournois(Ligue ligue) throws DataAccessException
	{
		ArrayList<Tournoi> tournois = new ArrayList<Tournoi>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from tournoi where ligue_id = ? order by id",
					String.valueOf(ligue.getNum())
					);
			while(rs.next())
			{
				tournois.add ( new Tournoi(
				rs.getInt("id"),
				rs.getString("nom"),
				rs.getString("description"),
				ligue,
				loadCategorie(rs.getInt("categorie_id"))
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return tournois;
	}
	
	public ArrayList<Tournoi> loadAllTournois(Categorie categorie) throws DataAccessException
	{
		ArrayList<Tournoi> tournois = new ArrayList<Tournoi>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from tournoi where categorie_id = ? order by id",
					String.valueOf(categorie.getNum())
					);
			while(rs.next())
			{
				tournois.add ( new Tournoi(
				rs.getInt("id"),
				rs.getString("nom"),
				rs.getString("description"),
				loadLigue(rs.getInt("ligue_id")),
				categorie
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return tournois;
	}

	public ArrayList<Inscription> loadAllInscriptions(Equipe equipe) throws DataAccessException
	{
		ArrayList<Inscription> inscriptions = new ArrayList<Inscription>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from inscription where equipe_id = ? order by equipe_id",
					String.valueOf(equipe.getNum())
					);
			while(rs.next())
			{
				inscriptions.add ( new Inscription(
				equipe,
				loadTournoi(rs.getInt("tournoi_id"))
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return inscriptions;
	}
	
	public ArrayList<Inscription> loadAllInscriptions(Tournoi tournoi) throws DataAccessException
	{
		ArrayList<Inscription> inscriptions = new ArrayList<Inscription>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from inscription where tournoi_id = ? order by tournoi_id",
					String.valueOf(tournoi.getNum())
					);
			while(rs.next())
			{
				inscriptions.add ( new Inscription(
				loadEquipe(rs.getInt("equipe_id")),
				tournoi
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return inscriptions;
	}

	public ArrayList<Etape> loadAllEtapes(Tournoi tournoi) throws DataAccessException
	{
		ArrayList<Etape> etapes = new ArrayList<Etape>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from etape where tournoi_id = ? order by id",
					String.valueOf(tournoi.getNum())
					);
			while(rs.next())
			{
				etapes.add ( new Etape(
				rs.getInt("id"),
				tournoi
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return etapes;
	}

	public ArrayList<Rencontre> loadAllRencontres(Etape etape) throws DataAccessException
	{
		ArrayList<Rencontre> rencontres = new ArrayList<Rencontre>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from rencontre where etape_id = ? order by id",
					String.valueOf(etape.getNum())
					);
			while(rs.next())
			{
				rencontres.add ( new Rencontre(
				rs.getInt("id"),
				rs.getString("lieu"),
				rs.getString("date"),
				etape
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return rencontres;
	}

	public ArrayList<Categorie> loadAllCategories(Ligue ligue) throws DataAccessException
	{
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from categorie where ligue_id = ? order by id",
					String.valueOf(ligue.getNum())
					);
			while(rs.next())
			{
				categories.add ( new Categorie(
				rs.getInt("id"),
				rs.getString("nom"),
				ligue
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return categories;
	}
	
	public ArrayList<Score> loadAllScores(Equipe equipe) throws DataAccessException
	{
		ArrayList<Score> scores = new ArrayList<Score>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from score where equipe_id = ? order by equipe_id",
					String.valueOf(equipe.getNum())
					);
			while(rs.next())
			{
				scores.add ( new Score(
				loadRencontre(rs.getInt("rencontre_id")),
				equipe,
				rs.getInt("points"),
				rs.getBoolean("publie")
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return scores;
	}
	
	public ArrayList<Score> loadAllScores(Rencontre rencontre) throws DataAccessException
	{
		ArrayList<Score> scores = new ArrayList<Score>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from score where rencontre_id = ? order by rencontre_id",
					String.valueOf(rencontre.getNum())
					);
			while(rs.next())
			{
				scores.add ( new Score(
				rencontre,
				loadEquipe(rs.getInt("equipe_id")),
				rs.getInt("points"),
				rs.getBoolean("publie")
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return scores;
	}
	
	public ArrayList<Appartenance> loadAllAppartenances(Equipe equipe) throws DataAccessException
	{
		ArrayList<Appartenance> appartenances = new ArrayList<Appartenance>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from appartenance where equipe_id = ? order by equipe_id",
					String.valueOf(equipe.getNum())
					);
			while(rs.next())
			{
				appartenances.add ( new Appartenance(
				loadUtilisateur(rs.getInt("utilisateur_id")),
				equipe
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return appartenances;
	}
	
	public ArrayList<Appartenance> loadAllAppartenances(Utilisateur utilisateur) throws DataAccessException
	{
		ArrayList<Appartenance> appartenances = new ArrayList<Appartenance>();
		try{
			ResultSet rs = connexion.sqlSelect("select * from appartenance where equipe_id = ? order by equipe_id", 
					String.valueOf(utilisateur.getNum())
					);
			while(rs.next())
			{
				appartenances.add ( new Appartenance(
				utilisateur,
				loadEquipe(rs.getInt("equipe_id"))
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return appartenances;
	}
	
	public Club loadClub(int idClub) throws DataAccessException
	{
		try{
			ResultSet rs = connexion.sqlSelect("select * from club where id = ? order by id",
					String.valueOf(idClub)
					);
			if (rs.next())
			{
				return new Club(
						rs.getInt("id"), 
						rs.getString("nom"), 
						loadLigue(rs.getInt("ligue_id")));
			}
			else
				return null;
		}
	 catch (SQLException e) {
		throw new DataAccessException(e);
	}
			
	}
	
	public Ligue loadLigue(int idLigue) throws DataAccessException
	{
		try{
			ResultSet rs = connexion.sqlSelect("select * from ligue  where id = ? order by id",
					String.valueOf(idLigue)
					);
			if(rs.next())
			{
				return new Ligue(
						root,
						rs.getInt("id"), 
						rs.getString("nom"),
						rs.getString("description"));
			}
			else
				return null;
	}
		 catch (SQLException e) {
				throw new DataAccessException(e);
			}
	}
	
	public Type loadType(int idType) throws DataAccessException
	{
		try
		{
			ResultSet rs = connexion.sqlSelect("select * from type  where id = ? order by id",
					String.valueOf(idType)
					);
			if(rs.next())
			{
				return new Type(
						root,
						rs.getInt("id"), 
						rs.getString("nom"),
						rs.getString("description"));
			}
			else
				return null;
		}
		catch (SQLException e) 
		{
			throw new DataAccessException(e);
		}
	}
	
	public Rencontre loadRencontre(int idRencontre) throws DataAccessException
	{
		try
		{
			ResultSet rs = connexion.sqlSelect("select * from rencontre  where id = ? order by id",
					String.valueOf(idRencontre)
							);
			if(rs.next())
			{
				return new Rencontre(
				rs.getInt("id"), 
				rs.getString("lieu"),
				rs.getString("date"),
				loadEtape(rs.getInt("etape_id")));
			}
			else
				return null;
		}
		catch (SQLException e) 
		{
			throw new DataAccessException(e);
		}
	}
	
	public Etape loadEtape(int idEtape) throws DataAccessException
	{
		try
		{
			ResultSet rs = connexion.sqlSelect("select * from etape  where id = ? order by id",
					String.valueOf(idEtape)
					);
			if(rs.next())
			{
				return new Etape(
				rs.getInt("id"), 
				loadTournoi(rs.getInt("tournoi_id")));
			}
			else
				return null;
		}
		catch (SQLException e) 
		{
			throw new DataAccessException(e);
		}
	}

	public Tournoi loadTournoi(int idTournoi) throws DataAccessException
	{
		try
		{
			ResultSet rs = connexion.sqlSelect("select * from tournoi where id = ? order by id",
					String.valueOf(idTournoi)
					);
			if(rs.next())
			{
				return new Tournoi(
				rs.getInt("id"), 
				rs.getString("nom"),
				rs.getString("description"),
				loadLigue(rs.getInt("ligue_id")),
				loadCategorie(rs.getInt("categorie_id")));
			}
			else
				return null;
		}
		catch (SQLException e) 
		{
			throw new DataAccessException(e);
		}
	}

	public Categorie loadCategorie(int idCategorie) throws DataAccessException
	{
		try
		{
			ResultSet rs = connexion.sqlSelect("select * from categorie where id = ? order by id",
					String.valueOf(idCategorie)
					);
			if(rs.next())
			{
				return new Categorie(
				rs.getInt("id"), 
				rs.getString("nom"),
				loadLigue(rs.getInt("ligue_id")));
			}
			else
				return null;
		}
		catch (SQLException e) 
		{
			throw new DataAccessException(e);
		}
	}
	
	public Equipe loadEquipe(int idEquipe) throws DataAccessException
	{
		try
		{
			ResultSet rs = connexion.sqlSelect("select * from equipe where id = ? order by id",
					String.valueOf(idEquipe)
					);
			if(rs.next())
			{
				return new Equipe(
				rs.getInt("id"), 
				rs.getString("nom"),
				loadCategorie(rs.getInt("categorie_id")),
				loadClub(rs.getInt("club_id")));
			}
			else
				return null;
		}
		catch (SQLException e) 
		{
			throw new DataAccessException(e);
		}
	}

	public Utilisateur loadUtilisateur(int idUtilisateur) throws DataAccessException
	{
		try
		{
			ResultSet rs = connexion.sqlSelect("select * from utilisateur where id = ? order by id",
					String.valueOf(idUtilisateur)
					);
			if(rs.next())
			{
				return new Utilisateur(
				rs.getInt("id"), 
				rs.getString("nom"),
				rs.getString("prenom"),
				rs.getInt("tel_fixe"),
				rs.getInt("tel_portable"),
				rs.getString("mail"),
				rs.getString("mdp"),
				loadType(rs.getInt("type_id")),
				loadClub(rs.getInt("club_id")));
				
			}
			else
				return null;
		}
		catch (SQLException e) 
		{
			throw new DataAccessException(e);
		}
	}
	
	public void delete(Type type) throws DataAccessException
	{
		connexion.sqlUpdate("delete from type where id = ?", ""
				+ type.getNum());
	}
	
	public void delete(Ligue ligue) throws DataAccessException
	{
		connexion.sqlUpdate("delete from ligue where id = ?", ""
				+ ligue.getNum());
	}
	
	public void delete(Utilisateur utilisateur) throws DataAccessException
	{
		connexion.sqlUpdate("delete from utilisateur where id = ?", ""
				+ utilisateur.getNum());
	}
	
	public void delete(Tournoi tournoi) throws DataAccessException
	{
		connexion.sqlUpdate("delete from tournoi where id = ?", ""
				+ tournoi.getNum());
	}
	
	public void delete(Etape etape) throws DataAccessException
	{
		connexion.sqlUpdate("delete from etape where id = ?", ""
				+ etape.getNum());
	}
	
	public void delete(Rencontre rencontre) throws DataAccessException
	{
		connexion.sqlUpdate("delete from rencontre where id = ?", ""
				+ rencontre.getNum());
	}
	
	public void delete(Club club) throws DataAccessException
	{
		connexion.sqlUpdate("delete from club where id = ?", ""
				+ club.getNum());
	}
	
	public void delete(Equipe equipe) throws DataAccessException
	{
		connexion.sqlUpdate("delete from equipe where id = ?", ""
				+ equipe.getNum());
	}
	
	public void delete(Categorie categorie) throws DataAccessException {
		connexion.sqlUpdate("delete from equipe where id = ?", ""
				+ categorie.getNum());
	}

	public void delete(Appartenance appartenance) throws DataAccessException {
		connexion.sqlUpdate("delete from appartenance where utilisateur_id = ? and equipe_id =?", ""
				+ appartenance.getNumUtilisateur(), String.valueOf(appartenance.getNumEquipe()));
	}

	public void delete(Inscription inscription) throws DataAccessException {
		connexion.sqlUpdate("delete from inscription where equipe_id = ? and tournoi_id = ?", ""
				+ inscription.getNumEquipe(), String.valueOf(inscription.getNumTournoi()));
	}
	
	public void delete(Score score) throws DataAccessException {
		connexion.sqlUpdate("delete from score where equipe_id = ? and rencontre_id = ?", ""
				+ score.getNumEquipe(), String.valueOf(score.getNumRencontre()));
	}
	
	SQLMapper(Root root) throws DataAccessException
	{

			this.root = root;
			connexion = new Connexion();

	}

	SQLMapper(Root root, Connexion connexion) throws DataAccessException
	{
		this.root = root;
		this.connexion = connexion;
	}

	

	
}
