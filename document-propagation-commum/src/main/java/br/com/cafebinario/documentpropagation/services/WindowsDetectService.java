package br.com.cafebinario.documentpropagation.services;

import org.apache.commons.exec.OS;
import org.springframework.stereotype.Service;

@Service
public class WindowsDetectService {

	public Boolean isWindowsSystemOperational() {
		return OS.isFamilyWindows();
	}
}
