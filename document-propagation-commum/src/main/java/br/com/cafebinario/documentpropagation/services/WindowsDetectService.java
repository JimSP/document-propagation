package br.com.cafebinario.documentpropagation.services;

import org.apache.commons.exec.OS;
import org.springframework.stereotype.Service;

import br.com.cafebinario.logger.Log;
import br.com.cafebinario.logger.VerboseMode;

@Service
public class WindowsDetectService {

	@Log(verboseMode=VerboseMode.ON)
	public Boolean isWindowsSystemOperational() {
		return OS.isFamilyWindows();
	}
}
