package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class StringMapper : SimpleMapper<String>() {
    override fun serialize(value: String): String = value
    override fun deserialize(propertyValue: String): String = propertyValue
}
