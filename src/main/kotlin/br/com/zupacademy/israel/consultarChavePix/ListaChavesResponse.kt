package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.ListarTodasChavesPixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListaChavesResponse(grpcResponse: ListarTodasChavesPixResponse) {

    val chaves = grpcResponse.chavesList.map { chave ->
        mapOf(
            Pair("pixId", chave.pixId),
            Pair("clientId", chave.clientId),
            Pair("tipoDeChave", chave.tipoDeChave),
            Pair("tipoDeConta", chave.tipoDeConta),
            Pair("criadaEm", chave.criadaEm.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
            })
        )
    }
    val clienteId = grpcResponse.clientId
}
