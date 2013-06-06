package metier;

public class Utilisateur
{
	private int num = NO_KEY;
	private Type type;
	private Club club;
	private String nom = null;
	private String prenom = null;
	private int telFixe = 0;
	private int telPortable = 0;
	private String mail = null;
	private String mdp = null;
	private final static int NO_KEY = -1;
	
	// GETTERS et SETTERS
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
	
	public String getNom()
	{
		return nom;
	}
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	public String getPrenom()
	{
		return prenom;
	}
	
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}
	
	public int getTelFixe()
	{
		return telFixe;
	}
	
	public void setTelFixe(int telFixe)
	{
		this.telFixe = telFixe;
	}
	
	public int getTelPortable()
	{
		return telPortable;
	}
	
	public void setTelPortable(int telPortable)
	{
		this.telPortable = telPortable;
	}
	
	public String getMail()
	{
		return mail;
	}
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}
	
	public String getMdp()
	{
		return mdp;
	}
	
	public void setMdp(String mdp)
	{
		this.mdp = mdp;
	}
	
	//
	public int getNumType()
	{
		return type.getNum();
	}
	
	public Type getType()
	{
		return type;
	}
	
	public void setType(Type type) throws DataAccessException
	{
		if (this.type != type)
		{
			if (this.type != null)
				this.type.removeUtilisateur(this);
			this.type = type;
			if (this.type != null)
				type.addUtilisateur(this);
		}
	}
	
	public Club getClub()
	{
		return club;
	}
	
	public void setClub(Club club) throws DataAccessException
	{
		if (this.club != club)
		{
			if (this.club != null)
				this.club.removeUtilisateur(this);
			this.club = club;
			if (this.club != null)
				club.addUtilisateur(this);
		}
	}

	Utilisateur(int num, String nom, String prenom, int telFixe, int telPortable, String mail, String mdp, Type type, Club club) throws DataAccessException
	{
		setNum(num);
		setNom(nom);
		setPrenom(prenom);
		setTelFixe(telFixe);
		setTelPortable(telPortable);
		setMail(mail);
		setMdp(mdp);
		setType(type);
		setClub(club);
	}

	public Utilisateur(String nom, String prenom, int telFixe, int telPortable, String mail, String mdp, Type type, Club club) throws DataAccessException
	{
		this(NO_KEY, nom, prenom, telFixe, telPortable, mail, mdp, type, club);
	}
}
