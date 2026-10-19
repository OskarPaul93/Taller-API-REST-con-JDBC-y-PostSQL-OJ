package com.krakdev.jdbc.videojuegos.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.krakdev.jdbc.videojuegos.VideojuegoJdbc;
import com.krakdev.videojuegos.entidades.Videojuego;

@Service
public class ServicioVideojuegoJdbc {

    private static final Logger LOG = LogManager.getLogger(ServicioVideojuegoJdbc.class);

    private final VideojuegoJdbc videojuegoJdbc;

    // Constructor
    public ServicioVideojuegoJdbc(VideojuegoJdbc videojuegoJdbc) {
        this.videojuegoJdbc = videojuegoJdbc;
    }

    // METODO CREAR
    public Videojuego crear(Videojuego videojuego) {
        LOG.info("Servicio crear videojuego");

        try {
            Videojuego resultado = videojuegoJdbc.insertar(videojuego);

            if (resultado != null) {
                LOG.info("Videojuego creado correctamente");
            } else {
                LOG.warn("No se pudo crear el videojuego");
            }

            return resultado;

        } catch (RuntimeException e) {
            LOG.error("Error al crear el videojuego", e);
            throw e;
        }
    }

    // METODO LISTAR
    public List<Videojuego> listar() {
        LOG.info("Servicio listar videojuegos");

        try {
            return videojuegoJdbc.listar();

        } catch (RuntimeException e) {
            LOG.error("Error al listar los videojuegos", e);
            throw e;
        }
    }

    // METODO BUSCAR POR CODIGO
    public Videojuego buscarPorCodigo(String codigo) {
        LOG.info("Servicio buscar videojuego por codigo: {}", codigo);

        try {
            return videojuegoJdbc.buscar(codigo);

        } catch (RuntimeException e) {
            LOG.error("Error al buscar el videojuego", e);
            throw e;
        }
    }

    // METODO ACTUALIZAR
    public Videojuego actualizar(Videojuego videojuego) {
        LOG.info("Servicio actualizar videojuego: {}",
                videojuego.getCodigo());

        try {
            Videojuego resultado = videojuegoJdbc.actualizar(videojuego);

            if (resultado != null) {
                LOG.info("Videojuego actualizado correctamente");
            } else {
                LOG.warn("No se pudo actualizar el videojuego");
            }

            return resultado;

        } catch (RuntimeException e) {
            LOG.error("Error al actualizar el videojuego", e);
            throw e;
        }
    }

    // METODO ELIMINAR
    public boolean eliminar(String codigo) {
        LOG.info("Servicio eliminar videojuego: {}", codigo);

        try {
            boolean eliminado = videojuegoJdbc.eliminar(codigo);

            if (eliminado) {
                LOG.info("Videojuego eliminado correctamente");
            } else {
                LOG.warn("No se encontro el videojuego para eliminar");
            }

            return eliminado;

        } catch (RuntimeException e) {
            LOG.error("Error al eliminar el videojuego", e);
            throw e;
        }
    }
}
