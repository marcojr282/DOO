package co.edu.uco.pch.business.facade.impl.ciudad;

import co.edu.uco.pch.business.assembler.DTO.impl.CiudadAssemblerDTO;
import co.edu.uco.pch.business.facade.FacadeWithoutReturn;
import co.edu.uco.pch.business.usecase.impl.ciudad.RegistrarCiudades;
import co.edu.uco.pch.crosscutting.exceptions.PCHException;
import co.edu.uco.pch.crosscutting.exceptions.custom.BusinessPCHException;
import co.edu.uco.pch.data.dao.factory.DAOFactory;
import co.edu.uco.pch.dto.CiudadDTO;

public class RegistrarCiudadesFacade implements FacadeWithoutReturn<CiudadDTO>{

	private DAOFactory daoFactory;
	public RegistrarCiudadesFacade() {
		daoFactory = DAOFactory.getFactory();
		
	}
	@Override
	public void execute(final CiudadDTO dto) {
		
		daoFactory.iniciarTransaccion();
		
		try {
            var useCase = new RegistrarCiudades(daoFactory);
			var ciudadDomain = CiudadAssemblerDTO.getInstance().toDomain(dto);
			
			useCase.execute(ciudadDomain);
			
			daoFactory.confirmarTransaccion();
			
		}catch(final PCHException excepcion) {
			daoFactory.cancelarTransaccion();
			
		}catch(final Exception excepcion) {
			daoFactory.cancelarTransaccion();
			
			var mensajeUsuario = "Sea precentado un problema tratando de registra la informacion de la ciudad...";
			var mensajeTecnico = "Sea precentado un problema INESPERADO tratando de registrar la ciudad..";
			throw new BusinessPCHException(mensajeTecnico,mensajeUsuario,excepcion);
			
		} finally {
			daoFactory.cerrarConexion();
		}
	}
	


}
