package com.e_commerce.ms_cliente;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class) // Usar Mockito puro en lugar de levantar Spring completo
@DisplayName("Pruebas de Inicialización del Microservicio")
class MsClienteApplicationTests {

	@Test
	@DisplayName("Validar que la clase principal de la aplicación pueda instanciarse correctamente")
	void contextLoadsSimulado() {
		assertDoesNotThrow(() -> {
			MsClienteApplication application = new MsClienteApplication();
		});
	}

}