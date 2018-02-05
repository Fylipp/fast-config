package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.FastConfigException
import com.pploder.fastconfig.SimpleMapper

class BooleanMapper : SimpleMapper<Boolean>() {
    override fun serialize(value: Boolean): String = value.toString()
    override fun deserialize(propertyValue: String): Boolean = when (propertyValue.trim().toLowerCase()) {
        in listOf("true", "yes", "on", "1") -> true
        in listOf("false", "no", "off", "0") -> false
        else -> throw FastConfigException("Value cannot be parsed as boolean value: $propertyValue")
    }
}
