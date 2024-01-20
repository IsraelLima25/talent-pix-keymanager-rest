package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.ConsultarChavePixResponse
import br.com.zupacademy.israel.TipoConta
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ConsultaChavePixResponse(chaveResponse: ConsultarChavePixResponse) {

    val clienteId = chaveResponse.clienteId
    val pixId = chaveResponse.pixId

    val chave = mapOf(
        Pair("tipo", chaveResponse.chave.tipo),
        Pair("chave", chaveResponse.chave.chave),
        Pair(
            "conta", mapOf(
                Pair("tipo",  when (chaveResponse.chave.conta.tipo) {
                    TipoConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
                    TipoConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
                    else -> "NAO_RECONHECIDA"
                }),
                Pair("instituicao", chaveResponse.chave.conta.instituicao),
                Pair("nomeDoTitular", chaveResponse.chave.conta.nomeDoTitular),
                Pair("cpfDoTitular", chaveResponse.chave.conta.cpfDoTitular),
                Pair("agencia", chaveResponse.chave.conta.agencia),
                Pair("numeroDaConta", chaveResponse.chave.conta.numeroDaConta)
            )
        )
    )
    val criadaEm = chaveResponse.chave.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
}
