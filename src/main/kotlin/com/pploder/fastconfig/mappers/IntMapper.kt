package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class IntMapper : SimpleMapper<Int>() {
    override fun serialize(t: Int): String = t.toString()
    override fun deserialize(s: String): Int = s.toInt()
}
