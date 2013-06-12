package metier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Connexion;

class SQLMapper implements Mapper {
	Root root;
	Connexion connexion;

	
	
	
	public void save(Type type) throws DataAccessException {
			if (type.getNum() == NO_KEY) {
				int id;
				id = connexion.sqlInsert(
						"insert into type_utilisateur(nom, description) values (?, ?)",
						type.getNom(), type.getDescription());
				type.setNum(id);
			} else {
				connexion
						.sqlUpdate(
								"update type_utilisateur set nom = ?, description  = ? where id = ?",
								type.getNom(), type.getDescription(),
								"" + type.getNum());
			}
	} 

	public void delete(Type type) throws DataAccessException
	{
		connexion.sqlUpdate("delete from type_utilisateur where id = ?", ""
				+ type.getNum());
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
							"update club set nom = ?, description  = ?, ligue_id = ?, where id = ?",
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
					"insert into utilisateur(nom, prenom, tel_fixe, tel_portable, mail, mdp, type_utilisateur_id, club_id) values (?, ?, ?, ?, ?, ?, ?, ?)",
					utilisateur.getNom(), utilisateur.getPrenom(), String.valueOf(utilisateur.getTelFixe()), 
					String.valueOf(utilisateur.getTelPortable()), utilisateur.getMail(), utilisateur.getMdp(), 
					String.valueOf(utilisateur.getNumType()), String.valueOf(utilisateur.getNumClub()));
			utilisateur.setNum(id);
		} else {
			connexion
					.sqlUpdate(
							"update ligue set nom = ?, prenom  = ?, tel_fixe  = ?, tel_portable  = ?, mail  = ?, mdp  = ?, type_utilisateur_id  = ?, club_id  = ? where id = ?",
							utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getPrenom(), String.valueOf(utilisateur.getTelFixe()), 
							String.valueOf(utilisateur.getTelPortable()), utilisateur.getMail(), utilisateur.getMdp(), 
							String.valueOf(utilisateur.getNumType()), String.valueOf(utilisateur.getNumClub()),
							"" + utilisateur.getNum());
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
			ResultSet rs = connexion.sqlSelect("select * from club where ligue_id = " + ligue.getNum() + " order by id");
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
			ResultSet rs = connexion.sqlSelect("select * from utilisateur where club_id = " + club.getNum() + " order by id");
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
	
	
	public ArrayList<Utilisateur> loadUtilisateurs(Type type) throws DataAccessException
	{
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from utilisateur " +
					"where type_utilisateur_id = " + type.getNum() +
					" order by id");
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

	public ArrayList<Utilisateur> loadUtilisateurs(Club club) throws DataAccessException
	{
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from utilisateur " +
					"where club_id = " + club.getNum() +
					" order by id");
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
				loadType(rs.getInt("type_id")),
				club
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return utilisateurs;
	}
	
	public ArrayList<Equipe> loadEquipes(Categorie categorie) throws DataAccessException
	{
		ArrayList<Equipe> equipes = new ArrayList<Equipe>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from utilisateur " +
					"where categorie_id = " + categorie.getNum() +
					" order by id");
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
	
	public ArrayList<Equipe> loadEquipes(Club club) throws DataAccessException
	{
		ArrayList<Equipe> equipes = new ArrayList<Equipe>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from utilisateur " +
					"where club_id = " + club.getNum() +
					" order by id");
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

	public ArrayList<Club> loadClubs(Ligue ligue) throws DataAccessException
	{
		ArrayList<Club> clubs = new ArrayList<Club>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from club " +
					"where ligue_id = " + ligue.getNum() +
					" order by id");
			while(rs.next())
			{
				clubs.add ( new Club(
				rs.getInt("id"),
				rs.getString("nom"),
				ligue
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return clubs;
	}

	public ArrayList<Tournoi> loadTournois(Ligue ligue) throws DataAccessException
	{
		ArrayList<Tournoi> tournois = new ArrayList<Tournoi>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from tournoi " +
					"where ligue_id = " + ligue.getNum() +
					" order by id");
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
	
	public ArrayList<Tournoi> loadTournois(Categorie categorie) throws DataAccessException
	{
		ArrayList<Tournoi> tournois = new ArrayList<Tournoi>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from tournoi " +
					"where categorie_id = " + categorie.getNum() +
					" order by id");
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

	public ArrayList<EquipeInscriteTournoi> loadEquipeInscriteTournois(Equipe equipe) throws DataAccessException
	{
		ArrayList<EquipeInscriteTournoi> equipeInscriteTournois = new ArrayList<EquipeInscriteTournoi>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from equipe_inscrite_tournoi " +
					"where equipe_id = " + equipe.getNum() +
					" order by id");
			while(rs.next())
			{
				equipeInscriteTournois.add ( new EquipeInscriteTournoi(
				equipe,
				loadTournoi(rs.getInt("tournoi_id"))
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return equipeInscriteTournois;
	}
	
	public ArrayList<EquipeInscriteTournoi> loadEquipeInscriteTournois(Tournoi tournoi) throws DataAccessException
	{
		ArrayList<EquipeInscriteTournoi> equipeInscriteTournois = new ArrayList<EquipeInscriteTournoi>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from equipe_inscrite_tournoi " +
					"where tournoi_id = " + tournoi.getNum() +
					" order by id");
			while(rs.next())
			{
				equipeInscriteTournois.add ( new EquipeInscriteTournoi(
				loadEquipe(rs.getInt("equipe_id")),
				tournoi
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return equipeInscriteTournois;
	}

	public ArrayList<EtapeTournoi> loadEtapesTournoi(Tournoi tournoi) throws DataAccessException
	{
		ArrayList<EtapeTournoi> etapesTournoi = new ArrayList<EtapeTournoi>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from etape_tournoi " +
					"where tournoi_id = " + tournoi.getNum() +
					" order by id");
			while(rs.next())
			{
				etapesTournoi.add ( new EtapeTournoi(
				rs.getInt("id"),
				rs.getInt("type_tournoi_id"),
				tournoi
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return etapesTournoi;
	}

	public ArrayList<Rencontre> loadRencontres(EtapeTournoi etapeTournoi) throws DataAccessException
	{
		ArrayList<Rencontre> rencontres = new ArrayList<Rencontre>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from rencontre " +
					"where etape_tournoi_id = " + etapeTournoi.getNum() +
					" order by id");
			while(rs.next())
			{
				rencontres.add ( new Rencontre(
				rs.getInt("id"),
				rs.getString("lieu"),
				rs.getString("date"),
				etapeTournoi
				));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return rencontres;
	}

	public ArrayList<Categorie> loadCategories(Ligue ligue) throws DataAccessException
	{
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from categorie " +
					"where ligue_id = " + ligue.getNum() +
					" order by id");
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
	
	
	public Club loadClub(int idClub) throws DataAccessException
	{
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from club " +
					"where id = " + idClub + 
					" order by id");
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
			ResultSet rs = connexion.sqlSelect("select * " +
					"from ligue " +
					" where id = " + idLigue);
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
			ResultSet rs = connexion.sqlSelect("select * " +
					"from type_utilisateur " +
					" where id = " + idType);
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
			ResultSet rs = connexion.sqlSelect("select * " +
					"from recontre " +
					" where id = " + idRencontre);
			if(rs.next())
			{
				return new Rencontre(
				rs.getInt("id"), 
				rs.getString("lieu"),
				rs.getString("date"),
				loadEtapeTournoi(rs.getInt("etape_tournoi_id")));
			}
			else
				return null;
		}
		catch (SQLException e) 
		{
			throw new DataAccessException(e);
		}
	}
	
	public EtapeTournoi loadEtapeTournoi(int idEtapeTournoi) throws DataAccessException
	{
		try
		{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from etape_tournoi " +
					" where id = " + idEtapeTournoi);
			if(rs.next())
			{
				return new EtapeTournoi(
				rs.getInt("id"), 
				rs.getInt("type_tournoi"),
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
			ResultSet rs = connexion.sqlSelect("select * " +
					"from tournoi " +
					" where id = " + idTournoi);
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
			ResultSet rs = connexion.sqlSelect("select * " +
					"from categorie " +
					"where id = " + idCategorie);
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
			ResultSet rs = connexion.sqlSelect("select * " +
					"from equipe " +
					"where id = " + idEquipe);
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
			ResultSet rs = connexion.sqlSelect("select * " +
					"from utilisateur " +
					"where id = " + idUtilisateur);
			if(rs.next())
			{
				return new Utilisateur(
				rs.getInt("id"), 
				rs.getString("nom"),
				rs.getString("prenom"),
				rs.getInt("tel_fixe"),
				rs.getInt("tel_porabe"),
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
