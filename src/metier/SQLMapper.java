package metier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import db.Connexion;

class SQLMapper implements Mapper {
	AbstractRoot root;
	Connexion connexion;

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

	public ArrayList<Type> loadAllTypes() throws DataAccessException {
		ArrayList<Type> types = new ArrayList<Type>();
	
		try {
			ResultSet rs = connexion.sqlSelect("select * from type order by id");
			while (rs.next()) {
				Type type = new Type(
						root, 
						rs.getInt("num"), 
						rs.getString("nom"),
						rs.getString("description"));
				types.add(type);
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return types;
	}
	
	public ArrayList<Utilisateur> loadUtilisateurs(Type type) throws DataAccessException
	{
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try{
			ResultSet rs = connexion.sqlSelect("select * " +
					"from utilisateur " +
					"where type_id = " + type.getNum() +
					"order by id");
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
					"where id = " + idLigue);
			if(rs.next())
			{
				return new Ligue(
						root,
						rs.getInt("id"), 
						rs.getString("nom"));
			}
			else
				return null;
	}
		 catch (SQLException e) {
				throw new DataAccessException(e);
			}
	}
}
