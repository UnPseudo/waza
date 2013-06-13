package metier;

import java.util.ArrayList;

interface Mapper {

	final static int NO_KEY = -1;
	
	public void save(Type type) throws DataAccessException;
	public void delete(Type type) throws DataAccessException;
	public void save(Ligue ligue) throws DataAccessException;
	public void save(Club club) throws DataAccessException;
	public void save(Utilisateur utilisateur) throws DataAccessException;
	public void save(Tournoi tournoi) throws DataAccessException;
	public void save(Etape etape) throws DataAccessException;
	public void save(Rencontre rencontre) throws DataAccessException;
	public void save(Categorie categorie) throws DataAccessException;
	public void save(Equipe equipe) throws DataAccessException;
	public void save(Score score) throws DataAccessException;
	public void save(Appartenance appartenance) throws DataAccessException;
	public void save(Inscription inscription) throws DataAccessException;

	
	public ArrayList<Type> loadAllTypes() throws DataAccessException;
	public ArrayList<Ligue> loadAllLigues() throws DataAccessException;
	public ArrayList<Club> loadAllClubs(Ligue ligue) throws DataAccessException;
	public ArrayList<Utilisateur> loadAllUtilisateurs(Club club) throws DataAccessException;
	public ArrayList<Utilisateur> loadAllUtilisateurs(Type type) throws DataAccessException;
	public ArrayList<Equipe> loadAllEquipes(Categorie categorie) throws DataAccessException;
	public ArrayList<Equipe> loadAllEquipes(Club club) throws DataAccessException;
	public ArrayList<Tournoi> loadAllTournois(Ligue ligue) throws DataAccessException;
	public ArrayList<Tournoi> loadAllTournois(Categorie categorie) throws DataAccessException;
	public ArrayList<Inscription> loadAllInscriptions(Equipe equipe) throws DataAccessException;
	public ArrayList<Inscription> loadAllInscriptions(Tournoi tournoi) throws DataAccessException;
	public ArrayList<Etape> loadAllEtapes(Tournoi tournoi) throws DataAccessException;
	public ArrayList<Rencontre> loadAllRencontres(Etape etape) throws DataAccessException;
	public ArrayList<Categorie> loadAllCategories(Ligue ligue) throws DataAccessException;
	public ArrayList<Score> loadAllScores(Rencontre rencontre) throws DataAccessException;
	public ArrayList<Score> loadAllScores(Equipe equipe) throws DataAccessException;
	public ArrayList<Appartenance> loadAllAppartenances(Equipe equipe) throws DataAccessException;
	public ArrayList<Appartenance> loadAllAppartenances(Utilisateur utilisateur) throws DataAccessException;
	
	
	
	public Club loadClub(int idClub) throws DataAccessException;
	public Ligue loadLigue(int idLigue) throws DataAccessException;
	public Type loadType(int idType) throws DataAccessException;
	public Rencontre loadRencontre(int idRencontre) throws DataAccessException;
	public Etape loadEtape(int idEtape) throws DataAccessException;
	public Tournoi loadTournoi(int idTournoi) throws DataAccessException;
	public Categorie loadCategorie(int idCategorie) throws DataAccessException;
	public Equipe loadEquipe(int idEquipe) throws DataAccessException;
	public Utilisateur loadUtilisateur(int idUtilisateur) throws DataAccessException;
	
	
}
 