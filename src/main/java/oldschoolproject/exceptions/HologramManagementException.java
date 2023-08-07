package oldschoolproject.exceptions;

public class HologramManagementException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public HologramManagementException(String management, String id) {
		super("Gerenciamento  \"" + management + "\" do holograma \"" + id + "\"");
	}

}
