package br.com.zupacademy.israel.registrarChavePix

import br.com.zupacademy.israel.KeymanagerRegistrarGrpcServiceGrpc
import br.com.zupacademy.israel.RegistraChavePixResponse
import br.com.zupacademy.israel.compartilhado.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegistrarChavePixControllerTest {

    @field:Inject
    lateinit var registraStub: KeymanagerRegistrarGrpcServiceGrpc.KeymanagerRegistrarGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve registrar uma nova chave pix`() {

        // cenário
        val pixId = UUID.randomUUID().toString()
        val clienteId = UUID.randomUUID().toString()

        val respostaGrpc = RegistraChavePixResponse
            .newBuilder()
            .setPixId(pixId)
            .build()
        Mockito.`when`(registraStub.registrar(Mockito.any())).thenReturn(respostaGrpc)

        val novaChavePix = NovaChavePixRequest(
            tipoDeConta = TipoDeContaRequest.CONTA_CORRENTE,
            chave = "teste@teste.com.br",
            tipoDeChave = TipoDeChaveRequest.EMAIL,
            TitularRequest(
                nome = "sizenando",
                documento = "04846596315",
                tipoDeTitular = TipoTitularRequest.PESSOA_FISICA
            )
        )

        // ação
        val request = HttpRequest.POST("/api/v1/clientes/$clienteId/pix", novaChavePix)
        val response = client.toBlocking().exchange(request, NovaChavePixRequest::class.java)

        // validação
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(pixId))
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() =
            Mockito.mock(KeymanagerRegistrarGrpcServiceGrpc.KeymanagerRegistrarGrpcServiceBlockingStub::class.java)
    }

}