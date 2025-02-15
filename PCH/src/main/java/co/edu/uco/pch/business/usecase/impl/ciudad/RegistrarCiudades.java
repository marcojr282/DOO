package co.edu.uco.pch.business.usecase.impl.ciudad;

import java.util.UUID;


import co.edu.uco.pch.business.assembler.entity.impl.DepartamentoAssemblerEntity;
import co.edu.uco.pch.business.domain.CiudadDomain;
import co.edu.uco.pch.business.usecase.UseCaseWithoutReturn;
import co.edu.uco.pch.crosscutting.exceptions.customs.BusinessPCHException;
import co.edu.uco.pch.crosscutting.helpers.ObjectHelper;
import co.edu.uco.pch.crosscutting.helpers.TextHelper;
import co.edu.uco.pch.crosscutting.helpers.UUIDHelper;
import co.edu.uco.pch.data.dao.factory.DAOFactory;
import co.edu.uco.pch.entity.CiudadEntity;
import co.edu.uco.pch.entity.DepartamentoEntity;



public final class RegistrarCiudades implements UseCaseWithoutReturn<CiudadDomain>{

	private DAOFactory factory;
	private static final int MAX_LENGTH_NOMBRE_CIUDAD = 50;
	
	public RegistrarCiudades(final DAOFactory factory) {
		if (ObjectHelper.getObjectHelper().isNull(factory)) {
			var mensajeUsuario= "se ha presentado un problema tratando de llevar a cabo el registro de la ciudad";
			var mensajeTecnico = "el DAOfactoty para creear la ciudad llego nulo...";
			throw new BusinessPCHException(mensajeTecnico, mensajeUsuario);
		}
		this.factory= factory;
	}
	
	
	@Override
	public void execute(final CiudadDomain data) {
		// validar que los datos requwridos por el caso de uso sean correctos
		//a nivel de tipo de dato, longitud,obligatoriedad,formato,rango
		validarDatos(data);
		
		
		
		// validar que no exista otra ciudad con el mismo nombre y departamento
		validarCiudadMismoNombreMismoDepartamento(data.getNombre(), data.getDepartamento().getId());
		
		// validar que no exista otra ciudad con el mismo identificador
		var ciudadEntity = CiudadEntity.build().setId(generarIdentificadorCiudad()).setNombre(data.getNombre())
															.setDepartamento(DepartamentoAssemblerEntity.getInstance().toEntity(data.getDepartamento()));
		
		
		// guardar la nueva ciudad
		factory.getCiudadDAO().crear(ciudadEntity);
		// TODO Auto-generated method stub
		
	}
	
	private final UUID generarIdentificadorCiudad() {
		UUID id = UUIDHelper.generate();
		boolean existeId = true;

		while (existeId) {
			id = UUIDHelper.generate();
			var ciudadEntity = CiudadEntity.build().setId(id);
			var resultados = factory.getCiudadDAO().consultar(ciudadEntity);
			existeId = !resultados.isEmpty();
		}
		return id;
	}
	
	private final void validarCiudadMismoNombreMismoDepartamento(final String nombreCiudad,final UUID idDepartamento) {
		var ciudadEntity = CiudadEntity.build().setNombre(nombreCiudad).setDepartamento(DepartamentoEntity.build().setId(idDepartamento));
		var resultados = factory.getCiudadDAO().consultar(ciudadEntity);
		if (!resultados.isEmpty()){
			var mensajeUsuario = "ya existe una ciudad con el nombre \"${1}\"asociado al departamento";
			throw new BusinessPCHException(mensajeUsuario);}
			
		}
		
	private void validarDatos(final CiudadDomain data) {

			if (TextHelper.isNull(data.getNombre())) {
				throw new BusinessPCHException("El nombre de la ciudad es obligatorio.");
			}

			if (ObjectHelper.getObjectHelper().isNull(data.getDepartamento())) {
				throw new BusinessPCHException("El departamento es obligatorio.");
			}

			if (data.getNombre().length() > MAX_LENGTH_NOMBRE_CIUDAD) {
				throw new BusinessPCHException("El nombre de la ciudad excede la longitud máxima permitida.");
			}

			if (!isValidCityNameFormat(data.getNombre())) {
				throw new BusinessPCHException("El formato del nombre de la ciudad es incorrecto.");
			}
		}

		private boolean isValidCityNameFormat(String nombre) {

			return nombre.matches("[a-zA-Z]+");
	}
	

	

		
}