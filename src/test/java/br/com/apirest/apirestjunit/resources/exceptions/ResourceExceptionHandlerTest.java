package br.com.apirest.apirestjunit.resources.exceptions;

import br.com.apirest.apirestjunit.services.exceptions.DataIntegratyViolationException;
import br.com.apirest.apirestjunit.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    public static final String E_MAIL_JÁ_CADASTRADO = "E-mail já cadastrado";
    DataIntegratyViolationException dataIntegratyViolationException;

    public static final String OBJETO_NÃO_ENCONTRADO = "Objeto não encontrado";
    @InjectMocks
    ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnReponseEntity(){
        ResponseEntity<StandardError> response = exceptionHandler
                .ObjectNotFound(new ObjectNotFoundException(OBJETO_NÃO_ENCONTRADO),
                        new MockHttpServletRequest());

        response.getBody().getPath();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(OBJETO_NÃO_ENCONTRADO, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
        assertNotEquals("/user/2", response.getBody().getPath());
        assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());

    }

    @Test
    void dataIntegrationViolationException(){
        ResponseEntity<StandardError> response = exceptionHandler
                .DataIntegratyViolationException(new DataIntegratyViolationException(E_MAIL_JÁ_CADASTRADO),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(E_MAIL_JÁ_CADASTRADO, response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }



}