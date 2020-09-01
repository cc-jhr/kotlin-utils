# kotlin-util

## ConcurrentHashMap

I tried to use `java.util.concurrent.ConcurrentHashMap` in kotlin using a nullable value.

```kotlin
import java.util.concurrent.ConcurrentHashMap

fun main() {
    val cm = ConcurrentHashMap<String, Int?>()

    cm["test"] = null
}
```
This resulted in a `NullpointerException`. So I created a kotlin wrapper which allows the usage of nullable types.
