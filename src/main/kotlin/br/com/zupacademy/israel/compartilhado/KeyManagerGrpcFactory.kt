package br.com.zupacademy.israel.compartilhado

import br.com.zupacademy.israel.KeymanagerConsultarGrpcServiceGrpc
import br.com.zupacademy.israel.KeymanagerListarTodasGrpcServiceGrpc
import br.com.zupacademy.israel.KeymanagerRegistrarGrpcServiceGrpc
import br.com.zupacademy.israel.KeymanagerRemoverGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registrarChave() = KeymanagerRegistrarGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removerChave() = KeymanagerRemoverGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultarChave() = KeymanagerConsultarGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listarTodasChaves() = KeymanagerListarTodasGrpcServiceGrpc.newBlockingStub(channel)

}