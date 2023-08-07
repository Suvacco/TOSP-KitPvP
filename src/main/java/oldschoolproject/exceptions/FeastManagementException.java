package oldschoolproject.exceptions;

public class FeastManagementException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FeastManagementException(String management, String id) {
		super("Gerenciamento  \"" + management + "\" do feast \"" + id + "\" falhou");
	}

}
