package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.OneToOneMapper

class CharMapper : OneToOneMapper<Char>() {
    override fun serialize(t: Char): String = t.toString()
    override fun deserialize(s: String): Char = s.single()
}
