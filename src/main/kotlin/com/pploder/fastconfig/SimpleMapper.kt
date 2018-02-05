package com.pploder.fastconfig

import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty

abstract class SimpleMapper<T> : Mapper<T> {
    override fun read(props: Properties, param: KParameter, mappers: Map<KClass<*>, SimpleMapper<*>>): MappedArgument<T> {
        val value = props.getProperty(param.name)

        return if (value == null) {
            MappedArgument.None()
        } else {
            MappedArgument.Value(deserialize(value))

        }
    }

    override fun write(props: Properties, value: T, prop: KProperty<T>, mappers: Map<KClass<*>, SimpleMapper<*>>) {
        props.setProperty(prop.name, serialize(value))
    }

    abstract fun serialize(value: T): String

    abstract fun deserialize(propertyValue: String): T
}
