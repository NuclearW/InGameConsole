package com.nuclearw.ingameconsole;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ConsoleLoggerHandler extends Handler {
	private InGameConsole plugin;

	public ConsoleLoggerHandler(InGameConsole instance) {
		this.plugin = instance;
	}

	@Override
	public void close() throws SecurityException {
	}

	@Override
	public void flush() {
	}

	@Override
	public void publish(LogRecord record) {
		plugin.sendToListeners(record.getMessage());
	}
}
