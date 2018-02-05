package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class LongMapper : SimpleMapper<Long>() {
    override fun serialize(t: Long): String = t.toString()
    override fun deserialize(s: String): Long = s.toLong()
}
