/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import model.Pacientes;
import connectionfactory.FabricaConexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao_vitor
 */
public class PacienteDAO {

    public void close(Connection conn, Statement st) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (st != null) {
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro de " + ex.getMessage());
        }
    }

    public Long salvar(Pacientes paciente) {
        String sql = "INSERT INTO pacientes (nome,cpf,data_nascimento,endereco,email,celular,observacao) VALUES (?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        Long ultimoId = -1L;
        try {
            conn = FabricaConexao.getConexao();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setDate(3, new java.sql.Date(paciente.getData_Nascimento().getTime()));
            stmt.setString(4, paciente.getEndereco());
            stmt.setString(5, paciente.getEmail());
            stmt.setString(6, paciente.getCelular());
            stmt.setString(7, paciente.getObservacao());
            stmt.execute();
            ResultSet resultadoId = stmt.getGeneratedKeys();
            while (resultadoId.next()) {
                ultimoId = resultadoId.getLong(1);
            }
            return ultimoId;
        } catch (SQLException e) {
            System.out.println("Erro de " + e.getMessage());
            return ultimoId;
        } finally {
            close(conn, stmt);
        }
    }

    public void alterar(Pacientes paciente) {
        String sql = "UPDATE pacientes SET nome = ?, cpf = ?, data_nascimento = ?, endereco = ?, email = ?, celular = ?, observacao = ? WHERE SISO = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = FabricaConexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setDate(3, new java.sql.Date(paciente.getData_Nascimento().getTime()));
            stmt.setString(4, paciente.getEndereco());
            stmt.setString(5, paciente.getEmail());
            stmt.setString(6, paciente.getCelular());
            stmt.setString(7, paciente.getObservacao());
            stmt.setInt(8, paciente.getSiso());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro de " + e.getMessage());
        } finally {
            close(conn, stmt);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pacientes WHERE siso = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = FabricaConexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro de " + e.getMessage());
        } finally {
            close(conn, stmt);
        }
    }

    public List<Pacientes> listaTodos() {
        String sql = "SELECT * FROM pacientes";
        List<Pacientes> lista = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        try {
            conn = FabricaConexao.getConexao();
            st = conn.createStatement();
            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                Pacientes paciente = new Pacientes();
                paciente.setSiso(resultado.getInt("siso"));
                paciente.setNome(resultado.getString("nome"));
                paciente.setCpf(resultado.getString("cpf"));
                paciente.setData_Nascimento(resultado.getDate("data_nascimento"));
                paciente.setEndereco(resultado.getString("endereco"));
                paciente.setEmail(resultado.getString("email"));
                paciente.setCelular(resultado.getString("celular"));
                paciente.setObservacao(resultado.getString("observacao"));
                lista.add(paciente);
            }
        } catch (SQLException e) {
            System.out.println("Erro de " + e.getMessage());
        } finally {
            close(conn, st);
        }
        return lista;
    }
}
