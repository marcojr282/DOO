package co.edu.uco.pch.dto;

import java.util.UUID;

import co.edu.uco.pch.crosscutting.helpers.ObjectHelper;
import co.edu.uco.pch.crosscutting.helpers.TextHelper;
import co.edu.uco.pch.crosscutting.helpers.UUIDHelper;

public final class PaisDTO {
	private UUID id;
	private String nombre;
	
	public PaisDTO() {
	super();
	}
	
	public PaisDTO(final UUID id, final String nombre) {
		setId(UUIDHelper.getDefault());
		setNombre(TextHelper.EMPTY);
	}
	
	public static final PaisDTO build() {
		return new PaisDTO();
	}
	
	
	public final UUID getId() {
		return id;
	}
	public final PaisDTO setId(final UUID id) {
		this.id = ObjectHelper.getObjectHelper().getDefaultValue(id, UUIDHelper.getDefault());
		return this;
	}
	public final String getNombre() {
		return nombre;
	}
	public final PaisDTO setNombre(final String nombre) {
		this.nombre = TextHelper.applyTrim(nombre);
		return this;
	}
	

}

