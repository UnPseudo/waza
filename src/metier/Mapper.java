package metier;

import java.util.ArrayList;
import java.util.Collection;

interface Mapper {

	final static int NO_KEY = -1;
	
	public void save(Type type) throws DataAccessException;
	public ArrayList<Type> loadAllTypes() throws DataAccessException;
	public ArrayList<Utilisateur> loadUtilisateurs(Type type) throws DataAccessException;
	public ArrayList<Utilisateur> loadUtilisateurs(Club club) throws DataAccessException;
	
	public Club loadClub(int idClub) throws DataAccessException;
	public Ligue loadLigue(int idLigue) throws DataAccessException;
	public Type loadType(int idType) throws DataAccessException;
	public Rencontre loadRencontre(int idRencontre) throws DataAccessException;
	public EtapeTournoi loadEtapeTournoi(int idEtapeTournoi) throws DataAccessException;
	public Tournoi loadTournoi(int idTournoi) throws DataAccessException;
	public Categorie loadCategorie(int idCategorie) throws DataAccessException;
	public Equipe loadEquipe(int idEquipe) throws DataAccessException;
	
}
