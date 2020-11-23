package io.github.ccjhr.kotlinutils

import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility.PRIVATE
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

inline fun <reified T: Any> T.isEqualToIgnoringFields(other: T, vararg props: KProperty<*>): Boolean {
    if (this::class != other::class) return false

    val fetchProperties = { obj: Any ->
        obj::class.memberProperties
            .map { it as KProperty1<T, *> }
            .map { it.apply { it.isAccessible = true } }
            .map { it.name to it }.toMap() }

    val reverseAccessibility = { obj: Any ->
        obj::class.memberProperties
            .map { it as KProperty1<T, *> }
            .filter { it.visibility == PRIVATE || it.isFinal }
            .map { it.apply { it.isAccessible = false } }
    }

    val thisProperties = fetchProperties(this)
    val otherProperties = fetchProperties(other)
    val ignoredProperties = props.map { it.name }.toSet()

    val result = !thisProperties.filterNot { ignoredProperties.contains(it.key) }
        .map { map -> map.value.get(this) == otherProperties[map.key]?.get(other) }
        .contains(false)

    reverseAccessibility(this)
    reverseAccessibility(other)

    return result
}
