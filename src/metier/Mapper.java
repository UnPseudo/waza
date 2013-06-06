package metier;

import java.util.ArrayList;
import java.util.Collection;

interface Mapper {

	final static int NO_KEY = -1;
	
	public void save(Type type) throws DataAccessException;
	public ArrayList<Type> loadAllTypes() throws DataAccessException;
	
	
	
}
