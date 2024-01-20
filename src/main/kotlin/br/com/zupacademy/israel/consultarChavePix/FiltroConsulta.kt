package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.ConsultarChavePixRequest

interface FiltroConsulta {

    fun filtroRequest(filter: FilterRequest): ConsultarChavePixRequest

}