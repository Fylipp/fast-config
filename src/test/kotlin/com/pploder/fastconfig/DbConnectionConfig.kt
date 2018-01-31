package com.pploder.fastconfig

data class DbConnectionConfig(
        val address: String = "localhost",
        val port: Int,
        val database: String,
        val username: String = "root",
        val password: String
) : FastConfig()