package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.*
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.jvmErasure

class ListMapper : Mapper<List<*>> {
    override fun read(props: Properties, param: KParameter, mappers: Map<KClass<*>, SimpleMapper<*>>): MappedArgument<List<*>> {
        val contentClass = param.type.arguments[0].type!!.jvmErasure

        val mapper = simpleMappers[contentClass]
                ?: throw FastConfigException("List content type has no mapper associated with it: $contentClass")

        val raw = props.getProperty(param.name!!)
                ?: return MappedArgument.None()

        val mapped = raw
                .split(',')
                .map { mapper.deserialize(it) }

        return MappedArgument.Value(mapped)
    }

    override fun write(props: Properties, value: List<*>, prop: KProperty<List<*>>, mappers: Map<KClass<*>, SimpleMapper<*>>) {
        val contentClass = prop.returnType.arguments[0].type!!.jvmErasure

        @Suppress("UNCHECKED_CAST")
        val mapper = simpleMappers[contentClass] as SimpleMapper<Any?>?
                ?: throw FastConfigException("List content type has no mapper associated with it: $contentClass")

        val mapped = value.map { mapper.serialize(it) }.fold("", String::plus)

        @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
        props.setProperty(prop.name!!, mapped)
    }
}
