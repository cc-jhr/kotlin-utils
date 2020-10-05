package io.github.ccjhr.kotlinutils

import org.springframework.jdbc.support.rowset.SqlRowSet
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData
import java.math.BigDecimal
import java.sql.Time
import java.sql.Timestamp
import java.util.*
import kotlin.reflect.KClass

/**
 * This class is an [Iterator] and lets you iterate a sql database result set. By using [SqlRowSet] not all results
 * mapped as objects will be loaded into memory at once. Therefore using this class has a lower memory footprint on huge result sets.
 *
 * **Usage:**
 * ```
 * fun retrieveElements(): Iterator<MyObject> {
 *   val parameters = mapOf()
 *   val sqlRowSet = namedParameterJdbcTemplate.queryForRowSet("", parameters)
 *
 *    return DatabaseCursor(
 *      sqlRowSet = sqlRowSet,
 *      objectMapper = { sqlRow ->
 *        MyObject(value = sqlRow.getString("value"))
 *      }
 *    )
 * }
 *
 * ```
 * @param sqlRowSet Result wrapped in a row set
 * @param objectMapper A lambda which creates the object. Input value is an [SqlRow] which let's you access all columns.
 */
class DatabaseCursor<T: Any>(
    private val sqlRowSet: SqlRowSet,
    private val objectMapper: (SqlRow) -> T
) : Iterator<T> {

    override fun hasNext(): Boolean {
        val hasNext = sqlRowSet.next()
        sqlRowSet.previous()
        return hasNext
    }

    override fun next(): T {
        sqlRowSet.next()
        return objectMapper.invoke(SqlRowDelegate(sqlRowSet))
    }
}

interface SqlRow {

    /**
     * @see SqlRowSet.getMetaData
     */
    fun getMetaData(): SqlRowSetMetaData

    /**
     * @see SqlRowSet.findColumn
     */
    fun findColumn(columnLabel: String): Int

    /**
     * @see SqlRowSet.getBigDecimal
     */
    fun getBigDecimal(columnIndex: Int): BigDecimal?

    /**
     * @see SqlRowSet.getBigDecimal
     */
    fun getBigDecimal(columnLabel: String): BigDecimal?

    /**
     * @see SqlRowSet.getBoolean
     */
    fun getBoolean(columnIndex: Int): Boolean

    /**
     * @see SqlRowSet.getBoolean
     */
    fun getBoolean(columnLabel: String): Boolean

    /**
     * @see SqlRowSet.getByte
     */
    fun getByte(columnIndex: Int): Byte

    /**
     * @see SqlRowSet.getByte
     */
    fun getByte(columnLabel: String): Byte

    /**
     * @see SqlRowSet.getDate
     */
    fun getDate(columnIndex: Int): Date?

    /**
     * @see SqlRowSet.getDate
     */
    fun getDate(columnLabel: String): Date?

    /**
     * @see SqlRowSet.getDate
     */
    fun getDate(columnIndex: Int, cal: Calendar): Date?

    /**
     * @see SqlRowSet.getDate
     */
    fun getDate(columnLabel: String, cal: Calendar): Date?

    /**
     * @see SqlRowSet.getDouble
     */
    fun getDouble(columnIndex: Int): Double

    /**
     * @see SqlRowSet.getDouble
     */
    fun getDouble(columnLabel: String): Double

    /**
     * @see SqlRowSet.getFloat
     */
    fun getFloat(columnIndex: Int): Float

    /**
     * @see SqlRowSet.getFloat
     */
    fun getFloat(columnLabel: String): Float

    /**
     * @see SqlRowSet.getInt
     */
    fun getInt(columnIndex: Int): Int

    /**
     * @see SqlRowSet.getInt
     */
    fun getInt(columnLabel: String): Int

    /**
     * @see SqlRowSet.getLong
     */
    fun getLong(columnIndex: Int): Long

    /**
     * @see SqlRowSet.getLong
     */
    fun getLong(columnLabel: String): Long

    /**
     * @see SqlRowSet.getNString
     */
    fun getNString(columnIndex: Int): String?

    /**
     * @see SqlRowSet.getNString
     */
    fun getNString(columnLabel: String): String?

    /**
     * @see SqlRowSet.getObject
     */
    fun getObject(columnIndex: Int): Any?

    /**
     * @see SqlRowSet.getObject
     */
    fun getObject(columnLabel: String): Any?

    /**
     * @see SqlRowSet.getObject
     */
    fun getObject(columnIndex: Int, map: Map<String, Class<*>> ): Any?

    /**
     * @see SqlRowSet.getObject
     */
    fun getObject(columnLabel: String, map: Map<String, Class<*>>): Any?

    /**
     * @see SqlRowSet.getObject
     */
    fun <T: Any> getObject(columnIndex: Int, type: KClass<T>): T?

    /**
     * @see SqlRowSet.getObject
     */
    fun <T: Any> getObject(columnLabel: String, type: KClass<T>): T?

    /**
     * @see SqlRowSet.getShort
     */
    fun getShort(columnIndex: Int): Short

    /**
     * @see SqlRowSet.getShort
     */
    fun getShort(columnLabel: String): Short

    /**
     * @see SqlRowSet.getString
     */
    fun getString(columnIndex: Int): String?

    /**
     * @see SqlRowSet.getString
     */
    fun getString(columnLabel: String): String?

    /**
     * @see SqlRowSet.getTime
     */
    fun getTime(columnIndex: Int): Time?

    /**
     * @see SqlRowSet.getTime
     */
    fun getTime(columnLabel: String): Time?

    /**
     * @see SqlRowSet.getTime
     */
    fun getTime(columnIndex: Int, cal: Calendar): Time?

    /**
     * @see SqlRowSet.getTime
     */
    fun getTime(columnLabel: String, cal: Calendar): Time?

    /**
     * @see SqlRowSet.getTimestamp
     */
    fun getTimestamp(columnIndex: Int): Timestamp?

    /**
     * @see SqlRowSet.getTimestamp
     */
    fun getTimestamp(columnLabel: String): Timestamp?

    /**
     * @see SqlRowSet.getTimestamp
     */
    fun getTimestamp(columnIndex: Int, cal: Calendar): Timestamp?

    /**
     * @see SqlRowSet.getTimestamp
     */
    fun getTimestamp(columnLabel: String, cal: Calendar): Timestamp?

    /**
     * @see SqlRowSet.getRow
     */
    fun getRow(): Int
}

class SqlRowDelegate(private val sqlRowSet: SqlRowSet): SqlRow {

    override fun getMetaData(): SqlRowSetMetaData = sqlRowSet.metaData

    override fun findColumn(columnLabel: String): Int = sqlRowSet.findColumn(columnLabel)

    override fun getBigDecimal(columnIndex: Int): BigDecimal? = sqlRowSet.getBigDecimal(columnIndex)

    override fun getBigDecimal(columnLabel: String): BigDecimal? = sqlRowSet.getBigDecimal(columnLabel)

    override fun getBoolean(columnIndex: Int): Boolean = sqlRowSet.getBoolean(columnIndex)

    override fun getBoolean(columnLabel: String): Boolean = sqlRowSet.getBoolean(columnLabel)

    override fun getByte(columnIndex: Int): Byte = sqlRowSet.getByte(columnIndex)

    override fun getByte(columnLabel: String): Byte = sqlRowSet.getByte(columnLabel)

    override fun getDate(columnIndex: Int): Date? = sqlRowSet.getDate(columnIndex)

    override fun getDate(columnLabel: String): Date? = sqlRowSet.getDate(columnLabel)

    override fun getDate(columnIndex: Int, cal: Calendar): Date? = sqlRowSet.getDate(columnIndex, cal)

    override fun getDate(columnLabel: String, cal: Calendar): Date? = sqlRowSet.getDate(columnLabel, cal)

    override fun getDouble(columnIndex: Int): Double = sqlRowSet.getDouble(columnIndex)

    override fun getDouble(columnLabel: String): Double = sqlRowSet.getDouble(columnLabel)

    override fun getFloat(columnIndex: Int): Float = sqlRowSet.getFloat(columnIndex)

    override fun getFloat(columnLabel: String): Float = sqlRowSet.getFloat(columnLabel)

    override fun getInt(columnIndex: Int): Int = sqlRowSet.getInt(columnIndex)

    override fun getInt(columnLabel: String): Int = sqlRowSet.getInt(columnLabel)

    override fun getLong(columnIndex: Int): Long = sqlRowSet.getLong(columnIndex)

    override fun getLong(columnLabel: String): Long = sqlRowSet.getLong(columnLabel)

    override fun getNString(columnIndex: Int): String? = sqlRowSet.getNString(columnIndex)

    override fun getNString(columnLabel: String): String? = sqlRowSet.getNString(columnLabel)

    override fun getObject(columnIndex: Int): Any? = sqlRowSet.getObject(columnIndex)

    override fun getObject(columnLabel: String): Any? = sqlRowSet.getObject(columnLabel)

    override fun getObject(columnIndex: Int, map: Map<String, Class<*>>): Any? = sqlRowSet.getObject(columnIndex, map)

    override fun getObject(columnLabel: String, map: Map<String, Class<*>>): Any? = sqlRowSet.getObject(columnLabel, map)

    override fun <T : Any> getObject(columnIndex: Int, type: KClass<T>): T? = sqlRowSet.getObject(columnIndex, type.java)

    override fun <T : Any> getObject(columnLabel: String, type: KClass<T>): T? = sqlRowSet.getObject(columnLabel, type.java)

    override fun getShort(columnIndex: Int): Short = sqlRowSet.getShort(columnIndex)

    override fun getShort(columnLabel: String): Short = sqlRowSet.getShort(columnLabel)

    override fun getString(columnIndex: Int): String? = sqlRowSet.getString(columnIndex)

    override fun getString(columnLabel: String): String? = sqlRowSet.getString(columnLabel)

    override fun getTime(columnIndex: Int): Time? = sqlRowSet.getTime(columnIndex)

    override fun getTime(columnLabel: String): Time? = sqlRowSet.getTime(columnLabel)

    override fun getTime(columnIndex: Int, cal: Calendar): Time? = sqlRowSet.getTime(columnIndex, cal)

    override fun getTime(columnLabel: String, cal: Calendar): Time? = sqlRowSet.getTime(columnLabel, cal)

    override fun getTimestamp(columnIndex: Int): Timestamp? = sqlRowSet.getTimestamp(columnIndex)

    override fun getTimestamp(columnLabel: String): Timestamp? = sqlRowSet.getTimestamp(columnLabel)

    override fun getTimestamp(columnIndex: Int, cal: Calendar): Timestamp? = sqlRowSet.getTimestamp(columnIndex, cal)

    override fun getTimestamp(columnLabel: String, cal: Calendar): Timestamp? = sqlRowSet.getTimestamp(columnLabel, cal)

    override fun getRow(): Int = sqlRowSet.row
}
