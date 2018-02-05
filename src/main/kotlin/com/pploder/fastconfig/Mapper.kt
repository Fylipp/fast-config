package com.pploder.fastconfig

import com.pploder.fastconfig.mappers.*
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.jvmErasure

interface Mapper<T> {
    fun read(props: Properties, param: KParameter, mappers: Map<KClass<*>, OneToOneMapper<*>>): MappedArgument<T>
    fun write(props: Properties, value: T, prop: KProperty<T>, mappers: Map<KClass<*>, OneToOneMapper<*>>)
}

sealed class MappedArgument<T> {
    data class Value<T>(val value: T) : MappedArgument<T>()
    class None<T> : MappedArgument<T>()
}

val simpleMappers = mapOf<KClass<*>, OneToOneMapper<*>>(
        String::class to StringMapper(),
        Char::class to CharMapper(),
        Boolean::class to BooleanMapper(),
        Int::class to IntMapper(),
        Long::class to LongMapper(),
        Float::class to FloatMapper(),
        Double::class to DoubleMapper()
)

val mappers = mapOf<KClass<*>, Mapper<*>>() + simpleMappers

fun <T> write(value: T, props: Properties, prop: KProperty<T>) {
    val propType = prop.returnType.jvmErasure
    val mapper = mappers[propType]

    if (mapper == null) {
        throw FastConfigException("No serializer available for type: $propType")
    } else {
        @Suppress("UNCHECKED_CAST")
        (mapper as Mapper<T>).write(props, value, prop, simpleMappers)
    }
}

fun <T> read(props: Properties, param: KParameter): MappedArgument<T> {
    val propType = param.type.jvmErasure
    val mapper = mappers[propType]

    if (mapper == null) {
        throw FastConfigException("No deserializer available for type: $propType")
    } else {
        @Suppress("UNCHECKED_CAST")
        return (mapper as Mapper<T>).read(props, param, simpleMappers)
    }
}
