package metier;

import java.util.ArrayList;
import java.util.Collection;

interface Mapper {

	final static int NO_KEY = -1;
	
	public void save(Type type) throws DataAccessException;
	public ArrayList<Type> loadAllTypes() throws DataAccessException;
	public ArrayList<Utilisateur> loadUtilisateurs(Type type) throws DataAccessException;
	public Club loadClub(int idClub) throws DataAccessException;
	public Ligue loadLigue(int idLigue) throws DataAccessException;
	
	
}
