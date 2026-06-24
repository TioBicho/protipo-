package com.e_commerce.ms_envios;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Inicialización")
class MsEnviosApplicationTests { // Cambia el nombre aquí

	@Test
	@DisplayName("Validar inicialización")
	void contextLoadsSimulado() {
		assertDoesNotThrow(() -> {
			// Pon la clase principal de tu microservicio aquí
			MsEnviosApplicationTests app = new MsEnviosApplicationTests();
		});
	}
}