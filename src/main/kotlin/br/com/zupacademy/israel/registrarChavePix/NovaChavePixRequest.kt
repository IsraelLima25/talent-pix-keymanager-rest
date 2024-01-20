package br.com.zupacademy.israel.registrarChavePix

import br.com.zupacademy.israel.RegistraChavePixRequest
import br.com.zupacademy.israel.TipoChave
import br.com.zupacademy.israel.TipoConta
import br.com.zupacademy.israel.Titular
import br.com.zupacademy.israel.validator.ValidPixKey
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
class NovaChavePixRequest(
    @field:NotNull val tipoDeConta: TipoDeContaRequest?,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoDeChave: TipoDeChaveRequest?,
    @field:Valid val titular: TitularRequest
) {

    fun toModelGrpc(clienteId: UUID): RegistraChavePixRequest {
        return RegistraChavePixRequest.newBuilder()
            .setIdCliente(clienteId.toString())
            .setTipoConta(tipoDeConta?.atributoGrpc ?: TipoConta.UNKNOW_TIPO_CONTA)
            .setTipoChave(tipoDeChave?.atributoGrpc ?: TipoChave.UNKNOW_TIPO_CHAVE)
            .setChave(chave ?: "")
            .setTitular(
                Titular.newBuilder()
                    .setNome(titular.nome)
                    .setDocumento(titular.documento)
                    .setTipoTitular(titular.tipoDeTitular.atributoGrpc)
            )
            .build()
    }

}

enum class TipoDeChaveRequest(val atributoGrpc: TipoChave) {
    CPF(TipoChave.CPF) {
        override fun valida(chave: String?, context: ConstraintValidatorContext?): Boolean {
            if (chave.isNullOrBlank()) {
                context?.messageTemplate("Cpf não deve está em branco!!")
                return false
            }
            return CPFValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    CELULAR(TipoChave.CELULAR) {
        override fun valida(chave: String?, context: ConstraintValidatorContext?): Boolean {
            if (chave.isNullOrBlank()) {
                context?.messageTemplate("Telefone não deve está em branco")
                return false
            }
            if (!chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())) {
                context?.messageTemplate("Telefone formato inválido!!")
                return false
            }
            return true
        }
    },
    EMAIL(TipoChave.EMAIL) {
        override fun valida(chave: String?, context: ConstraintValidatorContext?): Boolean {
            if (chave.isNullOrBlank()) {
                context?.messageTemplate("Email não deve está em branco!!")
                return false
            }
            val run = EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
            return run
        }
    },
    ALEATORIA(TipoChave.ALEATORIA) {
        override fun valida(chave: String?, context: ConstraintValidatorContext?): Boolean {
            if (chave!!.isNotBlank()) {
                context?.messageTemplate("Quando o tipo de chave é aleatório, não se espera uma chave preenchida!!")
                return false
            }
            return true
        }
    };

    abstract fun valida(chave: String?, context: ConstraintValidatorContext?): Boolean
}

enum class TipoDeContaRequest(val atributoGrpc: TipoConta) {

    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}
