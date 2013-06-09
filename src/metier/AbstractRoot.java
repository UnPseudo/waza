package metier;

public abstract class AbstractRoot {

	abstract void save(Type type) throws DataAccessException;
	abstract void loadAllTypes() throws DataAccessException;
	abstract void loadUtilisateurs(Type type) throws DataAccessException;
	abstract void loadUtilisateurs(Club club) throws DataAccessException;
	
	abstract Club loadClub(int idClub) throws DataAccessException;
	abstract Ligue loadLigue(int idLigue) throws DataAccessException;
	abstract Type loadType(int idType) throws DataAccessException;
	abstract Rencontre loadRencontre(int idRencontre) throws DataAccessException;
	abstract EtapeTournoi loadEtapeTournoi(int idEtapeTournoi) throws DataAccessException;
	abstract Tournoi loadTournoi(int idTournoi) throws DataAccessException;
	abstract Categorie loadCategorie(int idCategorie) throws DataAccessException;
	abstract Equipe loadEquipe(int idEquipe) throws DataAccessException;
}
 