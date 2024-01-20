package br.com.zupacademy.israel.registrarChavePix

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoDeChaveRequestTest {

    @Nested
    inner class CPF() {

        @Test
        fun `deve ser invalido quando valor em branco`() {
            assertFalse(TipoDeChaveRequest.CPF.valida(chave = "", context = null))
        }

        @Test
        fun `deve ser um tipo valido`() {
            assertTrue(TipoDeChaveRequest.CPF.valida(chave = "45242085002", context = null))
        }

        @Test
        fun `deve ser invalido quando formato invalido`() {
            assertFalse(TipoDeChaveRequest.CPF.valida(chave = "452420802", context = null))
        }

        @Test
        fun `deve ser um tipo invalido`() {
            assertTrue(TipoDeChaveRequest.CPF.valida(chave = "45242085002", context = null))
        }
    }

    @Nested
    inner class CELULAR() {

        @Test
        fun `deve ser invalido quando valor em branco`() {
            assertFalse(TipoDeChaveRequest.CELULAR.valida(chave = "", context = null))
        }

        @Test
        fun `deve ser invalido quando formato invalido`() {
            assertFalse(TipoDeChaveRequest.CELULAR.valida(chave = "71983369587", context = null))
        }

        @Test
        fun `deve um tipo valido`() {
            assertTrue(TipoDeChaveRequest.CELULAR.valida(chave = "+5571983306584", context = null))
        }
    }

    @Nested
    inner class EMAIL() {

        @Test
        fun `deve ser invalido quando valor em branco`() {
            assertFalse(TipoDeChaveRequest.EMAIL.valida(chave = "", context = null))
        }

        @Test
        fun `deve ser invalido quando tipo invalido`() {
            assertFalse(TipoDeChaveRequest.EMAIL.valida(chave = "sizenando.gmail.com", context = null))
        }

        @Test
        fun `deve ser um tipo valido`() {
            assertTrue(TipoDeChaveRequest.EMAIL.valida(chave = "sizenando@gmail.com", context = null))
        }
    }

    @Nested
    inner class ALEATORIA() {

        @Test
        fun `deve ser valido quando chave aleatoria for nula ou vazia`() {
            assertTrue(TipoDeChaveRequest.ALEATORIA.valida(chave = "", context = null))
        }

        @Test
        fun `deve ser invalido quando chave possui valor`() {
            assertFalse(TipoDeChaveRequest.ALEATORIA.valida(chave = "qualquer valor", context = null))
        }
    }

}