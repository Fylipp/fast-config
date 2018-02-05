package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class DoubleMapper : SimpleMapper<Double>() {
    override fun serialize(value: Double): String = value.toString()
    override fun deserialize(propertyValue: String): Double = propertyValue.trim().toDouble()
}
