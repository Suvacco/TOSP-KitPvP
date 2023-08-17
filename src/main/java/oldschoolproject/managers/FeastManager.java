package oldschoolproject.managers;

import oldschoolproject.exceptions.OperationFailException;
import org.bukkit.Location;

import oldschoolproject.feast.Feast;
import oldschoolproject.feast.FeastLoader;

public class FeastManager {

	public static void createFeast(String id, Location location, String secondsToSpawn, String secondsToDespawn, String secondsInCooldown) throws OperationFailException {
		if (FeastLoader.getFeastInstances().containsKey(id)) {
			throw new OperationFailException("A feast with the ID \"" + id + "\" already exists");
		}
		
		Feast feast = new Feast(id, location, Integer.parseInt(secondsToSpawn), Integer.parseInt(secondsToDespawn), Integer.parseInt(secondsInCooldown));

		FeastLoader.getFeastInstances().put(id, feast);
		
		FeastLoader.saveFeast(id, feast);
	}

	public static void deleteFeast(String id) throws OperationFailException {
		if (!FeastLoader.getFeastInstances().containsKey(id)) {
			throw new OperationFailException("Feast ID \"" + id + "\" not found");
		}

		findFeast(id).delete();

		FeastLoader.getFeastInstances().remove(id);
		
        FeastLoader.deleteFeast(id);
	}

	public static String getFeastsList() {
		StringBuilder sb = new StringBuilder();

		FeastLoader.getFeastInstances().keySet().forEach(key -> { sb.append(key).append(", "); });

		sb.delete(sb.length() - 2, sb.length());

		return sb.toString();
	}

	public static Feast findFeast(String id) throws OperationFailException {
		if (!FeastLoader.getFeastInstances().containsKey(id)) {
			throw new OperationFailException("Feast ID \"" + id + "\" not found");
		}

		return FeastLoader.getFeastInstances().get(id);
	}

	public static void moveFeast(String id, Location location) throws OperationFailException {
		if (!FeastLoader.getFeastInstances().containsKey(id)) {
			throw new OperationFailException("Feast ID \"" + id + "\" not found");
		}

		Feast feast = FeastLoader.getFeastInstances().get(id);

		feast.updateLocation(location);

		FeastLoader.saveFeast(id, feast);
	}

	public static void editFeast(String id, String field, String value) throws OperationFailException {
		if (!FeastLoader.getFeastInstances().containsKey(id)) {
			throw new OperationFailException("Feast ID \"" + id + "\" not found");
		}

		Feast feast = FeastLoader.getFeastInstances().get(id);

		int fieldValue = Integer.parseInt(value);

		switch (field) {
			case "spawn":
				feast.setSecondsToSpawn(fieldValue);
				break;

			case "despawn":
				feast.setSecondsToDespawn(fieldValue);
				break;

			case "cooldown":
				feast.setSecondsInCooldown(fieldValue);
				break;
			default:
				throw new OperationFailException("Field not found");
		}

		FeastLoader.saveFeast(id, feast);

		feast.updateFeast();
	}
}
