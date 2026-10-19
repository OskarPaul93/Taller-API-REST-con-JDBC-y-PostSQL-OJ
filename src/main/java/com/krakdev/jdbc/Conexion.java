package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Conexion {
	
	private static final Logger log = LogManager.getLogger(Conexion.class);
	private static final String URL = "jdbc:postgresql://localhost:5432/apijdbc";
	private static final String USER = "postgres";
	private static final String PASSWORD= "Pumpitup93";
	
	
	public static Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection(URL,USER, PASSWORD);
			log.info("Conexion Realizada");
			return con;

		} catch (SQLException e) {
			log.error("Error de conexion: "+ e.getMessage());

			//Para propagar la exception y otro lo resuelva, el que llame el metodo connection lo resolvera
			throw new RuntimeException ("No se pudo conectar", e);
			
		}
		
	}
	

}
