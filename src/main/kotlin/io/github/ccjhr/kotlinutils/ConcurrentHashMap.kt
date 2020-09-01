package io.github.ccjhr.kotlinutils

import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ConcurrentHashMap as JavaConcurrentHashMap

class ConcurrentHashMap<KEY, VALUE>: Map<KEY, VALUE>, ConcurrentMap<KEY, VALUE> {

    private val delegate: JavaConcurrentHashMap<KEY, Value> = JavaConcurrentHashMap()

    override val keys: MutableSet<KEY>
        get() = delegate.keys

    override val size: Int = delegate.size

    override fun containsKey(key: KEY): Boolean = delegate.containsKey(key)

    override fun containsValue(value: VALUE): Boolean = delegate.containsValue(Entry(value))

    override fun isEmpty(): Boolean = delegate.isEmpty()

    override fun clear() = delegate.clear()

    override fun remove(key: KEY, value: VALUE): Boolean = delegate.remove(key, Entry(value))

    override fun replace(key: KEY, oldValue: VALUE, newValue: VALUE): Boolean = delegate.replace(key, Entry(oldValue), Entry(newValue))

    override fun replace(key: KEY, value: VALUE): VALUE? {
        val previousValue = get(key)
        delegate.replace(key, Entry(value))

        return previousValue
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(key: KEY): VALUE? {
        return when(val entry = delegate[key]) {
            Empty -> null
            is Entry<*> -> entry.value as VALUE
            null -> null
        }
    }

    override fun put(key: KEY, value: VALUE): VALUE? {
        val previousValue = get(key)
        delegate[key] = Entry(value)

        return previousValue
    }

    override fun putIfAbsent(key: KEY, value: VALUE): VALUE? {
        val previousValue = get(key)
        delegate.putIfAbsent(key, Entry(value))

        return previousValue
    }

    override fun remove(key: KEY): VALUE? {
        val previousValue = get(key)
        delegate.remove(key)

        return previousValue
    }

    override fun putAll(from: Map<out KEY, VALUE>) {
        val convertedMap = from.map { (key, value) ->
            key to Entry(value)
        }.toMap()

        delegate.putAll(convertedMap)
    }




    override val entries: MutableSet<MutableMap.MutableEntry<KEY, VALUE>>
        get() = TODO("Not yet implemented")

/*
        override val entries: MutableSet<MutableMap.MutableEntry<KEY, VALUE>>
        get() = delegate.entries.map { (key, value) ->
            when(value) {
                Empty -> SimpleMutableEntry(key, null)
                is Entry<*> -> SimpleMutableEntry(key, (value.value as VALUE))
            }
        }.toMutableSet()
    */
    override val values: MutableCollection<VALUE>
        get() = TODO("Not yet implemented")
}

private sealed class Value
private object Empty : Value()
private data class Entry<T>(val value: T) : Value()

private class SimpleMutableEntry<K, V>(
    override val key: K,
    override var value: V
) : MutableMap.MutableEntry<K, V> {

    override fun setValue(newValue: V): V {
        val previousValue = value
        value = newValue
        return previousValue
    }
}
