package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.FastConfigException
import com.pploder.fastconfig.MappedArgument
import com.pploder.fastconfig.Mapper
import com.pploder.fastconfig.SimpleMapper
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.jvmErasure

class MapMapper : Mapper<Map<*, *>> {
    override fun read(props: Properties, param: KParameter, mappers: Map<KClass<*>, SimpleMapper<*>>): MappedArgument<Map<*, *>> {
        val keyClass = param.type.arguments[0].type!!.jvmErasure
        val valueClass = param.type.arguments[1].type!!.jvmErasure

        val keyMapper = mappers[keyClass]
        val valueMapper = mappers[valueClass]

        if (keyMapper == null) {
            throw FastConfigException("Map key type has no mapper associated with it: $keyClass")
        }

        if (valueMapper == null) {
            throw FastConfigException("Map value type has no mapper associated with it: $valueClass")
        }

        val prefix = param.name!!

        return MappedArgument.Value(mapOf(*props.keys
                .map { it as String }
                .filter { it.startsWith(prefix) }
                .map { k -> Pair(keyMapper.deserialize(k.removePrefix("$prefix.")), valueMapper.deserialize(props.getProperty(k))) }
                .toTypedArray()))
    }

    override fun write(props: Properties, value: Map<*, *>, prop: KProperty<Map<*, *>>, mappers: Map<KClass<*>, SimpleMapper<*>>) {
        val keyClass = prop.returnType.arguments[0].type!!.jvmErasure
        val valueClass = prop.returnType.arguments[1].type!!.jvmErasure

        @Suppress("UNCHECKED_CAST")
        val keyMapper = mappers[keyClass] as SimpleMapper<Any>?

        @Suppress("UNCHECKED_CAST")
        val valueMapper = mappers[valueClass] as SimpleMapper<Any>?

        if (keyMapper == null) {
            throw FastConfigException("Map key type has no mapper associated with it: $keyClass")
        }

        if (valueMapper == null) {
            throw FastConfigException("Map value type has no mapper associated with it: $valueClass")
        }

        value.forEach({ k, v ->
            props.setProperty(keyMapper.serialize(k!!), valueMapper.serialize(v!!))
        })
    }
}
