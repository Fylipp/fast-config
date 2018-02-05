package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class FloatMapper : SimpleMapper<Float>() {
    override fun serialize(t: Float): String = t.toString()
    override fun deserialize(s: String): Float = s.toFloat()
}
