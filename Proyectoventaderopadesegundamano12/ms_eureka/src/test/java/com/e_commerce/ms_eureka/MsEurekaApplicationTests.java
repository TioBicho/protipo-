package com.e_commerce.ms_eureka;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Inicialización")
class MsEurekaApplicationTests {

	@Test
	@DisplayName("Validar inicialización")
	void contextLoadsSimulado() {
		assertDoesNotThrow(() -> {
			MsEurekaApplication app = new MsEurekaApplication();
		});
	}
}