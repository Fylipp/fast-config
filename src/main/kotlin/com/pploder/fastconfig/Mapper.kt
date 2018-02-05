package com.pploder.fastconfig

import com.pploder.fastconfig.mappers.*
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

interface Mapper<T> {
    fun read(props: Map<String, String>, paramName: String, paramType: KType, mappers: Map<KClass<*>, Mapper<*>>): MappedArgument<T>
    fun write(props: MutableMap<String, String>, value: T, propName: String, propType: KType, mappers: Map<KClass<*>, Mapper<*>>)
}

sealed class MappedArgument<T> {
    data class Value<T>(val value: T) : MappedArgument<T>()
    class None<T> : MappedArgument<T>()
}

val mappers = mapOf<KClass<*>, Mapper<*>>(
        String::class to StringMapper(),
        Char::class to CharMapper(),
        Boolean::class to BooleanMapper(),
        Int::class to IntMapper(),
        Long::class to LongMapper(),
        Float::class to FloatMapper(),
        Double::class to DoubleMapper(),
        Map::class to MapMapper()
)

fun <T> write(value: T, props: Properties, prop: KProperty<T>) {
    val propClass = prop.returnType.jvmErasure
    val mapper = mappers[propClass]

    if (mapper == null) {
        throw FastConfigException("No serializer available for type: $propClass")
    } else {
        @Suppress("UNCHECKED_CAST")
        (mapper as Mapper<T>).write(props as MutableMap<String, String>, value, prop.name, prop.returnType, mappers)
    }
}

fun <T> read(props: Properties, param: KParameter): MappedArgument<T> {
    val propType = param.type.jvmErasure
    val mapper = mappers[propType]

    if (mapper == null) {
        throw FastConfigException("No deserializer available for type: $propType")
    } else {
        @Suppress("UNCHECKED_CAST")
        return (mapper as Mapper<T>).read(props as Map<String, String>, param.name!!, param.type, mappers)
    }
}
