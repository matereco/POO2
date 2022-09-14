package swingProject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class generic {

	private Connection connection=null;
	private Statement statement=null;
	private ResultSet resultset=null;
	public Connection conectar()
	{ String retorno=null;
	String servidor="jdbc:mysql://127.0.0.1:3306/loja";
	String usuario="root";
	String senha="";
	String driver="com.mysql.cj.jdbc.Driver";
	try
	{ Class.forName(driver) ;
	this.connection=DriverManager.getConnection(servidor,usuario,senha);
	this.statement=this.connection.createStatement();
	}
	catch(Exception e)
	{ connection=null; }
	return connection;


	}
}


