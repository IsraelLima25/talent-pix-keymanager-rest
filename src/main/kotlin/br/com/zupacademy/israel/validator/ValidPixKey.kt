package br.com.zupacademy.israel.validator

import br.com.zupacademy.israel.registrarChavePix.NovaChavePixRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.TYPE
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [ValidPixValidator::class])
@Retention(RUNTIME)
@Target(CLASS, TYPE)
annotation class ValidPixKey(
    val message: String = "chave pix inválida",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ValidPixValidator : ConstraintValidator<ValidPixKey, NovaChavePixRequest> {

    override fun isValid(
        value: NovaChavePixRequest?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext
    ): Boolean {

        if(value?.tipoDeChave == null){
            return false
        }
        return value.tipoDeChave.valida(value.chave, context)
    }
}
