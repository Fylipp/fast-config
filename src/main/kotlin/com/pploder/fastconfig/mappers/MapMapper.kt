package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.FastConfigException
import com.pploder.fastconfig.MappedArgument
import com.pploder.fastconfig.Mapper
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

class MapMapper : Mapper<Map<String, *>> {
    override fun read(props: Map<String, String>, paramName: String, paramType: KType, mappers: Map<KClass<*>, Mapper<*>>): MappedArgument<Map<String, *>> {
        val valueType = paramType.arguments[1].type!!
        val valueClass = valueType.jvmErasure

        @Suppress("UNCHECKED_CAST")
        val valueMapper = mappers[valueClass] as Mapper<Any?>?
                ?: throw FastConfigException("Map value type has no mapper associated with it: $valueClass")

        val prefix = "$paramName."

        val value = mapOf(*props.keys
                .filter { it.startsWith(prefix) }
                .map { it.removePrefix(prefix) }
                .map { Pair(it, props[it]!!) }
                .toTypedArray()
        )

        val mapped = mapOf(*value
                .flatMap {
                    val mapped = valueMapper.read(value, it.key, valueType, mappers)

                    if (mapped is MappedArgument.Value) {
                        listOf(Pair(it.key, mapped.value))
                    } else {
                        listOf()
                    }
                }
                .toTypedArray()
        )

        return MappedArgument.Value(mapped)
    }

    override fun write(props: MutableMap<String, String>, value: Map<String, *>, propName: String, propType: KType, mappers: Map<KClass<*>, Mapper<*>>) {
        val valueType = propType.arguments[1].type!!
        val valueClass = propType.jvmErasure

        @Suppress("UNCHECKED_CAST")
        val valueMapper = mappers[valueClass] as Mapper<Any?>?
                ?: throw FastConfigException("Map value type has no mapper associated with it: $valueClass")

        val subMap = mutableMapOf<String, String>()

        value.forEach { k, v -> valueMapper.write(subMap, v, k, valueType, mappers) }
        subMap.forEach { k, v -> props["$propName.$k"] = v }
    }
}
