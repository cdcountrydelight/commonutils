package com.cd.utility.utils


import java.math.BigDecimal
import java.math.BigInteger

inline fun FIXME(): Nothing = throw Error("An operation needs a fix.")

inline fun FIXME(reason: String): Nothing = throw Error("An operation needs a fix: $reason")

inline fun loop(block: () -> Unit) = run { while (true) block() }



inline infix operator fun Int.times(predicate: (Int) -> Unit) = repeat(this, predicate)

inline infix fun <T> T.with(noinline block: (T.() -> T)?): T = block?.invoke(this) ?: this

inline fun <T> T.asNullable(): T? = this

inline fun <T> T?.isNull(): Boolean = this == null
inline fun <T> T?.isNotNull(): Boolean = this != null

inline fun defaultByte(): Byte = 0
inline fun defaultShort(): Short = 0
inline fun defaultInt(): Int = 0
inline fun defaultLong(): Long = 0L
inline fun defaultFloat(): Float = 0f
inline fun defaultDouble(): Double = 0.0
inline fun defaultBoolean(): Boolean = false
inline fun defaultBigInteger(): BigInteger = BigInteger.ZERO
inline fun defaultBigDecimal(): BigDecimal = BigDecimal.ZERO
inline fun defaultString(): String = ""

inline fun <T, R> T?.letOrElse(nullBlock: () -> R, block: (T) -> R): R = this?.let(block) ?: nullBlock()

inline fun <T, R> T?.letOrElse(nullValue: R, block: (T) -> R): R = letOrElse({ nullValue }, block)

inline fun Boolean.letIfTrue(block: () -> Unit): Unit = letIfTrue(block, {})

inline fun Boolean.letIfFalse(block: () -> Unit) = letIfTrue({}, block)

inline fun <R> Boolean.letIfTrue(ifBlock: () -> R, elseBlock: () -> R): R = letIf({ this }, { run(ifBlock) }, { run(elseBlock) })

inline fun <T> T.letIf(condition: T.() -> Boolean, ifBlock: (T) -> Unit): Unit = letIf(condition, ifBlock) {}

inline fun <T, R> T.letIf(condition: T.() -> Boolean, ifBlock: (T) -> R, elseBlock: (T) -> R): R = if (run(condition)) run(ifBlock) else run(elseBlock)

inline fun Boolean.alsoIfTrue(block: () -> Unit): Boolean = alsoIfTrue(block, {})

inline fun Boolean.alsoIfFalse(block: () -> Unit): Boolean = alsoIfTrue({}, block)

inline fun Boolean.alsoIfTrue(ifBlock: () -> Unit, elseBlock: () -> Unit): Boolean = alsoIf({ this }, { run(ifBlock) }, { run(elseBlock) })

inline fun <T> T.alsoIf(condition: T.() -> Boolean, ifBlock: (T) -> Unit): T = alsoIf(condition, ifBlock) {}

inline fun <T, R> T.alsoIf(condition: T.() -> Boolean, ifBlock: (T) -> R, elseBlock: (T) -> R): T = also { if (run(condition)) run(ifBlock) else run(elseBlock) }

inline infix fun <T> (() -> T).butBefore(crossinline block: () -> Unit): () -> T = { block(); this() }

inline infix fun <T, R> ((T) -> R).butBefore(crossinline block: () -> T): () -> R = { this(block()) }

inline infix fun <T, R> (() -> T).andThen(crossinline block: T.() -> R): () -> R = { block(this()) }

inline infix fun <T> (() -> T).andThrow(crossinline throwable: T.() -> Throwable): () -> Nothing = andThen { throw throwable() }
inline infix fun <T> (() -> T).andThrow(throwable: Throwable): () -> Nothing = andThrow { throwable }

inline infix fun <T, R> (() -> T).andReturn(value: R): () -> R = andThen { value }

inline fun <T, R> (() -> T).returnValue(value: R): () -> R = andReturn(value)
inline fun <T> (() -> T).returnByte(): () -> Byte = andReturn(defaultByte())
inline fun <T> (() -> T).returnShort(): () -> Short = andReturn(defaultShort())
inline fun <T> (() -> T).returnInt(): () -> Int = andReturn(defaultInt())
inline fun <T> (() -> T).returnLong(): () -> Long = andReturn(defaultLong())
inline fun <T> (() -> T).returnFloat(): () -> Float = andReturn(defaultFloat())
inline fun <T> (() -> T).returnDouble(): () -> Double = andReturn(defaultDouble())
inline fun <T> (() -> T).returnBigInteger(): () -> BigInteger = andReturn(defaultBigInteger())
inline fun <T> (() -> T).returnBigDecimal(): () -> BigDecimal = andReturn(defaultBigDecimal())
inline fun <T> (() -> T).returnString(): () -> String = andReturn(defaultString())
inline fun <T> (() -> T).returnTrue(): () -> Boolean = andReturn(true)
inline fun <T> (() -> T).returnFalse(): () -> Boolean = andReturn(false)
inline fun <T> (() -> T).returnBoolean(): () -> Boolean = andReturn(defaultBoolean())
inline fun <T> (() -> T).returnNull(): () -> T? = andReturn(null)
inline fun <T> (() -> T).returnUnit(): () -> Unit = andReturn(Unit)

