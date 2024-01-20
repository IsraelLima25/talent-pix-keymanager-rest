package br.com.zupacademy.israel.consultarChavePix

import br.com.zupacademy.israel.*
import br.com.zupacademy.israel.compartilhado.KeyManagerGrpcFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultarChavePixControllerTest {

    @field:Inject
    lateinit var consultarStub: KeymanagerConsultarGrpcServiceGrpc.KeymanagerConsultarGrpcServiceBlockingStub

    @field:Inject
    lateinit var listarStub: KeymanagerListarTodasGrpcServiceGrpc.KeymanagerListarTodasGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @Test
    fun `deve consultar chave pix por chave`() {

        //cenário
        val requestGrpc = ConsultarChavePixRequest.newBuilder().setChave("sizenando@gmail.com").build()
        `when`(consultarStub.consultar(requestGrpc))
            .thenReturn(createConsultarChavePixRequest())

        val chaveExistente = "sizenando@gmail.com"

        //ação
        val request = HttpRequest.GET<Any>("/api/v1/clientes/pix?chave=$chaveExistente")
        val response = client.toBlocking().exchange(request, ConsultarChavePixResponseTest::class.java)

        //validação
        with(response) {
            assertEquals(HttpStatus.OK, response.status)
            assertEquals("sizenando@gmail.com", body()!!.chave.chave)
        }
    }

    @Test
    fun `deve consultar chave pix por pixId e idClient`() {

        //cenário
        val pixIdExistente = "0f89235e-6fd1-45d9-b989-f714fb881bcf"
        val clienteIdExistente = "5260263c-a3c1-4727-ae32-3bdb2538841b"
        val requestGrpc = ConsultarChavePixRequest.newBuilder().setPixId(
            ConsultarChavePixRequest.FiltroPorPixId.newBuilder()
                .setPixId(pixIdExistente)
                .setClientId(clienteIdExistente)
        ).build()

        `when`(consultarStub.consultar(requestGrpc))
            .thenReturn(createConsultarChavePixRequest())
        //ação
        val request = HttpRequest.GET<Any>("/api/v1/clientes/pix?pixId=$pixIdExistente&idCliente=$clienteIdExistente")
        val response = client.toBlocking().exchange(request, ConsultarChavePixResponseTest::class.java)

        //validação
        with(response) {
            assertEquals(HttpStatus.OK, response.status)
        }
    }

    @Test
    fun `deve lancar excecao quando os dados da consulta por pixId e clienteId forem invalidos`() {

        //cenário
        val pixIdExistente = "0f89235e-6fd1-45d9-b989-f714fb881bcf"
        val clienteIdExistente = "5260263c-a3c1-4727-ae32-3bdb2538841b"
        val requestGrpc = ConsultarChavePixRequest.newBuilder().setPixId(
            ConsultarChavePixRequest.FiltroPorPixId.newBuilder()
                .setPixId(pixIdExistente)
                .setClientId(clienteIdExistente)
        ).build()

        val pixIdInexistente = UUID.randomUUID().toString()
        val clienteIdInexistente = UUID.randomUUID().toString()

        `when`(consultarStub.consultar(requestGrpc))
            .thenReturn(createConsultarChavePixRequest())

        //ação
        val ex = assertThrows(Exception::class.java) {
            val request = HttpRequest.GET<Any>("/api/v1/clientes/pix")
            client.toBlocking().exchange(request, ConsultarChavePixResponseTest::class.java)
        }

        //validação
        with(ex) {
            assertTrue(ex != null)
        }
    }

    @Test
    fun `deve listar todas as chaves pix de um cliente`() {

        val clientIdExistente = "5260263c-a3c1-4727-ae32-3bdb2538841b"

        //cenário
        val requestGrpc = ListarTodasChavesPixRequest
            .newBuilder()
            .setClientId(clientIdExistente)
            .build()

        `when`(listarStub.listarTodas(requestGrpc))
            .thenReturn(createListChavePixResponse(clientIdExistente))

        //ação
        val request = HttpRequest.GET<Any>("/api/v1/clientes/pix/$clientIdExistente")
        val response = client.toBlocking().exchange(request, Map::class.java)

        //validação
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(response.body().values.size, 2)
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory() {
        @Singleton
        fun stubMockConsultar() =
            mock(KeymanagerConsultarGrpcServiceGrpc.KeymanagerConsultarGrpcServiceBlockingStub::class.java)

        @Singleton
        fun stubMockListar() =
            mock(KeymanagerListarTodasGrpcServiceGrpc.KeymanagerListarTodasGrpcServiceBlockingStub::class.java)
    }

    private fun createConsultarChavePixRequest(): ConsultarChavePixResponse {
        return ConsultarChavePixResponse
            .newBuilder()
            .setChave(
                ConsultarChavePixResponse.ChavePix.newBuilder().setTipo(TipoChave.EMAIL)
                    .setConta(
                        ConsultarChavePixResponse.ChavePix.ContaInfo.newBuilder().setTipo(TipoConta.CONTA_POUPANCA)
                            .setInstituicao("Itau Unibanco").setNomeDoTitular("Sizenando")
                            .setCpfDoTitular("04846598714")
                            .setAgencia("0001").setNumeroDaConta("1565987")
                    ).setChave("sizenando@gmail.com")
            ).setClienteId("5260263c-a3c1-4727-ae32-3bdb2538841b")
            .setPixId("0f89235e-6fd1-45d9-b989-f714fb881bcf")
            .build()
    }

    private fun createListChavePixResponse(idClient: String): ListarTodasChavesPixResponse {
        val chaveCpf = ListarTodasChavesPixResponse.ChavePix.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setTipoDeChave(TipoChave.CPF)
            .setValorChave("ListarTodasChavesPixResponse")
            .setTipoDeConta(TipoConta.CONTA_POUPANCA)
            .setCriadaEm(LocalDateTime.now().let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build()

        val chaveEmail = ListarTodasChavesPixResponse.ChavePix.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setTipoDeChave(TipoChave.EMAIL)
            .setValorChave("sizenando@gmail.com")
            .setTipoDeConta(TipoConta.CONTA_POUPANCA)
            .setCriadaEm(LocalDateTime.now().let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build()


        return ListarTodasChavesPixResponse.newBuilder()
            .setClientId(idClient)
            .addAllChaves(listOf(chaveCpf, chaveEmail))
            .build()
    }
}