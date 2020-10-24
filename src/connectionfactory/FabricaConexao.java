/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionfactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joao_vitor
 */
public class FabricaConexao {
    
    public static Connection  getConexao(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db_dentista?verifyServerCertificate=false&useSSL=true";
            String usuario = "root";
            String senha = "root";
            return DriverManager.getConnection(url, usuario, senha);
        }catch(SQLException e ){
            System.out.println("Erro de " + e.getMessage());
        }
           catch (ClassNotFoundException ex) {
            Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
        } return conn;
    }
}
