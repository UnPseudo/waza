package metier;

/**
 * Exception lev&eacute;e en cas d'erreur de lecture ou d'&eacute;criture de donn&eacute;es.
 *
 */

@SuppressWarnings("serial")
public class DataAccessException extends Throwable
{
	private Exception e;
	
	/**
	 * Retourne l'exception ayant &eacute;t&eacute; lev&eacute;e au moment de l'acc&egrave;s aux donn&eacute;es. 
	 * Par exemple SQLException.
	 * @return l'exception lev&eacute;e par l'acc&egrave;s aux donn&eacute;es.
	 */
	
	public Exception getException()
	{
		return e;
	}
	
	DataAccessException(Exception e)
	{
		this.e = e;
	}

	@Override
	public void printStackTrace()
	{
		super.printStackTrace();
		e.printStackTrace();
	}
	
	public String toString()
	{
		return "DataAccessException : throw by " + e.toString(); 
	}
}
