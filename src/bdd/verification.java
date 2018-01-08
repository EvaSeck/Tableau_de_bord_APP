package bdd;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class verification
 */
@WebServlet("/verification")
public class verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Connection connexion;//connexion à la basse de données 
	public String driver;// pilote de la base de données 
	protected String url;// accès à la base de données 
	public ResultSet results=null; // l'objet qui récupére le résultat de la requete SQL 
	public PreparedStatement preparedstatement=null;//l'objet statement pour atttaquer la base de données
	public static String ide="";
	public static String mdp=""; 
	

    /**
     * Default constructor. 
     */
    public verification() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		// Gets a reference to an to a text-output stream
		PrintWriter out;
		out = response.getWriter();
		// Create the HTML page
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Vérifions vos identifiants</TITLE></HEAD>");
		out.println("<BODY>");
		String log=request.getParameter("login");
		String pass=request.getParameter("password");
		/* Load JDBC Driver */

		try {
			 Class.forName( "com.mysql.jdbc.Driver" );
			 // Class.forName("oracle.jdbc.OracleDriver") ;
			} catch ( ClassNotFoundException e ) {
			 e.printStackTrace();
			}

			//Connection to the database
  
			url = "jdbc:mysql://localhost:3306/projet?autoReconnect=true&useSSL=false";
			String user = "root";
			String pwd = "Shiloofmylife9";
			Connection connexion = null;
			try {
			 connexion = DriverManager.getConnection( url, user, pwd );
			 
			 /* Requests to bdd will be here */
			 
			 PreparedStatement preparedStatement = connexion.prepareStatement(
					 "SELECT login, mdp  FROM utilisateur WHERE login = ? AND mdp = ?" );
			 preparedStatement.setString( 1, log);
			 preparedStatement.setString( 2, pass );
			 /*ResultSet */ results = preparedStatement.executeQuery();
			 
			 System.out.println("Bdd Connected" );
			 
			 if( log!="" & pass!= ""){ // Il prend en compte l'espace
					if(results!=null){
							try {
								while(results.next()){
									if(results.getString("login").equals(log) && results.getString("mdp").equals(pass)){
										out.println("Connexion réussie");
											}
									
									
									else{
										out.println("Identifiants non existants");
									}
									}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
			 }
			else{
					out.println("Identifiants absents");
				}
			 results.close();
			 preparedstatement.close();
			 
			}catch ( SQLException e ) {
				e.printStackTrace();
				} finally {
				 if ( connexion != null )
				 try {
				 connexion.close();
				 } catch ( SQLException ignore ) {
				ignore.printStackTrace();
				 }
				}
			
			
			 
			 
			
					

			
						
				
				
				

		out.println("</BODY></HTML>");
		
		//close the output stream
		out.close();
		
		

			}
			
	}
 
		



//Ajouter la partie des règles métier
/*
private void validationEmail( String email ) throws Exception {
    if ( email != null && email.trim().length() != 0 ) {
        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    } else {
        throw new Exception( "Merci de saisir une adresse mail." );
    }
}

/**
 * Valide les mots de passe saisis.

private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception{
    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
        if (!motDePasse.equals(confirmation)) {
            throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
        } else if (motDePasse.trim().length() < 3) {
            throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
        }
    } else {
        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
    }
}

*/

		