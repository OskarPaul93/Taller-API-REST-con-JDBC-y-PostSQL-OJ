package com.krakdev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.krakdev.videojuegos.entidades.Videojuego;

@Repository
public class VideojuegoJdbc {

    private static final Logger LOG =
            LogManager.getLogger(VideojuegoJdbc.class);

    private final DataSource dataSource;

    // Constructor
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
                LOG.info("Videojuego insertado correctamente");
                return videojuego;
            }

            LOG.warn("No se pudo insertar el videojuego");
            return null;

        } catch (SQLException e) {
            LOG.error("Error al insertar videojuego", e);
            return null;

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                LOG.error("Error al cerrar recursos de insercion", e);
            }
        }
    }

    // METODO LISTAR
    public List<Videojuego> listar() {

        List<Videojuego> videojuegos = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT codigo, nombre, plataforma, precio, "
                + "disponible, genero FROM videojuegos";

        try {
            con = dataSource.getConnection();
            LOG.info("Conexion realizada");

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                Videojuego videojuego = new Videojuego();

                videojuego.setCodigo(rs.getString("codigo"));
                videojuego.setNombre(rs.getString("nombre"));
                videojuego.setPlataforma(rs.getString("plataforma"));
                videojuego.setPrecio(rs.getDouble("precio"));
                videojuego.setDisponible(rs.getBoolean("disponible"));
                videojuego.setGenero(rs.getString("genero"));

                videojuegos.add(videojuego);
            }

            LOG.info("Videojuegos consultados: {}", videojuegos.size());

        } catch (SQLException e) {
            LOG.error("Error al listar videojuegos", e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                LOG.error("Error al cerrar recursos de consulta", e);
            }
        }

        return videojuegos;
    }

    // METODO BUSCAR POR CODIGO
    public Videojuego buscar(String codigo) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Videojuego videojuego = null;

        String sql = "SELECT codigo, nombre, plataforma, precio, "
                + "disponible, genero FROM videojuegos WHERE codigo = ?";

        try {
            con = dataSource.getConnection();
            LOG.info("Conexion realizada");

            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);

            rs = ps.executeQuery();

            if (rs.next()) {

                videojuego = new Videojuego();

                videojuego.setCodigo(rs.getString("codigo"));
                videojuego.setNombre(rs.getString("nombre"));
                videojuego.setPlataforma(rs.getString("plataforma"));
                videojuego.setPrecio(rs.getDouble("precio"));
                videojuego.setDisponible(rs.getBoolean("disponible"));
                videojuego.setGenero(rs.getString("genero"));

                LOG.info("Videojuego encontrado: {}", codigo);

            } else {
                LOG.warn("No se encontro el videojuego: {}", codigo);
            }

        } catch (SQLException e) {
            LOG.error("Error al buscar videojuego", e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                LOG.error("Error al cerrar recursos de busqueda", e);
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
                LOG.info("Videojuego actualizado: {}",
                        videojuego.getCodigo());
                return videojuego;
            }

            LOG.warn("No se encontro el videojuego para actualizar: {}",
                    videojuego.getCodigo());
            return null;

        } catch (SQLException e) {
            LOG.error("Error al actualizar videojuego", e);
            return null;

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                LOG.error("Error al cerrar recursos de actualizacion", e);
            }
        }
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
                LOG.info("Videojuego eliminado: {}", codigo);
                return true;
            }

            LOG.warn("No se encontro el videojuego para eliminar: {}",
                    codigo);
            return false;

        } catch (SQLException e) {
            LOG.error("Error al eliminar videojuego", e);
            return false;

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                LOG.error("Error al cerrar recursos de eliminacion", e);
            }
        }
    }
}
