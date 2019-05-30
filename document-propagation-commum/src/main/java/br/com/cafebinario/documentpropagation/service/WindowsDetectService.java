package br.com.cafebinario.documentpropagation.service;

import org.apache.commons.exec.OS;
import org.springframework.stereotype.Service;

@Service
public class WindowsDetectService {

	public Boolean isWindowsSystemOperational() {
		return OS.isFamilyWindows();
	}
}
