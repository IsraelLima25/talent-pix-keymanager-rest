package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.ConsultarChavePixRequest

class ConsultarPorIdPixIdClienteRequest : FiltroConsulta {

    override fun filtroRequest(filter: FilterRequest): ConsultarChavePixRequest {

        return ConsultarChavePixRequest
            .newBuilder()
            .setPixId(
                ConsultarChavePixRequest.FiltroPorPixId
                    .newBuilder()
                    .setPixId(filter.pixId)
                    .setClientId(filter.clienteId).build()
            )
            .build()
    }
}