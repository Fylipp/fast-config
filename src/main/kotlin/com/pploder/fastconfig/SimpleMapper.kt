package com.pploder.fastconfig

import kotlin.reflect.KClass
import kotlin.reflect.KType

abstract class SimpleMapper<T> : Mapper<T> {
    override fun read(props: Map<String, String>, paramName: String, paramType: KType, mappers: Map<KClass<*>, Mapper<*>>): MappedArgument<T> {
        val value = props[paramName]

        return if (value == null) {
            MappedArgument.None()
        } else {
            MappedArgument.Value(deserialize(value))

        }
    }

    override fun write(props: MutableMap<String, String>, value: T, propName: String, propType: KType, mappers: Map<KClass<*>, Mapper<*>>) {
        props[propName] = serialize(value)
    }

    abstract fun serialize(value: T): String

    abstract fun deserialize(propertyValue: String): T
}
