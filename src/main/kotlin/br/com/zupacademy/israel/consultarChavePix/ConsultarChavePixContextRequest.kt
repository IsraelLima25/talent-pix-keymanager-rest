package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.ConsultarChavePixRequest
import java.lang.IllegalArgumentException

class ConsultarChavePixContextRequest {

    fun filtroRequest(filterRequest: FilterRequest): ConsultarChavePixRequest {

        if (filterRequest.chave != null) {
            return ConsultarPorChaveRequest().filtroRequest(filterRequest)
        } else if (filterRequest.pixId != null && filterRequest.clienteId != null) {
            return ConsultarPorIdPixIdClienteRequest().filtroRequest(filterRequest)
        }
        throw IllegalArgumentException("Filtro inválido")
    }

}