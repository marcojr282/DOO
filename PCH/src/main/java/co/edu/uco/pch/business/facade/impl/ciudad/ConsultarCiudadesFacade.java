package co.edu.uco.pch.business.facade.impl.ciudad;

import java.util.List;

import co.edu.uco.pch.business.assembler.DTO.impl.CiudadAssemblerDTO;
import co.edu.uco.pch.business.facade.FacadeWithReturn;
import co.edu.uco.pch.business.usecase.impl.ciudad.ConsultarCiudades;

import co.edu.uco.pch.crosscutting.exceptions.PCHException;
import co.edu.uco.pch.crosscutting.exceptions.customs.BusinessPCHException;
import co.edu.uco.pch.data.dao.factory.DAOFactory;
import co.edu.uco.pch.dto.CiudadDTO;

public final class ConsultarCiudadesFacade implements FacadeWithReturn<CiudadDTO, List<CiudadDTO>> {

	
	
	private DAOFactory daoFactory;
	public ConsultarCiudadesFacade() {
		daoFactory = DAOFactory.getFactory();
	}
		
	@Override
	public final List<CiudadDTO> excute(final CiudadDTO dto) {
		try {
            var useCase = new ConsultarCiudades(daoFactory);
			var ciudadDomain = CiudadAssemblerDTO.getInstance().toDomain(dto);
			var resultadosDomain = useCase.execute(ciudadDomain);
			return CiudadAssemblerDTO.getInstance().toDTOCollection(resultadosDomain);
		
		}catch(final PCHException excepcion) {
			
			throw excepcion;
			
		}catch(final Exception excepcion) {
			
		
	
			
			var mensajeUsuario = "Sea precentado un problema tratando de consultar la informacion de la ciudad...";
			var mensajeTecnico = "Sea precentado un problema INESPERADO tratando de registrar la ciudad..";
			throw new BusinessPCHException(mensajeTecnico,mensajeUsuario,excepcion);
			
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
