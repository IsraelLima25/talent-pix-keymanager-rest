package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.ConsultarChavePixRequest
import br.com.zupacademy.israel.KeymanagerConsultarGrpcServiceGrpc
import br.com.zupacademy.israel.KeymanagerListarTodasGrpcServiceGrpc
import br.com.zupacademy.israel.ListarTodasChavesPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes")
class ConsultarChavePixController(
    val consultarChavePixClient: KeymanagerConsultarGrpcServiceGrpc.KeymanagerConsultarGrpcServiceBlockingStub,
    val listarTodasChavesPix: KeymanagerListarTodasGrpcServiceGrpc.KeymanagerListarTodasGrpcServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/pix")
    fun consultarChavePix(
        @QueryValue pixId: String?,
        @QueryValue idCliente: String?,
        @QueryValue chave: String?
    ): HttpResponse<Any> {

        LOGGER.info("consultando chave pix")

        val requestFilter: ConsultarChavePixRequest = ConsultarChavePixContextRequest()
            .filtroRequest(FilterRequest(pixId, idCliente, chave))

        val chaveResponse = consultarChavePixClient.consultar(requestFilter)

        return HttpResponse.ok(ConsultaChavePixResponse(chaveResponse))
    }

    @Get("/pix/{idCliente}")
    fun listarTodasChavesPix(idCliente: UUID): HttpResponse<Any> {

        LOGGER.info("Listando todas as chaves pix do cliente ${idCliente.toString()}")

        val grpcRequest = ListarTodasChavesPixRequest
            .newBuilder()
            .setClientId(idCliente.toString())
            .build()

        val grpcResponse = listarTodasChavesPix.listarTodas(grpcRequest)

        return HttpResponse.ok(ListaChavesResponse(grpcResponse))
    }
}