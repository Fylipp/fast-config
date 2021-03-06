package com.pploder.fastconfig

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class DbConnectionConfigTest {
    @Test
    fun testLoad() {
        val config = FastConfig.loadFromAbsoluteClasspath(DbConnectionConfig::class)

        assertEquals("localhost", config.address)
        assertEquals(1234, config.port)
        assertEquals("data", config.database)
        assertEquals("paul", "paul")
        assertEquals("verysecretpassword", config.password)
        assertEquals(mapOf(
                "ssl" to "true",
                "timeout" to "10"
        ), config.vendorSettings)
        assertEquals(listOf(12, 23, 45), config.ignoredErrorCodes)
    }
}
