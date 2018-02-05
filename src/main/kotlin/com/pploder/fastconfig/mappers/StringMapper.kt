package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.SimpleMapper

class StringMapper : SimpleMapper<String>() {
    override fun serialize(t: String): String = t
    override fun deserialize(s: String): String = s
}
