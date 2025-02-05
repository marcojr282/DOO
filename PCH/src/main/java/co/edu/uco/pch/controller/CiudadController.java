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
@RequestMapping("/api/V1/ciudades")
public final class CiudadController {
	
	


	@GetMapping("/dummy")
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
			
			ciudadResponse.setDatos(facade.execute(ciudadDto));
			ciudadResponse.getMensajes().add("ciudades consultadas exitosamente");
			
		}catch(final PCHException excepcion) {
			httpStatusCode = HttpStatus.BAD_REQUEST;
			ciudadResponse.getMensajes().add(excepcion.getMensajeUsuario());
			excepcion.printStackTrace();
		}catch(final Exception excepcion) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			
			var mensajeUsuario = "se ha presentado un prblema tratando de consultar";
			ciudadResponse.getMensajes().add(mensajeUsuario);
			
			excepcion.printStackTrace();
		}
		
		return new ResponseEntity<>(ciudadResponse,httpStatusCode);
		
		
	}
@PostMapping	
public ResponseEntity<CiudadResponse> crear(@RequestBody CiudadDTO ciudad){
		
		var httpStatusCode = HttpStatus.ACCEPTED;
		var ciudadResponse = new CiudadResponse();
		
		try {
			var facade = new RegistrarCiudadesFacade();
			facade.execute(ciudad);
			ciudadResponse.getMensajes().add("ciudades creada exitosamente");
			
		}catch(final PCHException excepcion) {
			httpStatusCode = HttpStatus.BAD_REQUEST;
			ciudadResponse.getMensajes().add(excepcion.getMensajeUsuario());
			excepcion.printStackTrace();
		}catch(final Exception excepcion) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			
			var mensajeUsuario = "se ha presentado un prblema tratando de registar la nueva ciudad";
			ciudadResponse.getMensajes().add(mensajeUsuario);
			
			excepcion.printStackTrace();
		}
		
		return new ResponseEntity<>(ciudadResponse,httpStatusCode);
		
		
	}


@DeleteMapping("/{id}")	
public ResponseEntity<CiudadResponse> eliminar(@PathVariable UUID  id){
		
		var httpStatusCode = HttpStatus.ACCEPTED;
		var ciudadResponse = new CiudadResponse();
		
		try {
			//var facade = new EliminarCiudadFacade();
			//facade.execute(id);
			ciudadResponse.getMensajes().add("ciudades eliminadas exitosamente");
			
		}catch(final PCHException excepcion) {
			httpStatusCode = HttpStatus.BAD_REQUEST;
			ciudadResponse.getMensajes().add(excepcion.getMensajeUsuario());
			excepcion.printStackTrace();
		}catch(final Exception excepcion) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			
			var mensajeUsuario = "se ha presentado un prblema tratando de eliminar la nueva ciudad";
			ciudadResponse.getMensajes().add(mensajeUsuario);
			
			excepcion.printStackTrace();
		}
		
		return new ResponseEntity<>(ciudadResponse,httpStatusCode);
		
		
	}

@PutMapping("/{id}")
public ResponseEntity<CiudadResponse> modificar(@PathVariable UUID  id,@RequestBody CiudadDTO ciudadDTO){
	
	var httpStatusCode = HttpStatus.ACCEPTED;
	var ciudadResponse = new CiudadResponse();
	
	try {
		
		ciudadDTO.setId(id);
		//var facade = new EliminarCiudadFacade();
		//facade.execute(id);
		ciudadResponse.getMensajes().add("ciudades modificadas exitosamente");
		
	}catch(final PCHException excepcion) {
		httpStatusCode = HttpStatus.BAD_REQUEST;
		ciudadResponse.getMensajes().add(excepcion.getMensajeUsuario());
		excepcion.printStackTrace();
	}catch(final Exception excepcion) {
		httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		
		var mensajeUsuario = "se ha presentado un prblema tratando de modificar la nueva ciudad";
		ciudadResponse.getMensajes().add(mensajeUsuario);
		
		excepcion.printStackTrace();
	}
	
	return new ResponseEntity<>(ciudadResponse,httpStatusCode);
	
	
}


}