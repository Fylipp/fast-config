package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class FloatMapper : SimpleMapper<Float>() {
    override fun serialize(value: Float): String = value.toString()
    override fun deserialize(propertyValue: String): Float = propertyValue.toFloat()
}
