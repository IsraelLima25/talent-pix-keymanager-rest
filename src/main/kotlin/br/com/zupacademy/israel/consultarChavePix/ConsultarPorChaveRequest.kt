package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.ConsultarChavePixRequest

class ConsultarPorChaveRequest : FiltroConsulta {

    override fun filtroRequest(filter: FilterRequest): ConsultarChavePixRequest {

        return ConsultarChavePixRequest
            .newBuilder()
            .setChave(filter.chave)
            .build()
    }
}