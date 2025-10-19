package com.jmteamconsulting.sgps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Define la aplicación SGPS.
 * 
 * @author Ian P.
 */
@SpringBootApplication
public class SgpsApplication {
	/**
	 * Punto de entrada de la aplicación. Es el primer método ejecutado al correr la aplicación.
	 * @param args Argumentos de línea de comando.
	 */
	public static void main(String[] args) {
		// Inicia (ejecuta) la aplicación.
		SpringApplication.run(SgpsApplication.class, args);
	}

}
