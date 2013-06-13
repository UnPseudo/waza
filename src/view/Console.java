package view;

import java.util.Scanner; 

import metier.*;

public class Console {

	private static Root root;
	public static int saisieInt()
	{
		Scanner sc = new Scanner(System.in); 
		System.out.println(""); 
		return sc.nextInt(); 
	}
	
	public static String saisieStr()
	{
		Scanner sc = new Scanner(System.in); 
		System.out.println("Saisie :"); 
		return sc.next(); 
	}
	
	public static void menuPrincipal() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-Ajouter"); 
		System.out.println("2-Modifier / Afficher");
		System.out.println("3-Supprimer");
		int rep= saisieInt();
		if (rep == 1)
			menuAjouter();
		else if (rep == 2)
			menuModifier();
		else if (rep == 3)
			menuSupprimer();
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuAjouter() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-Ajouter Type");
		System.out.println("2-Ajouter Ligue");
		System.out.println("3-Ajouter Club");
		System.out.println("4-Ajouter Categorie");
		System.out.println("5-Ajouter Equipe");
		System.out.println("6-Ajouter Utilisateur");
		System.out.println("7-Ajouter Tournoi");
		System.out.println("8-Ajouter Etape");
		System.out.println("9-Ajouter Rencontre");
		System.out.println("10-Ajouter Appartenance");
		System.out.println("11-Ajouter Inscription");
		System.out.println("12-Ajouter Score");
		int rep= saisieInt();
		if (rep == 0)
			menuPrincipal();
		else if (rep == 1)
			menuAjouterType();
		else if (rep == 2)
			menuAjouterLigue();
		else if (rep == 3)
			menuAjouterClub();
		else if (rep == 4)
			menuAjouterCategorie();
		else if (rep == 5)
			menuAjouterEquipe();
		else if (rep == 6)
			menuAjouterUtilisateur();
		else if (rep == 7)
			menuAjouterTournoi();
		else if (rep == 8)
			menuAjouterEtape();
		else if (rep == 9)
			menuAjouterRencontre();
		else if (rep == 10)
			menuAjouterAppartenance();
		else if (rep == 11)
			menuAjouterInscription();
		else if (rep == 12)
			menuAjouterScore();
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuAjouterType() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("description:");
		String description= saisieStr();
		//Root root= new Root();
		Type type = new Type(root, nom, description);
		root.save(type);
		System.out.println("Type ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterLigue() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("description:");
		String description= saisieStr();
		//Root root= new Root();
		Ligue ligue = new Ligue(root, nom, description);
		root.save(ligue);
		System.out.println("Ligue ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterClub() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("description:");
		String description= saisieStr();
		System.out.println("ligue_id:");
		int idLigue= saisieInt();
		Club club = new Club(nom, description, root.loadLigue(idLigue));
		root.save(club);
		System.out.println("Club ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterCategorie() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("description:");
		String description= saisieStr();
		System.out.println("ligue_id:");
		int idLigue= saisieInt();
		Categorie categorie = new Categorie(nom, description, root.loadLigue(idLigue));
		root.save(categorie);
		System.out.println("Categorie ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterEquipe() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("club_id:");
		int idClub= saisieInt();
		System.out.println("categorie_id:");
		int idCategorie= saisieInt();
		Equipe equipe = new Equipe(nom, root.loadCategorie(idCategorie), root.loadClub(idClub));
		root.save(equipe);
		System.out.println("Equipe ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterUtilisateur() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("prenom:");
		String prenom= saisieStr();
		System.out.println("mail:");
		String mail= saisieStr();
		System.out.println("mdp:");
		String mdp= saisieStr();
		System.out.println("tel fixe:");
		int telFixe= saisieInt();
		System.out.println("tel portable:");
		int telPortable= saisieInt();
		System.out.println("type_id:");
		int idType= saisieInt();
		System.out.println("club_id:");
		int idClub= saisieInt();
		Utilisateur utilisateur = new Utilisateur(nom, prenom, telFixe, telPortable, mail, mdp, root.loadType(idType), root.loadClub(idClub));
		root.save(utilisateur);
		System.out.println("Utilisateur ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterTournoi() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("nom:");
		String nom= saisieStr();
		System.out.println("description:");
		String description= saisieStr();
		System.out.println("ligue_id:");
		int idLigue= saisieInt();
		System.out.println("categorie_id:");
		int idCategorie= saisieInt();
		Tournoi tournoi = new Tournoi(nom, description, root.loadLigue(idLigue), root.loadCategorie(idCategorie));
		root.save(tournoi);
		System.out.println("Tournoi ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterEtape() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("tournoi_id:");
		int idTournoi= saisieInt();
		Etape etape = new Etape(root.loadTournoi(idTournoi));
		root.save(etape);
		System.out.println("Etape ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterRencontre() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("lieu:");
		String lieu= saisieStr();
		System.out.println("date:");
		String date= saisieStr();
		System.out.println("etape_id:");
		int idEtape= saisieInt();
		Rencontre rencontre = new Rencontre(lieu, date, root.loadEtape(idEtape));
		root.save(rencontre);
		System.out.println("Rencontre ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterAppartenance() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("utilisateur_id:");
		int idUtilisateur= saisieInt();
		System.out.println("equipe_id:");
		int idEquipe= saisieInt();
		Appartenance appartenance = new Appartenance(root.loadUtilisateur(idUtilisateur), root.loadEquipe(idEquipe));
		root.save(appartenance);
		System.out.println("Appartenance ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterInscription() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("equipe_id:");
		int idEquipe= saisieInt();
		System.out.println("tournoi_id:");
		int idTournoi= saisieInt();
		Inscription inscription = new Inscription(root.loadEquipe(idEquipe), root.loadTournoi(idTournoi));
		root.save(inscription);
		System.out.println("Inscription ajouté");
		menuAjouter();
	}
	
	public static void menuAjouterScore() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("equipe_id:");
		int idEquipe= saisieInt();
		System.out.println("rencontre_id:");
		int idRencontre= saisieInt();
		System.out.println("points:");
		int points= saisieInt();
		System.out.println("publie:");
		int i= saisieInt();
		boolean publie = true;
		if (i==0){publie=false;} 
		Score score = new Score(root.loadRencontre(idRencontre), root.loadEquipe(idEquipe), points, publie);
		root.save(score);
		System.out.println("Score ajouté");
		menuAjouter();
	}
	
	public static void menuModifier() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-Modifier Type");
		System.out.println("2-Modifier Ligue");
		System.out.println("3-Modifier Club");
		System.out.println("4-Modifier Categorie");
		System.out.println("5-Modifier Equipe");
		System.out.println("6-Modifier Utilisateur");
		System.out.println("7-Modifier Tournoi");
		System.out.println("8-Modifier Etape");
		System.out.println("9-Modifier Rencontre");
		//System.out.println("3-Modifier Score");
		
		int rep= saisieInt();
		if (rep == 0)
			menuPrincipal();
		else if (rep == 1)
			menuModifierType();
		else if (rep == 2)
			menuModifierLigue();
		else if (rep == 3)
			menuModifierClub();
		else if (rep == 4)
			menuModifierCategorie();
		else if (rep == 5)
			menuModifierEquipe();
		else if (rep == 6)
			menuModifierUtilisateur();
		else if (rep == 7)
			menuModifierTournoi();
		else if (rep == 8)
			menuModifierEtape();
		else if (rep == 9)
			menuModifierRencontre();
		else if (rep == 10)
			menuModifierScore();
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuModifierType() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id a modifier:");
		int id= saisieInt();
		Type type = root.loadType(id);
		System.out.println(type.getNum());
		System.out.println(type.getNom());
		System.out.println(type.getDescription());
		
		System.out.println("1-Modifier Type Nom");
		System.out.println("2-Modifier Type Description");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			type.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			type.setDescription(str);
		}
		else
		{
			System.out.println("erreur");
			menuModifierType();
		}
		root.save(type);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierLigue() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Ligue a modifier:");
		int id= saisieInt();
		Ligue ligue = root.loadLigue(id);
		System.out.println(ligue.getNum());
		System.out.println(ligue.getNom());
		System.out.println(ligue.getDescription());
		
		System.out.println("1-Modifier Ligue Nom");
		System.out.println("2-Modifier Ligue Description");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			ligue.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			ligue.setDescription(str);
		}
		else
		{
			System.out.println("erreur");
			menuModifierLigue();
		}
		root.save(ligue);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierClub() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Club a modifier:");
		int id= saisieInt();
		Club club = root.loadClub(id);
		System.out.println("id: " + club.getNum());
		System.out.println("nom: " + club.getNom());
		System.out.println("description: " + club.getDescription());
		System.out.println("ligue_id: " + club.getNumLigue());
		
		System.out.println("1-Modifier Club Nom");
		System.out.println("2-Modifier Club Description");
		System.out.println("3-Modifier Club Ligue_id");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			club.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			club.setDescription(str);
		}
		else if (rep == 3)
		{
			System.out.println("Nouvelle Ligue_id:");
			int i= saisieInt();
			club.setLigue(root.loadLigue(i));
		}
		else
		{
			System.out.println("erreur");
			menuModifierClub();
		}
		root.save(club);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierCategorie() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Categorie a modifier:");
		int id= saisieInt();
		Categorie categorie = root.loadCategorie(id);
		System.out.println("id: " + categorie.getNum());
		System.out.println("nom: " + categorie.getNom());
		System.out.println("description: " + categorie.getDescription());
		System.out.println("ligue_id: " + categorie.getNumLigue());
		
		System.out.println("1-Modifier Categorie Nom");
		System.out.println("2-Modifier Catgorie Description");
		System.out.println("3-Modifier Categorie Ligue_id");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			categorie.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			categorie.setDescription(str);
		}
		else if (rep == 3)
		{
			System.out.println("Nouvelle Ligue_id:");
			int i= saisieInt();
			categorie.setLigue(root.loadLigue(i));
		}
		else
		{
			System.out.println("erreur");
			menuModifierCategorie();
		}
		root.save(categorie);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierUtilisateur() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Utilisateur a modifier:");
		int id= saisieInt();
		Utilisateur utilisateur = root.loadUtilisateur(id);
		System.out.println("id: " + utilisateur.getNum());
		System.out.println("nom: " + utilisateur.getNom());
		System.out.println("prenom: " + utilisateur.getPrenom());
		System.out.println("tel fixe: " + utilisateur.getTelFixe());
		System.out.println("tel portable: " + utilisateur.getTelPortable());
		System.out.println("mail: " + utilisateur.getMail());
		System.out.println("mdp: " + utilisateur.getMdp());
		System.out.println("type_id: " + utilisateur.getNumType());
		System.out.println("club_id: " + utilisateur.getNumClub());
		
		System.out.println("1-Modifier Utilisateur Nom " );
		System.out.println("2-Modifier Utilisateur prenom");
		System.out.println("3-Modifier Utilisateur tel fixe");
		System.out.println("4-Modifier Utilisateur tel portable");
		System.out.println("5-Modifier Utilisateur mail");
		System.out.println("6-Modifier Utilisateur mdp");
		System.out.println("7-Modifier Utilisateur type_id");
		System.out.println("8-Modifier Utilisateur club_id");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom : ");
			String str= saisieStr();
			utilisateur.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouveau prenom : ");
			String str= saisieStr();
			utilisateur.setPrenom(str);
		}
		else if (rep == 3)
		{
			System.out.println("Nouveau tel fixe");
			int i= saisieInt();
			utilisateur.setTelFixe(i);
		}
		else if (rep == 4)
		{
			System.out.println("Nouveau Tel portable");
			int i= saisieInt();
			utilisateur.setTelPortable(i);
		}
		else if (rep == 5)
		{
			System.out.println("Nouveau mail");
			String str= saisieStr();
			utilisateur.setMail(str);
		}
		else if (rep == 6)
		{
			System.out.println("Nouveau mdp");
			String str= saisieStr();
			utilisateur.setMdp(str);
		}
		else if (rep == 7)
		{
			System.out.println("Nouveau type_id:");
			int i= saisieInt();
			utilisateur.setType(root.loadType(i));
		}
		else if (rep == 8)
		{
			System.out.println("Nouveau Club_id:");
			int i= saisieInt();
			utilisateur.setClub(root.loadClub(i));
		}
		else
		{
			System.out.println("erreur");
			menuModifierUtilisateur();
		}
		root.save(utilisateur);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierEquipe() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Equipe a modifier:");
		int id= saisieInt();
		Equipe equipe = root.loadEquipe(id);
		System.out.println(equipe.getNum());
		System.out.println(equipe.getNom());
		System.out.println("club_id: " + equipe.getNumClub());
		System.out.println("categorie_id: " + equipe.getNumCategorie());
		
		System.out.println("1-Modifier Equipe Nom");
		System.out.println("2-Modifier Equipe club_id");
		System.out.println("3-Modifier Equipe categorie_id");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			equipe.setNom(str);
		}
		else if (rep == 2)
		{
			System.out.println("Nouveau club_id:");
			int i = saisieInt();
			equipe.setClub(root.loadClub(i));;
		}
		else if (rep == 3)
		{
			System.out.println("Nouveau categorie_id:");
			int i = saisieInt();
			equipe.setCategorie(root.loadCategorie(i));;
		}
		else
		{
			System.out.println("erreur");
			menuModifierEquipe();
		}
		root.save(equipe);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierTournoi() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Tournoi a modifier:");
		int id= saisieInt();
		Tournoi tournoi = root.loadTournoi(id);
		System.out.println(tournoi.getNum());
		System.out.println(tournoi.getNom());
		System.out.println(tournoi.getDescription());
		System.out.println("ligue_id: " + tournoi.getNumLigue());
		System.out.println("categorie_id: " + tournoi.getNumCategorie());
		
		System.out.println("1-Modifier Tournoi nom");
		System.out.println("2-Modifier Tournoi description");
		System.out.println("3-Modifier Tournoi ligue_id");
		System.out.println("4-Modifier Tournoi categorie_id");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau nom:");
			String str= saisieStr();
			tournoi.setNom(str);
		}
		else if(rep == 2)
		{
			System.out.println("Nouvelle description:");
			String str= saisieStr();
			tournoi.setDescription(str);
		}
		else if (rep == 3)
		{
			System.out.println("Nouveau ligue_id:");
			int i = saisieInt();
			tournoi.setLigue(root.loadLigue(i));;
		}
		else if (rep == 4)
		{
			System.out.println("Nouveau categorie_id:");
			int i = saisieInt();
			tournoi.setCategorie(root.loadCategorie(i));;
		}
		else
		{
			System.out.println("erreur");
			menuModifierTournoi();
		}
		root.save(tournoi);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierEtape() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Etape a modifier:");
		int id= saisieInt();
		Etape etape= root.loadEtape(id);
		System.out.println("nom : " + etape.getNum());
		System.out.println("ligue_id : " + etape.getNumTournoi());
	
		System.out.println("1-Modifier Etape tournoi_id");
		int rep= saisieInt();
		if (rep == 1)
		{
			System.out.println("Nouveau tournoi_id:");
			int i = saisieInt();
			etape.setTournoi(root.loadTournoi(i));;
		}
		else
		{
			System.out.println("erreur");
			menuModifierEtape();
		}
		root.save(etape);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierRencontre() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Rencontre a modifier:");
		int id= saisieInt();
		Rencontre rencontre = root.loadRencontre(id);
		System.out.println("id : " + rencontre.getNum());
		System.out.println("date : "+ rencontre.getDate());
		System.out.println("lieu : " + rencontre.getLieu());
		System.out.println("etape_id: " + rencontre.getNumEtape());
		
		System.out.println("1-Modifier Rencontre date");
		System.out.println("2-Modifier Rencontre lieu");
		System.out.println("3-Modifier Rencontre etape_id");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouvelle date :");
			String str= saisieStr();
			rencontre.setDate(str);
		}
		else if(rep == 2)
		{
			System.out.println("Nouveau lieu :");
			String str= saisieStr();
			rencontre.setLieu(str);
		}
		else if (rep == 3)
		{
			System.out.println("Nouveau etape_id : ");
			int i = saisieInt();
			rencontre.setEtape(root.loadEtape(i));
		}
		else
		{
			System.out.println("erreur");
			menuModifierRencontre();
		}
		root.save(rencontre);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuModifierScore() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id equipe_id Score a modifier:");
		int idEquipe= saisieInt();
		System.out.println("id rencontre_id Score a modifier:");
		int idRencontre= saisieInt();
		int idScore = root.loadIdScore(idEquipe, idRencontre);
		Score score = root.loadScore(idScore);
		
		System.out.println("id : " + score.getNum());
		System.out.println("equipe_id: " + score.getNumEquipe());;
		System.out.println("rencontre_id: " + score.getNumRencontre());
		
		System.out.println("1-Modifier Score points");
		System.out.println("2-Modifier Score publie");
		int rep= saisieInt();
		if(rep == 1)
		{
			System.out.println("Nouveau points :");
			int i= saisieInt();
			score.setPoints(i);
		}
		else if(rep == 2)
		{
			System.out.println("Nouveau publie :");
			int i= saisieInt();
			boolean b = true;
			if (i == 0)
				b = false;
			score.setPublie(b);
		}
		else
		{
			System.out.println("erreur");
			menuModifierScore();
		}
		root.save(score);
		System.out.println("Modification prise en compte");
		menuModifier();
	}
	
	public static void menuSupprimer() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("1-Supprimer Type");
		System.out.println("2-Supprimer Ligue");
		System.out.println("3-Supprimer Club");
		System.out.println("4-Supprimer Categorie");
		System.out.println("5-Supprimer Equipe");
		System.out.println("6-Supprimer Utilisateur");
		System.out.println("7-Supprimer Tournoi");
		System.out.println("8-Supprimer Etape");
		System.out.println("9-Supprimer Rencontre");
		System.out.println("10-Supprimer Appartenance");
		System.out.println("11-Supprimer Inscription");
		System.out.println("12-Supprimer Score");
		int rep= saisieInt();
		if (rep == 0)
			menuPrincipal();
		else if (rep == 1)
			menuSupprimerType();
		else if (rep == 2)
			menuSupprimerLigue();
		else if (rep == 3)
			menuSupprimerClub();
		else if (rep == 4)
			menuSupprimerCategorie();
		else if (rep == 5)
			menuSupprimerEquipe();
		else if (rep == 6)
			menuSupprimerUtilisateur();
		else if (rep == 7)
			menuSupprimerTournoi();
		else if (rep == 8)
			menuSupprimerEtape();
		else if (rep == 9)
			menuSupprimerRencontre();
		else if (rep == 10)
			menuSupprimerAppartenance();
		else if (rep == 11)
			menuSupprimerInscription();
		else if (rep == 12)
			menuSupprimerScore();
		else
		{
			System.out.println("erreur");
			menuPrincipal();
		}
	}
	
	public static void menuSupprimerType() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Type a supprimer:");
		int id= saisieInt();
		Type type = root.loadType(id);
		root.delete(type);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerLigue() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Ligue a supprimer:");
		int id= saisieInt();
		Ligue ligue = root.loadLigue(id);
		root.delete(ligue);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerClub() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Club a supprimer:");
		int id= saisieInt();
		Club club = root.loadClub(id);
		root.delete(club);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerCategorie() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Categorie a supprimer:");
		int id= saisieInt();
		Categorie categorie= root.loadCategorie(id);
		root.delete(categorie);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerEquipe() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Equipe a supprimer:");
		int id= saisieInt();
		Equipe equipe = root.loadEquipe(id);
		root.delete(equipe);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerUtilisateur() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Utilisateur a supprimer:");
		int id= saisieInt();
		Utilisateur utilisateur = root.loadUtilisateur(id);
		root.delete(utilisateur);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerTournoi() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Tournoi a supprimer:");
		int id= saisieInt();
		Tournoi tournoi = root.loadTournoi(id);
		root.delete(tournoi);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerEtape() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Etape a supprimer:");
		int id= saisieInt();
		Etape etape = root.loadEtape(id);
		root.delete(etape);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerRencontre() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("id Rencontre a supprimer:");
		int id= saisieInt();
		Rencontre rencontre = root.loadRencontre(id);
		root.delete(rencontre);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerAppartenance() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("utilisateur_id Appartenance a supprimer:");
		int idUtilisateur= saisieInt();
		System.out.println("equipe_id Appartenance a supprimer:");
		int idEquipe= saisieInt();
		int idAppartenance = root.loadIdAppartenance (idUtilisateur, idEquipe);
		Appartenance appartenance = root.loadAppartenance(idAppartenance);
		root.delete(appartenance);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerInscription() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("equipe_id Inscription a supprimer:");
		int idEquipe= saisieInt();
		System.out.println("tournoi_id Inscription a supprimer:");
		int idTournoi= saisieInt();
		int idInscription = root.loadIdInscription (idEquipe, idTournoi);
		Inscription inscription = root.loadInscription(idInscription);
		root.delete(inscription);
		System.out.println("Suppression prise en compte");
	}
	
	public static void menuSupprimerScore() throws ClassNotFoundException, DataAccessException
	{
		System.out.println("equipe_id Scoree a supprimer:");
		int idEquipe= saisieInt();
		System.out.println("rencontre_id Score a supprimer:");
		int idRencontre= saisieInt();
		int idScore = root.loadIdScore (idEquipe, idRencontre);
		Score score = root.loadScore(idScore);
		root.delete(score);
		System.out.println("Suppression prise en compte");
	}
	
	
	Console() throws ClassNotFoundException, DataAccessException
	{
		root = new Root();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, DataAccessException
	{
		Console s = new Console();
		s.menuPrincipal();
	}
}