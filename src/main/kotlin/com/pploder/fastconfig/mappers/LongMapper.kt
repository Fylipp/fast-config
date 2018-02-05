package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class LongMapper : SimpleMapper<Long>() {
    override fun serialize(value: Long): String = value.toString()
    override fun deserialize(propertyValue: String): Long = propertyValue.trim().toLong()
}
