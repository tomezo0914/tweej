package tweej;

public class TjMain {

	public static final String version = "1.0.0";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getVersion();
	}
	
	public static String getVersion() {
		System.out.println("tweej version " + version);
		return version;
	}

}
