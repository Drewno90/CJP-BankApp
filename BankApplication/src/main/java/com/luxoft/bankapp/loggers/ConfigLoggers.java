package com.luxoft.bankapp.loggers;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ConfigLoggers {

	public ConfigLoggers() throws SecurityException, IOException {

		Logger l = Logger.getLogger("exceptions");
		l.addHandler(new FileHandler("exceptions.log"));
		l = Logger.getLogger("clients");
		l.addHandler(new FileHandler("clients.log"));
		l = Logger.getLogger("db");
		l.addHandler(new FileHandler("db.log"));
	}
}
