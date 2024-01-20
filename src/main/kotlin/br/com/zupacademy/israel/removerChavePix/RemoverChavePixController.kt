package br.com.zupacademy.israel.removerChavePix

import br.com.zupacademy.israel.KeymanagerRemoverGrpcServiceGrpc
import br.com.zupacademy.israel.RemoverChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes")
class RemoverChavePixController(
    @Inject val removerChavePixClient: KeymanagerRemoverGrpcServiceGrpc.KeymanagerRemoverGrpcServiceBlockingStub
) {

    val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Delete
    fun removerChavePix(@Valid @Body removerChavePix: RemoverChavePix): HttpResponse<Any> {

        LOGGER.info("removendo chave pix: pixId: ${removerChavePix.pixId} idCliente: ${removerChavePix.idCliente}")

        val grpcRequest = RemoverChavePixRequest
            .newBuilder()
            .setPixId(removerChavePix.pixId)
            .setIdCliente(removerChavePix.idCliente)
            .build()
        val grpcResponse = removerChavePixClient.remover(grpcRequest)
        return HttpResponse.ok()
    }
}