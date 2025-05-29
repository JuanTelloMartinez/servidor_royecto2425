package es.ieslavereda.proyecto2425_servidor.repository;

import es.ieslavereda.proyecto2425_servidor.connection.MyDataSource;
import es.ieslavereda.proyecto2425_servidor.repository.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository implements ICRUDUsuario{

    @Autowired
    @Qualifier("mysqlDataSource")
    private DataSource dataSource;


    @Override
    public List<Usuario> getAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = MyDataSource.conectarMySQL();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("select * from  usuario")){
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario getbyID(int id) throws SQLException {
        Usuario usuario = null;

        String query = "select * from usuario where idUsuario = ?";

        try (Connection connection = MyDataSource.conectarMySQL();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                usuario = new Usuario(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4)
                );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public Usuario deleteUser(int id) throws SQLException {
        Usuario usuario = getbyID(id);

        String query = "delete from usuario where idUsuario = ?";

        try (Connection connection = MyDataSource.conectarMySQL();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public Usuario addUser(Usuario usuario) throws SQLException {
        String query = "insert into usuario(nombre, apellidos, Oficio_idOficio)" +
                       "values(?, ?, ?);";

        try (Connection connection = MyDataSource.conectarMySQL();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setDouble(3, usuario.getOficio_idOficio());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public Usuario updateUser(Usuario usuario) throws SQLException {
        String query = "update usuario set nombre = ?, apellidos = ?, Oficio_idOficio = ? where idUsuario = ?";

        try (Connection connection = MyDataSource.conectarMySQL();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setDouble(3, usuario.getOficio_idOficio());
            ps.setInt(4, usuario.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
