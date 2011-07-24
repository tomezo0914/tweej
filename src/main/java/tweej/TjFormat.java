package tweej;

public enum TjFormat {
	
	xml,
	
	json,
	
	rss,
	
	atom;
	
	
	public static final String formatToString(TjFormat format, TjFormat...supportedFormats) {
		if (supportedFormats != null) {
			if (format == null) {
				return supportedFormats[0].name();
			}
			for (TjFormat supportedFormat : supportedFormats) {
				if (format == supportedFormat) {
					return format.name();
				}
			}
		}
		return null;
	}

}
