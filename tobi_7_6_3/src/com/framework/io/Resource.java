package com.framework.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public interface Resource extends InputStreamSource {
	
	boolean exists();
	boolean isReadable();
	boolean isOpen();
	URL getURL() throws IOException;
	URI getURI() throws IOException;
	File getFile() throws IOException;
	
	Resource createRelative(String relativePath) throws IOException;
	
	long lastModified() throws IOException;
	String getFilename();
	String getDescription();
}

