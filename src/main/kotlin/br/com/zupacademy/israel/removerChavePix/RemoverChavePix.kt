package br.com.zupacademy.israel.removerChavePix

import br.com.zupacademy.israel.validator.ValidUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class RemoverChavePix(
    @field:ValidUUID @field:NotBlank val pixId: String,
    @field:ValidUUID @field:NotBlank val idCliente: String
)
