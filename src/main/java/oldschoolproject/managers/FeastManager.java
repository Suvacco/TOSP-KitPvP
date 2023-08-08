package oldschoolproject.managers;

import org.bukkit.Location;

import oldschoolproject.exceptions.FeastManagementException;
import oldschoolproject.feast.Feast;
import oldschoolproject.feast.FeastLoader;
import oldschoolproject.holograms.Hologram;
import oldschoolproject.holograms.HologramLoader;

public class FeastManager {

	public static void createFeast(String id, Location location) throws FeastManagementException {
		if (FeastLoader.getFeastInstances().containsKey(id)) {
			throw new FeastManagementException(id, "create");
		}
		
		Feast feast = new Feast(location);
		
		FeastLoader.saveFeast(id, feast);
	}

	public static void deleteFeast(String id) throws FeastManagementException {
		if (!FeastLoader.getFeastInstances().containsKey(id)) {
			throw new FeastManagementException(id, "delete");
		}
		
        Feast feast = FeastLoader.getFeastInstances().get(id);

        FeastLoader.getFeastInstances().remove(id);

        FeastLoader.deleteFeast(id);
	}

}
