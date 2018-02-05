package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class IntMapper : SimpleMapper<Int>() {
    override fun serialize(value: Int): String = value.toString()
    override fun deserialize(propertyValue: String): Int = propertyValue.trim().toInt()
}
