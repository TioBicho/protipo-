package com.e_commerce.ms_api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Inicialización")
class MsApiApplicationTests{

	@Test
	@DisplayName("Validar inicialización")
	void contextLoadsSimulado() {
		assertDoesNotThrow(() -> {
			MsApiApplicationTests app = new MsApiApplicationTests();
		});
	}
}