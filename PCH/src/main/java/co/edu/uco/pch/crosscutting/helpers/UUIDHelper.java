package co.edu.uco.pch.crosscutting.helpers;

import java.util.Random;
import java.util.UUID;


public class UUIDHelper {


	public static final String DEFAULD_UUID_STRING = "2699389d-4110-4c0a-85c8-5b9a7167ebaa";

	private UUIDHelper() {
		super();
	}

	public static UUID generarUUIDDefecto() {
        return new UUID(0L, 0L);
    }

	public static UUID convertirStringaUUID(String uuidString) {
            return UUID.fromString(uuidString);        
    }

	public static UUID generate() {
		Random random = new Random();
        long mas = random.nextLong();
        long menos = random.nextLong();
        return new UUID(mas, menos);
    }

	public static final UUID getDefauld(final UUID value, final UUID valorDefecto) {		
		return ObjectHelper.getObjectHelper().getDefaultValue(value, valorDefecto);
	}


	public static final UUID getDefauld() {
		return convertirStringaUUID(DEFAULD_UUID_STRING);
	}
}
