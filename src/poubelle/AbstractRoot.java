package poubelle;

import java.util.ArrayList;

import metier.Categorie;
import metier.Club;
import metier.DataAccessException;
import metier.Equipe;
import metier.Ligue;
import metier.Mapper;
import metier.Rencontre;
import metier.Tournoi;
import metier.Type;


abstract class AbstractRoot {

	protected Mapper mapper;
	
	abstract void save(Type type) throws DataAccessException;
	abstract void loadAllTypes() throws DataAccessException;
	abstract void loadAllLigues() throws DataAccessException;
	abstract void loadAllClubs(Ligue ligue) throws DataAccessException;
	abstract void loadAllUtilisateurs(Club club) throws DataAccessException;
	 
	abstract void loadUtilisateurs(Type type) throws DataAccessException;
	abstract void loadUtilisateurs(Club club) throws DataAccessException;
	abstract void loadEtapesTournoi(Tournoi tournoi) throws DataAccessException;;
	abstract void loadEquipeInscriteTournois(Tournoi tournoi) throws DataAccessException;;
	abstract void loadClubs(Ligue ligue) throws DataAccessException;
	abstract void loadTournois(Ligue ligue) throws DataAccessException;
	abstract void loadCategories(Ligue ligue) throws DataAccessException;
	abstract void loadRencontres(EtapeTournoi etapeTournoi)throws DataAccessException;
	abstract void loadEquipeInscriteTournois(Equipe equipe) throws DataAccessException;
	abstract void loadEquipes(Club club) throws DataAccessException;
	abstract void loadEquipes(Categorie categorie) throws DataAccessException;
	abstract void loadTournois(Categorie categorie) throws DataAccessException;
	
	
	abstract Club loadClub(int idClub) throws DataAccessException;
	abstract Ligue loadLigue(int idLigue) throws DataAccessException;
	abstract Type loadType(int idType) throws DataAccessException;
	abstract Rencontre loadRencontre(int idRencontre) throws DataAccessException;
	abstract EtapeTournoi loadEtapeTournoi(int idEtapeTournoi) throws DataAccessException;
	abstract Tournoi loadTournoi(int idTournoi) throws DataAccessException;
	abstract Categorie loadCategorie(int idCategorie) throws DataAccessException;
	abstract Equipe loadEquipe(int idEquipe) throws DataAccessException;
	
	
	
	
		
}
 