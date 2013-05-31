package metier;

import java.sql.SQLException;
import java.util.ArrayList;

public class Rencontre
{
	private int num = NO_KEY;
	private String lieu = null;
	private String date = null;
	private EtapeTournoi etapeTournoi;
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
	
	public String getLieu()
	{
		return lieu;
	}
	
	public String setLieu(String lieu)
	{
		return this.lieu = lieu;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String setDate(String date)
	{
		return this.date = date;
	}
	
	//
	public EtapeTournoi getEtapeTournoi()
	{
		return etapeTournoi;
	}
	
	public void setEtapeTournoi(EtapeTournoi etapeTournoi) throws SQLException
	{
		if (this.etapeTournoi != etapeTournoi)
		{
			if (this.etapeTournoi != null)
				this.etapeTournoi.removeRencontre(this);
			this.etapeTournoi = etapeTournoi;
			if (this.etapeTournoi != null)
				etapeTournoi.addRencontre(this);
		}
	}
	
	public int getNumEtapeTournoi()
	{
		return etapeTournoi.getNum();
	}
	
	Rencontre(int num, String lieu, String date, EtapeTournoi etapeTournoi) throws SQLException
	{
		setNum(num);
		setLieu(lieu);
		setDate(date);
		setEtapeTournoi(etapeTournoi);
	}

	public Rencontre(String lieu, String date, EtapeTournoi etapeTournoi) throws SQLException
	{
		this(NO_KEY, lieu, date, etapeTournoi);
	}
}
