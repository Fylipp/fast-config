# fast-config

Effortlessly use configuration files in Kotlin.

The aim is not to provide a one-size-fits-all solution for complex configurations,
but rather to allow for quick setup of basic configuration files and a convenient
yet minimal API.

Supported types: `String`, `Boolean`, `Int`, `Long`, `Float`, `Double`, `Char`

## Example
```kotlin
data class DbConnectionConfig(
        val address: String = "localhost",
        val port: Int,
        val database: String,
        val username: String = "root",
        val password: String
) : FastConfig()
```

```properties
# db.properties
port=1234
database=data
username=paul
password=verysecretpassword
```

```kotlin
// Loading
val config = FastConfig.loadFromFile(DbConnectionConfig::class, "db.properties")

// Saving
config.saveToFile("db.properties")
```

## Installation

Since **fast-config** is served via [JitPack](https://jitpack.io) a custom repository is required.
```xml
<dependency>
    <groupId>com.github.Fylipp</groupId>
    <artifactId>fast-config</artifactId>
    <version>v1.0.0</version>
</dependency>
```

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

## License
MIT.