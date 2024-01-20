package br.com.zupacademy.israel.registrarChavePix

import br.com.zupacademy.israel.TipoTitular
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class TitularRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank val documento: String,
    @field:NotNull val tipoDeTitular: TipoTitularRequest
)

enum class TipoTitularRequest(val atributoGrpc: TipoTitular) {

    PESSOA_FISICA(TipoTitular.PESSOA_FISICA),
    PESSOA_JURIDICA(TipoTitular.PESSOA_JURIDICA)

}
