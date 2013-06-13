package metier;


import java.util.ArrayList;

public class Etape
{
	private int num = NO_KEY;
	private Tournoi tournoi;
	private final static int NO_KEY = -1;
	private ArrayList<Rencontre> rencontres = new ArrayList<Rencontre>();
	
	private boolean allRencontresLoaded = false;
	
	//
	public int getNum()
	{
		return num;
	}
 
	void setNum(int num)
	{
		if (this.num != NO_KEY)
			throw new RuntimeException("Cannot change DB ID");
		this.num = num;
	}
	
	// Tournoi
	public Tournoi getTournoi()
	{
		return tournoi;
	}
	
	public void setTournoi(Tournoi tournoi) throws DataAccessException
	{
		if (this.tournoi != tournoi)
		{
			if (this.tournoi != null)
				this.tournoi.removeEtape(this);
			this.tournoi = tournoi;
			if (this.tournoi != null)
				tournoi.addEtape(this);
		}
	}
	
	public int getNumTournoi()
	{
		return tournoi.getNum();
	}
	
	Etape(int num, Tournoi tournoi) throws DataAccessException
	{
		setNum(num);
		setTournoi(tournoi);
	}

	public Etape(Tournoi tournoi) throws DataAccessException
	{
		this(NO_KEY, tournoi);
	}

	//Rencontre
	public int getNbRencontres() throws DataAccessException 
	{
		loadAllRencontres();
		return rencontres.size();
	}

	public Rencontre getRencontre(int index) throws DataAccessException 
	{
		loadAllRencontres();
		return rencontres.get(index);
	}
	
	private boolean possedeRencontre(Rencontre rencontre) throws DataAccessException 
	{
		loadAllRencontres();
		return rencontres.contains(rencontre);
	}
	
	private void loadAllRencontres() throws DataAccessException
	{
		if (!allRencontresLoaded)
		{
			Root root = getTournoi().getLigue().getRoot();
			root.loadAllRencontres(this);
			allRencontresLoaded = true;
		}
	}
	
	public void addRencontre(Rencontre rencontre) throws DataAccessException 
	{
		loadAllRencontres();
		if(!possedeRencontre(rencontre))
		{
			rencontres.add(rencontre);
			rencontre.setEtape(this);
		}
	}

	public void removeRencontre(Rencontre rencontre) throws DataAccessException 
	{
		loadAllRencontres();
		if(possedeRencontre(rencontre))
		{
			rencontres.remove(rencontre);
			rencontre.setEtape(null);
		}
	}
	
}