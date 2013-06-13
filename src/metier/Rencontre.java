package metier;

import java.util.ArrayList;

public class Rencontre
{
	private int num = NO_KEY;
	private String lieu = null;
	private String date = null;
	private Etape etape;
	private ArrayList<Score> scores = new ArrayList<Score>();
	private final static int NO_KEY = -1;
	
	private boolean allScoresLoaded = false;
	//
	public int getNum()
	{
		return num;
	} 
	
	public void setNum(int num)
	{
		if (this.num != NO_KEY)
			throw new RuntimeException("Cannot change DB ID");
		this.num = num;
	}
	
	public String getLieu()
	{ 
		return lieu;
	}
	
	public void setLieu(String lieu)
	{
		this.lieu = lieu;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	//
	public Etape getEtape()
	{
		return etape;
	}
	
	public void setEtape(Etape etape) throws DataAccessException
	{
		if (this.etape != etape)
		{
			if (this.etape != null)
				this.etape.removeRencontre(this);
			this.etape = etape;
			if (this.etape != null)
				etape.addRencontre(this);
		}
	}
	
	public int getNumEtape()
	{
		return etape.getNum();
	}
	
	Rencontre(int num, String lieu, String date, Etape etape) throws DataAccessException
	{
		setNum(num);
		setLieu(lieu);
		setDate(date);
		setEtape(etape);
	}

	public Rencontre(String lieu, String date, Etape etape) throws DataAccessException
	{
		this(NO_KEY, lieu, date, etape);
	}
	
	// Score

	public int getNbScore() throws DataAccessException 
	{
		loadAllScores();
		return scores.size();
	}

	public Score getScore(int index) throws DataAccessException
	{
		loadAllScores();
		return scores.get(index);
	}
	
	private boolean possedeScore(Score score) throws DataAccessException 
	{
		loadAllScores();
		return scores.contains(score);
	}
	
	private void loadAllScores() throws DataAccessException
	{
		if (!allScoresLoaded)
		{
			Root root = getEtape().getTournoi().getLigue().getRoot();
			root.loadAllScores(this);
			allScoresLoaded = true;
		}
	}
	
	public void addScore(Score score) throws DataAccessException 
	{
		loadAllScores();
		if(!possedeScore(score))
		{
			scores.add(score);
			score.setRencontre(this);
		}
	}

	public void removeScore(Score score) throws DataAccessException 
	{
		loadAllScores();
		if(possedeScore(score))
		{
			scores.remove(score);
			score.setEquipe(null);
		}
	}
}
