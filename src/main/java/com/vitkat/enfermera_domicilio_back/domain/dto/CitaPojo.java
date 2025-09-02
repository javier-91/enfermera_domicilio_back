package com.vitkat.enfermera_domicilio_back.domain.dto;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitaPojo {

    private Integer id;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nom;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no es correcto")
    private String correu;

    @Pattern(regexp = "^[0-9]{9}$", message = "El número debe tener exactamente 9 dígitos")
    private String telefon;

    private String missatge;

    @NotNull(message = "La fecha no puede ser nula")
    private Date data;

    @NotBlank(message = "La hora no puede ser nula ni vacía")
    private String hora;

    @NotBlank(message = "Tienes que seleccionar una enfermera")
    private String enfermera;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccio;

    @NotNull(message = "Minutos de servicio no puede ser nulo")
    private Integer minutosServicio;

    @Override
    public String toString() {
        return "Cita: \n Nombre = " + nom + "\n" + 
               "Correo = " + correu + "\n" + 
               "Telefono = " + telefon + "\n" + 
               "Fecha = " + data + "\n" + 
               "Hora = " + hora + "\n" + 
               "Mensaje = " + missatge + "\n" +
               "Enfermera = " + enfermera + "\n" +
               "Direccion cliente = " + direccio;
    }
}
