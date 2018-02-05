package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class DoubleMapper : SimpleMapper<Double>() {
    override fun serialize(t: Double): String = t.toString()
    override fun deserialize(s: String): Double = s.toDouble()
}
