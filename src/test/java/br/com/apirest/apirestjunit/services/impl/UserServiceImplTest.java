package br.com.apirest.apirestjunit.services.impl;

import br.com.apirest.apirestjunit.domain.DTO.UserDTO;
import br.com.apirest.apirestjunit.domain.User;
import br.com.apirest.apirestjunit.repositories.UserRepository;
import br.com.apirest.apirestjunit.services.exceptions.DataIntegratyViolationException;
import br.com.apirest.apirestjunit.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final String LUCAS = "Lucas";
    public static final String EMAIL = "lucas@gmail.com";
    public static final String PASSWORD = "123";
    public static final int ID = 1;
    public static final String OBJETO_NÃO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    public static final String E_MAIL_JÁ_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startuser();
    }

    @Test
    void WhenFindByIdThenReturnAnUserInstance(){
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        User response = service.findById(ID);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
    }

    @Test
    void whenFindyIdThenReturnAnObjectNotFoundException(){
        when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NÃO_ENCONTRADO));
        try{
            service.findById(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnListOfUsers(){
        when(userRepository.findAll()).thenReturn(List.of(user, user, user));
        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(3,response.size());
        assertEquals(User.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(LUCAS, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnSuccess(){
        when(userRepository.save(any())).thenReturn(user);

        User response = service.create(userDTO);
        assertNotNull(User.class, String.valueOf(response.getClass()));
        assertEquals(ID, response.getId());
        assertEquals(LUCAS, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException(){
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
           optionalUser.get().setId(1);
           service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JÁ_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }


    @Test
    void whenUpdateThenReturnSuccess(){
        when(userRepository.save(any())).thenReturn(user);

        User response = service.update(userDTO);
        assertNotNull(User.class, String.valueOf(response.getClass()));
        assertEquals(ID, response.getId());
        assertEquals(LUCAS, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnDataIntegrityViolationException(){
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JÁ_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess(){
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyInt());
        service.delete(ID);
        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException(){
        when(userRepository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NÃO_ENCONTRADO));
        try{
            service.delete(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NÃO_ENCONTRADO, ex.getMessage());
        }
    }

    private void startuser(){
        user = new User(ID, LUCAS, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, LUCAS, EMAIL, PASSWORD);
        optionalUser = optionalUser.of(new User(ID, LUCAS, EMAIL, PASSWORD));

    }

}