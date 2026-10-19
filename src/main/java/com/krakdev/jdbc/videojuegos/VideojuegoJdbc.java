package com.krakdev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.krakdev.videojuegos.entidades.Videojuego;

@Repository
public class VideojuegoJdbc {

    private static final Logger LOG =
            Logger.getLogger(VideojuegoJdbc.class.getName());

    private final DataSource dataSource;

    public VideojuegoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // METODO INSERTAR
    public Videojuego insertar(Videojuego videojuego) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO videojuegos "
                + "(codigo, nombre, plataforma, precio, disponible, genero) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

       
        
        try {
            con = dataSource.getConnection();
            LOG.info("Conexion realizada");

            ps = con.prepareStatement(sql);

            ps.setString(1, videojuego.getCodigo());
            ps.setString(2, videojuego.getNombre());
            ps.setString(3, videojuego.getPlataforma());
            ps.setDouble(4, videojuego.getPrecio());
            ps.setBoolean(5, videojuego.isDisponible());
            ps.setString(6, videojuego.getGenero());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                LOG.info("Videojuego insertado");
                return videojuego;
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error al insertar videojuego", e);

        } finally {
            try {
                if (ps != null) {
                	ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error al cerrar recursos", e);
            }
        }

        return null;
    }

    // METODO LISTAR
    public List<Videojuego> listar() {

        List<Videojuego> videojuegos = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultado = null;

        String sql = "SELECT codigo, nombre, plataforma, precio, "
                + "disponible, genero FROM videojuegos";

        try {
            con = dataSource.getConnection();
            LOG.info("Conexion realizada");

            ps = con.prepareStatement(sql);
            resultado = ps.executeQuery();

            while (resultado.next()) {

                Videojuego videojuego = new Videojuego();

                videojuego.setCodigo(resultado.getString("codigo"));
                videojuego.setNombre(resultado.getString("nombre"));
                videojuego.setPlataforma(resultado.getString("plataforma"));
                videojuego.setPrecio(resultado.getDouble("precio"));
                videojuego.setDisponible(resultado.getBoolean("disponible"));
                videojuego.setGenero(resultado.getString("genero"));

                videojuegos.add(videojuego);
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error al listar videojuegos", e);

        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (ps != null) {
                	ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error al cerrar recursos", e);
            }
        }

        return videojuegos;
    }

    // METODO BUSCAR POR CODIGO
    public Videojuego buscar(String codigo) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultado = null;

        Videojuego videojuego = null;

        String sql = "SELECT codigo, nombre, plataforma, precio, "
                + "disponible, genero FROM videojuegos WHERE codigo = ?";

        try {
            con = dataSource.getConnection();
            LOG.info("Conexion realizada");

            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);

            resultado = ps.executeQuery();

            if (resultado.next()) {

                videojuego = new Videojuego();

                videojuego.setCodigo(resultado.getString("codigo"));
                videojuego.setNombre(resultado.getString("nombre"));
                videojuego.setPlataforma(resultado.getString("plataforma"));
                videojuego.setPrecio(resultado.getDouble("precio"));
                videojuego.setDisponible(resultado.getBoolean("disponible"));
                videojuego.setGenero(resultado.getString("genero"));
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error al buscar videojuego", e);

        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (ps != null) {
                	ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error al cerrar recursos", e);
            }
        }

        return videojuego;
    }

    // METODO ACTUALIZAR
    public Videojuego actualizar(Videojuego videojuego) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "UPDATE videojuegos SET nombre = ?, plataforma = ?, "
                + "precio = ?, disponible = ?, genero = ? WHERE codigo = ?";

        try {
            con = dataSource.getConnection();
            LOG.info("Conexion realizada");

            ps = con.prepareStatement(sql);

            ps.setString(1, videojuego.getNombre());
            ps.setString(2, videojuego.getPlataforma());
            ps.setDouble(3, videojuego.getPrecio());
            ps.setBoolean(4, videojuego.isDisponible());
            ps.setString(5, videojuego.getGenero());
            ps.setString(6, videojuego.getCodigo());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                LOG.info("Videojuego actualizado");
                return videojuego;
            } else {
                LOG.warning("No se encontro el videojuego para actualizar");
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error al actualizar videojuego", e);

        } finally {
            try {
                if (ps != null) {
                	ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error al cerrar recursos", e);
            }
        }

        return null;
    }

    // METODO ELIMINAR
    public boolean eliminar(String codigo) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM videojuegos WHERE codigo = ?";

        try {
            con = dataSource.getConnection();
            LOG.info("Conexion realizada");

            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                LOG.info("Videojuego eliminado");
                return true;
            } else {
                LOG.warning("No se encontro el videojuego para eliminar");
                return false;
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error al eliminar videojuego", e);

        } finally {
            try {
                if (ps != null) {
                	ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "Error al cerrar recursos", e);
            }
        }

        return false;
    }
}

