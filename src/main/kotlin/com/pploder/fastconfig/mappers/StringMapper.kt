package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.OneToOneMapper

class StringMapper : OneToOneMapper<String>() {
    override fun serialize(t: String): String = t
    override fun deserialize(s: String): String = s
}
