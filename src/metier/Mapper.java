package metier;

import java.util.ArrayList;

interface Mapper {

	final static int NO_KEY = -1;
	
	public void save(Type type) throws DataAccessException;
	public void delete(Type type) throws DataAccessException;
	public void save(Ligue ligue) throws DataAccessException;
	public void save(Club club) throws DataAccessException;
	public void save(Utilisateur utilisateur) throws DataAccessException;
	
	public ArrayList<Type> loadAllTypes() throws DataAccessException;
	public ArrayList<Ligue> loadAllLigues() throws DataAccessException;
	public ArrayList<Club> loadAllClubs(Ligue ligue) throws DataAccessException;
	public ArrayList<Utilisateur> loadAllUtilisateurs(Club club) throws DataAccessException;
	
	 
	public ArrayList<Utilisateur> loadUtilisateurs(Type type) throws DataAccessException;
	public ArrayList<Utilisateur> loadUtilisateurs(Club club) throws DataAccessException;
	public ArrayList<Equipe> loadEquipes(Categorie categorie) throws DataAccessException;
	public ArrayList<Equipe> loadEquipes(Club club) throws DataAccessException;
	public ArrayList<Club> loadClubs(Ligue ligue) throws DataAccessException;
	public ArrayList<Tournoi> loadTournois(Ligue ligue) throws DataAccessException;
	public ArrayList<Tournoi> loadTournois(Categorie categorie) throws DataAccessException;
	public ArrayList<EquipeInscriteTournoi> loadEquipeInscriteTournois(Equipe equipe) throws DataAccessException;
	public ArrayList<EquipeInscriteTournoi> loadEquipeInscriteTournois(Tournoi tournoi) throws DataAccessException;
	public ArrayList<EtapeTournoi> loadEtapesTournoi(Tournoi tournoi) throws DataAccessException;
	public ArrayList<Rencontre> loadRencontres(EtapeTournoi etapeTournoi) throws DataAccessException;
	public ArrayList<Categorie> loadCategories(Ligue ligue) throws DataAccessException;
	
	
	public Club loadClub(int idClub) throws DataAccessException;
	public Ligue loadLigue(int idLigue) throws DataAccessException;
	public Type loadType(int idType) throws DataAccessException;
	public Rencontre loadRencontre(int idRencontre) throws DataAccessException;
	public EtapeTournoi loadEtapeTournoi(int idEtapeTournoi) throws DataAccessException;
	public Tournoi loadTournoi(int idTournoi) throws DataAccessException;
	public Categorie loadCategorie(int idCategorie) throws DataAccessException;
	public Equipe loadEquipe(int idEquipe) throws DataAccessException;
	public Utilisateur loadUtilisateur(int idUtilisateur) throws DataAccessException;
	
	
}
 