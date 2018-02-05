package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class CharMapper : SimpleMapper<Char>() {
    override fun serialize(value: Char): String = value.toString()
    override fun deserialize(propertyValue: String): Char = propertyValue.single()
}
