package co.edu.uco.pch.business.domain;

import java.util.UUID;

import co.edu.uco.pch.crosscutting.helpers.TextHelper;
import co.edu.uco.pch.crosscutting.helpers.UUIDHelper;

public final class PaisDomain {

	private UUID id;
	private String nombre;
	
	private PaisDomain(final UUID id,final String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	public static PaisDomain build(final UUID id,final String nombre) {
		return new PaisDomain(id, nombre);
	}
	
	public static PaisDomain build(final UUID id) {
		return new PaisDomain(id, TextHelper.EMPTY);
	}
	
	public static PaisDomain build() {
		return new PaisDomain(UUIDHelper.getDefault(),TextHelper.EMPTY);
	}
	
	public final UUID getId() {
		return id;
	}
	public final String getNombre() {
		return nombre;
	}
	private final void setId(final UUID id) {
		this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
	}
	private final void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	
	
	
}