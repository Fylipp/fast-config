package com.pploder.fastconfig.mappers

import com.pploder.fastconfig.FastConfigException
import com.pploder.fastconfig.OneToOneMapper

class BooleanMapper : OneToOneMapper<Boolean>() {
    override fun serialize(t: Boolean): String = t.toString()
    override fun deserialize(s: String): Boolean = when (s.trim().toLowerCase()) {
        in listOf("true", "yes", "on", "1") -> true
        in listOf("false", "no", "off", "0") -> false
        else -> throw FastConfigException("Value cannot be parsed as boolean value: $s")
    }
}
