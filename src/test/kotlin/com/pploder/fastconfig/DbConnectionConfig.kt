package com.pploder.fastconfig

data class DbConnectionConfig(
        val address: String = "localhost",
        val port: Int,
        val database: String,
        val username: String = "root",
        val password: String,
        val vendorSettings: Map<String, String>,
        val ignoredErrorCodes: List<Int>
) : FastConfig()
