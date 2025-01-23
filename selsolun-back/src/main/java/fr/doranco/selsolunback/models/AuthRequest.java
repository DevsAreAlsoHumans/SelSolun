package fr.doranco.selsolunback.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



public record AuthRequest (String email, String password) {

}
