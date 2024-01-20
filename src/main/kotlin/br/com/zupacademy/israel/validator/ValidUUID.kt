package br.com.zupacademy.israel.validator

import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.ReportAsSingleViolation
import javax.validation.constraints.Pattern
import kotlin.reflect.KClass

@ReportAsSingleViolation
@Constraint(validatedBy = [])
@Pattern(
    regexp = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}",
    flags = [Pattern.Flag.CASE_INSENSITIVE]
)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
annotation class ValidUUID(
    val message: String = "formato de UUID inválido",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

