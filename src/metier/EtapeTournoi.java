package metier;

import java.sql.SQLException;
import java.util.ArrayList;

public class EtapeTournoi
{
	private int num = NO_KEY;
	private int typeEtape;
	private Tournoi tournoi;
	private final static int NO_KEY = -1;
	private ArrayList<Rencontre> rencontres;
	
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
	
	public int getTypeEtape()
	{
		return typeEtape;
	}

	void setTypeEtape(int typeEtape)
	{
		this.typeEtape = typeEtape;
	}
	
	// Tournoi
	public Tournoi getTournoi()
	{
		return tournoi;
	}
	
	public void setTournoi(Tournoi tournoi) throws SQLException
	{
		if (this.tournoi != tournoi)
		{
			if (this.tournoi != null)
				this.tournoi.removeEtapeTournoi(this);
			this.tournoi = tournoi;
			if (this.tournoi != null)
				tournoi.addEtapeTournoi(this);
		}
	}
	
	public int getNumTournoi()
	{
		return tournoi.getNum();
	}
	
	EtapeTournoi(int num, int typeEtape, Tournoi tournoi) throws SQLException
	{
		setNum(num);
		setTypeEtape(typeEtape);
		setTournoi(tournoi);
	}

	public EtapeTournoi(int typeEtape, Tournoi tournoi) throws SQLException
	{
		this(NO_KEY, typeEtape, tournoi);
	}

	//Rencontre
	public int getNbRencontres() throws SQLException 
	{
		loadAllRencontres();
		return rencontres.size();
	}

	public Rencontre getRencontre(int index) throws SQLException 
	{
		loadAllRencontres();
		return rencontres.get(index);
	}
	
	private boolean possedeRencontre(Rencontre rencontre) throws SQLException 
	{
		loadAllRencontres();
		return rencontres.contains(rencontre);
	}
	
	private void loadAllRencontres() throws SQLException
	{
		if (rencontres == null)
		{
			rencontres = new ArrayList<Rencontre>();
			RootSuperAdmin rootSuperAdmin = getTournoi().getLigue().getRootSuperAdmin();
			rootSuperAdmin.loadRencontre(this);
		}
	}
	
	public void addRencontre(Rencontre rencontre) throws SQLException 
	{
		loadAllRencontres();
		if(!possedeRencontre(rencontre))
		{
			rencontres.add(rencontre);
			rencontre.setEtapeTournoi(this);
		}
	}

	public void removeRencontre(Rencontre rencontre) throws SQLException 
	{
		loadAllRencontres();
		if(possedeRencontre(rencontre))
		{
			rencontres.remove(rencontre);
			rencontre.setEtapeTournoi(null);
		}
	}
	
}
