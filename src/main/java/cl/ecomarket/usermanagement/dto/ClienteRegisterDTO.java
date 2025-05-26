package cl.ecomarket.usermanagement.dto;

import lombok.Data;

@Data
public class ClienteRegisterDTO {
    private String username;
    private String password;
    private String email;
}