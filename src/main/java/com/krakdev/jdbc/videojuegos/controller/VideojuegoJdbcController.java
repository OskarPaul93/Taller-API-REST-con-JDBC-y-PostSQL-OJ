package com.krakdev.jdbc.videojuegos.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakdev.jdbc.videojuegos.services.ServicioVideojuegoJdbc;
import com.krakdev.videojuegos.entidades.Videojuego;

@RestController
@RequestMapping("/jdbc/videojuegos")
public class VideojuegoJdbcController {

    private static final Logger LOG =
            LogManager.getLogger(VideojuegoJdbcController.class);

    private final ServicioVideojuegoJdbc servicioVideojuegoJdbc;

    // Constructor
    public VideojuegoJdbcController(
            ServicioVideojuegoJdbc servicioVideojuegoJdbc) {
        this.servicioVideojuegoJdbc = servicioVideojuegoJdbc;
    }

    // POST: Crear videojuego
    @PostMapping
    public ResponseEntity<Videojuego> crear(
            @RequestBody Videojuego videojuego) {

        LOG.info("Solicitud para crear videojuego");

        try {
            Videojuego resultado = servicioVideojuegoJdbc.crear(videojuego);

            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(resultado);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        } catch (RuntimeException e) {
            LOG.error("Error al crear videojuego", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // GET: Listar videojuegos
    @GetMapping
    public ResponseEntity<List<Videojuego>> listar() {

        LOG.info("Solicitud para listar videojuegos");

        try {
            List<Videojuego> videojuegos = servicioVideojuegoJdbc.listar();
            return ResponseEntity.ok(videojuegos);

        } catch (RuntimeException e) {
            LOG.error("Error al listar videojuegos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // GET: Buscar videojuego por codigo
    @GetMapping("/{codigo}")
    public ResponseEntity<Videojuego> buscarPorCodigo(
            @PathVariable String codigo) {

        LOG.info("Solicitud para buscar videojuego: {}", codigo);

        try {
            Videojuego videojuego =
                    servicioVideojuegoJdbc.buscarPorCodigo(codigo);

            if (videojuego != null) {
                return ResponseEntity.ok(videojuego);
            }

            return ResponseEntity.notFound().build();

        } catch (RuntimeException e) {
            LOG.error("Error al buscar videojuego", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // PUT: Actualizar videojuego
    @PutMapping("/{codigo}")
    public ResponseEntity<Videojuego> actualizar(
            @PathVariable String codigo,
            @RequestBody Videojuego videojuego) {

        LOG.info("Solicitud para actualizar videojuego: {}", codigo);

        try {
            // El codigo de la URL es el que se utiliza para actualizar
            videojuego.setCodigo(codigo);

            Videojuego resultado =
                    servicioVideojuegoJdbc.actualizar(videojuego);

            if (resultado != null) {
                return ResponseEntity.ok(resultado);
            }

            return ResponseEntity.notFound().build();

        } catch (RuntimeException e) {
            LOG.error("Error al actualizar videojuego", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // DELETE: Eliminar videojuego
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String codigo) {

        LOG.info("Solicitud para eliminar videojuego: {}", codigo);

        try {
            boolean eliminado =
                    servicioVideojuegoJdbc.eliminar(codigo);

            if (eliminado) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.notFound().build();

        } catch (RuntimeException e) {
            LOG.error("Error al eliminar videojuego", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
