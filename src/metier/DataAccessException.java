package metier;

/**
 * Exception lev&eacute;e en cas d'erreur de lecture ou d'&eacute;criture de donn&eacute;es.
 *
 */

@SuppressWarnings("serial")
public class DataAccessException extends Throwable
{
	private Exception e = null;
	private String s = null;
	
	/**
	 * Retourne l'exception ayant &eacute;t&eacute; lev&eacute;e au moment de l'acc&egrave;s aux donn&eacute;es. 
	 * Par exemple DataAccessException.
	 * @return l'exception lev&eacute;e par l'acc&egrave;s aux donn&eacute;es.
	 */
	
	public Exception getException()
	{
		return e;
	}
	
	public DataAccessException(Exception e)
	{
		this.e = e;
	}
	
	public DataAccessException(String s)
	{
		this.s = s;
	}
	

	@Override
	public void printStackTrace()
	{
		super.printStackTrace();
		if (e != null)
			e.printStackTrace();
	}
	
	public String toString()
	{
		return "DataAccessException : thrown by " + ((e != null) ? e.toString(): s); 
	}
}
