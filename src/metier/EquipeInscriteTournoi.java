package metier;

import java.sql.SQLException;

public class EquipeInscriteTournoi {
	private int num = NO_KEY;
	private Equipe equipe;
	private Tournoi tournoi;
	private final static int NO_KEY = -1;
	
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
	
	//
	public Tournoi getTournoi()
	{
		return tournoi;
	}
	
	public void setTournoi(Tournoi tournoi) throws SQLException
	{
		if (this.tournoi != tournoi)
		{
			if (this.tournoi != null)
				this.tournoi.removeEquipeInscriteTournoi(this);
			this.tournoi = tournoi;
			if (this.tournoi != null)
				tournoi.addEquipeInscriteTournoi(this);
		}
	}
	
	public int getNumTournoi()
	{
		return tournoi.getNum();
	}
	
	public Equipe getEquipe()
	{
		return equipe;
	}
	
	public void setEquipe(Equipe equipe) throws SQLException
	{
		if (this.equipe != equipe)
		{
			if (this.equipe != null)
				this.equipe.removeEquipeInscriteTournoi(this);
			this.equipe = equipe;
			if (this.equipe != null)
				equipe.addEquipeInscriteTournoi(this);
		}
	}
	
	public int getNumEquipe()
	{
		return equipe.getNum();
	}
	
	EquipeInscriteTournoi(Equipe equipe, Tournoi tournoi) throws SQLException
	{
		setEquipe(equipe);
		setTournoi(tournoi);
	}

}