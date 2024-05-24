package co.edu.uco.pch.data.dao.entity.concrete.azuresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.pch.crosscutting.exceptions.custom.DataPCHException;
import co.edu.uco.pch.crosscutting.helpers.ObjectHelper;
import co.edu.uco.pch.crosscutting.helpers.TextHelper;
import co.edu.uco.pch.data.dao.entity.CiudadDAO;
import co.edu.uco.pch.data.dao.entity.concrete.SqlConnection;
import co.edu.uco.pch.entity.CiudadEntity;
import co.edu.uco.pch.entity.DepartamentoEntity;

public final class CiudadAzureSqlDAO extends SqlConnection  implements CiudadDAO{
	
	public CiudadAzureSqlDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(CiudadEntity data) {
		
		final StringBuilder sentenciaSql = new StringBuilder();
	    sentenciaSql.append("SELECT id, nombre, departamento FROM Ciudad WHERE 1=1");

	    final List<Object> parametros = new ArrayList<>();

	    if (!ObjectHelper.getObjectHelper().isNull(data.getId())) {
	        sentenciaSql.append(" AND id = ?");
	        parametros.add(data.getId());
	    }
	    if (!TextHelper.isNullOrEmpty(data.getNombre())) {
	        sentenciaSql.append(" AND nombre = ?");
	        parametros.add(data.getNombre());
	    }
	    if (!ObjectHelper.getObjectHelper().isNull(data.getDepartamento()) && !ObjectHelper.getObjectHelper().isNull(data.getDepartamento().getId())) {
	        sentenciaSql.append(" AND departamento = ?");
	        parametros.add(data.getDepartamento().getId());
	    }
	    final List<CiudadEntity> ciudades = new ArrayList<>();

	    try (final PreparedStatement sentenciaSqlPreparada = getConnection()
	            .prepareStatement(sentenciaSql.toString())) {

	        for (int i = 0; i < parametros.size(); i++) {
	            sentenciaSqlPreparada.setObject(i + 1, parametros.get(i));
	        }

	        try (final ResultSet resultado = sentenciaSqlPreparada.executeQuery()) {
	            while (resultado.next()) {
	                CiudadEntity ciudad = new CiudadEntity();
	                ciudad.setId((UUID) resultado.getObject("id"));
	                ciudad.setNombre(resultado.getString("nombre"));
	                DepartamentoEntity departamento = new DepartamentoEntity();
	                departamento.setId((UUID) resultado.getObject("departamento"));
	                ciudad.setDepartamento(departamento);
	                ciudades.add(ciudad);
	            }
	        }

	    } catch (final SQLException excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de consultar las ciudades. Por favor, contacte al administrador del sistema.";
	        var mensajeTecnico = "Se ha presentado una SQLException tratando de realizar la consulta de las ciudades en la tabla \"Ciudad\" de la base de datos Azure SQL.";

	        throw new DataPCHException(mensajeUsuario, mensajeTecnico, excepcion);

	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de consultar las ciudades. Por favor, contacte al administrador del sistema.";
	        var mensajeTecnico = "Se ha presentado un problema INESPERADO con una excepción de tipo Exception tratando de realizar la consulta de las ciudades en la tabla \"Ciudad\" de la base de datos Azure SQL.";

	        throw new DataPCHException(mensajeUsuario, mensajeTecnico, excepcion);
	    }


		return ciudades;
	}

	@Override
	public List<CiudadEntity> consultar(CiudadEntity data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modificar(CiudadEntity data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	public void eliminar(UUID id) {
		// TODO Auto-generated method stub
		final StringBuilder sentenciaSQL = new StringBuilder();

		sentenciaSQL.append("DELETE FROM Ciudad WHERE id = ?");

		try (final PreparedStatement sentenciaSQLPreparada = getConnection().prepareStatement(sentenciaSQL.toString())){
			sentenciaSQLPreparada.setObject(1, id);

			sentenciaSQLPreparada.executeUpdate();
		}catch(final SQLException excepcion) {
			var mensajeUsuario = "se ha presentado un prblemao tratando de eliminar la ciudad \"${1}\" y si el problemas contacte a el administrador ...";
			var mensajeTecnico = "Se ha presentado una excepcion se tipo SQLexception tatando de realizar el delete de la ciudad \\\"${1}\\\" en la tabla pais\"\r\n"
					+ "					+ \"de la base de datos azureSql.para mas detalles revise de forma completa la excepcionRaiz presentada";
			throw new DataPCHException(mensajeTecnico, mensajeUsuario);

		}catch(final Exception excepcion) {
			var mensajeUsuario = "\"se ha presentado un prblema tratando de eliminar la ciudad \\\"${1}\\\" y si el problemas contacte a el administrador ...\"";
			var mensajeTecnico = "Se ha presentado una excepcion se tipo SQLexception tatando de realizar el delete de la ciudad \"${1}\" en la tabla pais"
					+ "de la base de datos azureSql.para mas detalles revise de forma completa la excepcionRaiz presentada ";
			throw new DataPCHException(mensajeTecnico, mensajeUsuario);
		}


	}

}