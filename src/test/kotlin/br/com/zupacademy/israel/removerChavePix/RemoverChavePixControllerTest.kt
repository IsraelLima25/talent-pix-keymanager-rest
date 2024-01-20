package br.com.zupacademy.israel.removerChavePix

import br.com.zupacademy.israel.KeymanagerRemoverGrpcServiceGrpc
import br.com.zupacademy.israel.RemoverChavePixResponseVoid
import br.com.zupacademy.israel.compartilhado.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RemoverChavePixControllerTest {

    @field:Inject
    lateinit var removerStub: KeymanagerRemoverGrpcServiceGrpc.KeymanagerRemoverGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deve remover chave pix`() {

        //cenário
        val pixId = UUID.randomUUID().toString()
        val idCliente = UUID.randomUUID().toString()

        val removerChavePix = RemoverChavePix(pixId, idCliente)

        Mockito.`when`(removerStub.remover(Mockito.any()))
            .thenReturn(
                RemoverChavePixResponseVoid
                    .newBuilder()
                    .build()
            )

        //ação
        val request = HttpRequest.DELETE("/api/v1/clientes", removerChavePix)
        val response = client.toBlocking().exchange(request, RemoverChavePix::class.java)

        //validação
        assertEquals(HttpStatus.OK, response.status)
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory() {
        @Singleton
        fun stubMock() =
            Mockito.mock(KeymanagerRemoverGrpcServiceGrpc.KeymanagerRemoverGrpcServiceBlockingStub::class.java)
    }

}