package br.com.ejrocha.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PaisDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotNull(message = "O código ISO é obrigatório")
    @Size(min = 2, max = 3, message = "O código ISO deve ter entre 2 e 3 caracteres")
    private String codigoIso;

    private String codigoIso3;
    private String regiao;
    private String subRegiao;
    private Long area;
    private Long populacao;
    private String codigoFifa;
    private String bandeiraSvg;


}
