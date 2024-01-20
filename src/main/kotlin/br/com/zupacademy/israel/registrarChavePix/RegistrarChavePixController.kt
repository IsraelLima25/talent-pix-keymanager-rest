package br.com.zupacademy.israel.registrarChavePix

import br.com.zupacademy.israel.KeymanagerRegistrarGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.validation.Valid


@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RegistrarChavePixController(
    @Inject private val registrarChavePixClient: KeymanagerRegistrarGrpcServiceGrpc.KeymanagerRegistrarGrpcServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Post("/pix")
    fun registrarChavePix(clienteId: UUID, @Valid @Body request: NovaChavePixRequest): HttpResponse<Any> {

        LOGGER.info("[$clienteId] registrando uma nova chave pix com $request")

        val grpcReponse = registrarChavePixClient.registrar(request.toModelGrpc(clienteId))

        return HttpResponse.created(location(clienteId = clienteId, pixId = grpcReponse.pixId))
    }

    private fun location(clienteId: UUID, pixId: String) = HttpResponse
        .uri("/api/v1/clientes/$clienteId/pix/${pixId}")

}