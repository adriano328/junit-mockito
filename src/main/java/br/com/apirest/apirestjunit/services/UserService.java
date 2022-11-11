package br.com.apirest.apirestjunit.services;

import br.com.apirest.apirestjunit.domain.DTO.UserDTO;
import br.com.apirest.apirestjunit.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User create(UserDTO obj);

    User update(UserDTO obj);

    void delete(Integer id);

}
