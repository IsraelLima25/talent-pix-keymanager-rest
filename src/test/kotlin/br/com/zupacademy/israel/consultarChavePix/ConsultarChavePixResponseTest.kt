package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.TipoChave
import br.com.zupacademy.israel.TipoConta
import java.time.LocalDateTime

class ConsultarChavePixResponseTest(
    val clienteId: String,
    val pixId: String,
    val chave: Chave,
    val criadaEm: LocalDateTime
)

class Chave(val tipo: TipoChave, val chave: String, val conta: Conta)

class Conta(
    val tipo: TipoConta,
    val instituicao: String,
    val nomeDoTitular: String,
    val cpfDoTitular: String,
    val agencia: String,
    val numeroDaConta: String
)
