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
		ResultSet rs = connexion.sqlSelect("select * from type order by id");
		try {
			while (rs.next()) {
				Type type = new Type(root, rs.getInt("num"), rs.getString("nom"),
						rs.getString("description"));
				types.add(type);
			}
			return types;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

}
