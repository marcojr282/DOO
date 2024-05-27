package co.edu.uco.pch.controller;


import java.util.UUID;

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
import co.edu.uco.pch.business.facade.impl.ciudad.ConsultarCiudadesFacade;
import co.edu.uco.pch.business.facade.impl.ciudad.RegistrarCiudadesFacade;
import co.edu.uco.pch.controller.response.CiudadResponse;
import co.edu.uco.pch.crosscutting.exceptions.PCHException;
import co.edu.uco.pch.dto.CiudadDTO;

@RestController
@RequestMapping("/api/v1/ciudades")
public class CiudadController {
	
	@GetMapping
	public CiudadDTO dummy() {
		return CiudadDTO.build();
	}
	
	
	
	@GetMapping
	public ResponseEntity<CiudadResponse> consultar(){
		var httpStatusCode = HttpStatus.ACCEPTED;
		var ciudadResponse = new CiudadResponse();
		try {
			var ciudadDto = CiudadDTO.build();			
			var facade = new ConsultarCiudadesFacade();
			
			ciudadResponse.setDatos(facade.excute(ciudadDto));
			ciudadResponse.getMensajes().add("Ciudades consultadas exitosamente");
			
		}catch (final PCHException excepcion) {
			httpStatusCode = HttpStatus.BAD_REQUEST;
			ciudadResponse.getMensajes().add(excepcion.getMensajeUsuario());			
		}catch ( final Exception excepcion) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			
			var mensajeUsuario = "Se ha presentado un problema tratando de consultar la informacion de la ciudades...";
			ciudadResponse.getMensajes().add(mensajeUsuario);
			
			excepcion.printStackTrace();
		}
		
		return new ResponseEntity<>(ciudadResponse, httpStatusCode);
	}
	
	@PostMapping
	public ResponseEntity<CiudadResponse> crear(@RequestBody CiudadDTO ciudad){
		var httpStatusCode = HttpStatus.ACCEPTED;
		var ciudadResponse = new CiudadResponse();
		try {
			
			var facade = new RegistrarCiudadesFacade();
			
			facade.execute(ciudad);
			ciudadResponse.getMensajes().add("Ciudades creada exitosamente");
			
		}catch (final PCHException excepcion) {
			httpStatusCode = HttpStatus.BAD_REQUEST;
			ciudadResponse.getMensajes().add(excepcion.getMensajeUsuario());			
		}catch ( final Exception excepcion) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			
			var mensajeUsuario = "Se ha presentado un problema tratando de registrar la informacion de la ciudades...";
			ciudadResponse.getMensajes().add(mensajeUsuario);
			
			excepcion.printStackTrace();
		}
		
		return new ResponseEntity<>(ciudadResponse, httpStatusCode);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CiudadResponse> eliminar(@PathVariable UUID id){
		var httpStatusCode = HttpStatus.ACCEPTED;
		var ciudadResponse = new CiudadResponse();
		try {
			
			
			
			//var facade = new RegistrarCiudadesFacade();
			
			//facade.execute(id);
			ciudadResponse.getMensajes().add("Ciudades creada exitosamente");
			
		}catch (final PCHException excepcion) {
			httpStatusCode = HttpStatus.BAD_REQUEST;
			ciudadResponse.getMensajes().add(excepcion.getMensajeUsuario());			
		}catch ( final Exception excepcion) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			
			var mensajeUsuario = "Se ha presentado un problema tratando de eliminar la informacion de la ciudades...";
			ciudadResponse.getMensajes().add(mensajeUsuario);
			
			excepcion.printStackTrace();
		}
		
		return new ResponseEntity<>(ciudadResponse, httpStatusCode);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CiudadResponse> Modificar(@PathVariable UUID id, @RequestBody CiudadDTO ciudadDto){
		var httpStatusCode = HttpStatus.ACCEPTED;
		var ciudadResponse = new CiudadResponse();
		try {
			ciudadDto.setId(id);
			//var facade = new RegistrarCiudadesFacade();
			
			//facade.execute(id);
			ciudadResponse.getMensajes().add("Ciudades creada exitosamente");
			
		}catch (final PCHException excepcion) {
			httpStatusCode = HttpStatus.BAD_REQUEST;
			ciudadResponse.getMensajes().add(excepcion.getMensajeUsuario());			
		}catch ( final Exception excepcion) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			
			var mensajeUsuario = "Se ha presentado un problema tratando de modificar la informacion de la ciudades...";
			ciudadResponse.getMensajes().add(mensajeUsuario);
			
			excepcion.printStackTrace();
		}
		
		return new ResponseEntity<>(ciudadResponse, httpStatusCode);
	}
}
