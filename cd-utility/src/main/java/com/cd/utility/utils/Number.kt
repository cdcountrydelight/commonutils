package com.cd.utility.utils

import com.google.common.math.BigIntegerMath
import com.google.common.math.DoubleMath
import com.google.common.math.LongMath
import java.text.DecimalFormat
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.util.*
import kotlin.math.ln

fun Int?.nullToDefault() = this ?: -1
fun Int?.nullToZero() = this ?: 0
fun Int?.isTrue() = this == 1
fun Double?.isNullOrZero() = this == 0.0 || this == null
fun Double?.nullToDefault() = this ?: -1.0
fun Double?.nullToZero() = this ?: 0.0
fun Double?.convertToPrice(currency: String) = String.format("%s%s", this.nullToZero().formatMoney(), currency)

fun Double.formatMoney(): String {
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(this)
}


val LOG2 by lazy { ln(2.0) }

val TWO_BIG: BigInteger by lazy { BigInteger.valueOf(2) }

fun Long.toBigInteger() = BigInteger(toString())
fun Number.toBigInteger() = toLong().toBigInteger()

fun Double.toBigDecimal(mathContext: MathContext = MathContext.UNLIMITED) = BigDecimal(this, mathContext)
fun Number.toBigDecimal(mathContext: MathContext = MathContext.UNLIMITED) = toDouble().toBigDecimal(mathContext)

fun BigInteger.even() = mod(2L.toBigInteger()) == BigInteger.ZERO
fun BigInteger.odd() = !even()

fun BigInteger.fitsInLong(): Boolean = this in (Long.MIN_VALUE.toBigInteger()..Long.MAX_VALUE.toBigInteger())
fun BigInteger.fitsInInt(): Boolean = this in (Int.MIN_VALUE.toBigInteger()..Int.MAX_VALUE.toBigInteger())

fun BigDecimal.fitsInDouble(): Boolean = this in (-Double.MAX_VALUE.toBigDecimal()..Double.MAX_VALUE.toBigDecimal())
fun BigDecimal.fitsInFloat(): Boolean = this in (-Float.MAX_VALUE.toBigDecimal()..Float.MAX_VALUE.toBigDecimal())

tailrec fun gcd(a: BigInteger, b: BigInteger): BigInteger = if (b == BigInteger.ZERO) a else if (a.fitsInLong() && b.fitsInLong()) gcd(
    a.toLong(),
    b.toLong()
).toBigInteger() else gcd(a, a % b)
fun lcm(a: BigInteger, b: BigInteger) = a * b / gcd(a, b)

fun BigDecimal.round(precision: Int = 1, roundingMode: RoundingMode = RoundingMode.HALF_UP): BigDecimal = round(MathContext(precision, roundingMode))

fun MathContext.round(n: BigDecimal): BigDecimal = run(n::round)

fun MathContext.roundFunctionBigDecimal(): (BigDecimal) -> BigDecimal = { round(it) }

fun Double.round(mathContext: MathContext = MathContext(1)): BigDecimal = run(mathContext::round)
fun Float.round(mathContext: MathContext = MathContext(1)): BigDecimal = run(mathContext::round)

fun Double.round(precision: Int = 1, roundingMode: RoundingMode = RoundingMode.HALF_UP): BigDecimal = round(MathContext(precision, roundingMode))
fun Float.round(precision: Int = 1, roundingMode: RoundingMode = RoundingMode.HALF_UP): BigDecimal = round(MathContext(precision, roundingMode))

fun MathContext.round(n: Double): BigDecimal = run(n.toBigDecimal()::round)
fun MathContext.round(n: Float): BigDecimal = run(n.toBigDecimal()::round)

fun MathContext.roundFunctionDouble(): (Double) -> BigDecimal = { round(it) }
fun MathContext.roundFunctionFloat(): (Float) -> BigDecimal = { round(it) }

fun Number.roundDiv(y: Number, mathContext: MathContext): BigDecimal = toBigDecimal().divide(y.toBigDecimal(), mathContext)

fun BigInteger.sqrt(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): BigInteger = BigIntegerMath.sqrt(this, roundingMode)
fun BigDecimal.sqrt(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): BigInteger = toBigInteger().sqrt(roundingMode)

fun BigInteger.isPowerOfTwo() = run(BigIntegerMath::isPowerOfTwo)
fun BigDecimal.isPowerOfTwo() = toBigInteger().isPowerOfTwo()

fun BigInteger.log10(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Int = BigIntegerMath.log10(this, roundingMode)
fun BigInteger.log2(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Int = BigIntegerMath.log2(this, roundingMode)
fun BigInteger.ln(): Double = log()
fun BigInteger.log(): Double {
    var ref = this
    val blex = bitLength() - 1022 // any value in 60..1023 is ok
    if (blex > 0) ref = shiftRight(blex)
    val res = ref.toDouble().log()
    return if (blex > 0) res + blex * LOG2 else res
}

fun Int.factorial(): BigInteger = run(BigIntegerMath::factorial)
fun Number.factorial(): BigInteger = toInt().factorial()

infix fun Int.binomial(k: Int): BigInteger = BigIntegerMath.binomial(this, k)
infix fun Number.binomial(k: Int): BigInteger = toInt() binomial k

fun Number.fibonacci(): BigInteger = toBigInteger().fibonacci()
fun BigInteger.fibonacci(): BigInteger {
    require(this >= BigInteger.ZERO) { "Cannot compute fibonacci for negative numbers" }
    fun fib(n: BigInteger): Pair<BigInteger, BigInteger> {
        if (n == BigInteger.ZERO) return BigInteger.ZERO to BigInteger.ONE
        val (a, b) = fib(n / TWO_BIG)
        val c = a * (b * TWO_BIG - a)
        val d = a * a + b * b
        return if (n % TWO_BIG == BigInteger.ZERO) c to d else d to c + d
    }
    return fib(this).first
}

fun probablePrime(bits: Int, random: Random = Random()) = BigInteger.probablePrime(bits, random)

fun BigInteger.isPrime(certainty: Int = 5, precise: Boolean = false): Boolean {
    if (!isProbablePrime(certainty)) return false else if (!precise) return true

    if (TWO_BIG != this && mod(TWO_BIG) == BigInteger.ZERO) return false

    var index = BigInteger.ONE
    val indices = generateSequence { index += TWO_BIG; if (index.multiply(index) <= this) index else null }
    indices.forEach {
        if (mod(it) == BigInteger.ZERO) return false
    }
    return true;
}


fun Long.even() = this % 2L == 0L
fun Number.even() = toLong().even()

fun Long.odd() = !even()
fun Number.odd() = toLong().odd()

fun Number.isInteger() = when (this) {
    is Double -> DoubleMath.isMathematicalInteger(this)
    is Float -> DoubleMath.isMathematicalInteger(toDouble())
    is BigDecimal -> signum() == 0 || scale() <= 0 || stripTrailingZeros().scale() <= 0
    else -> true
}

fun Double.floor(): Long = Math.floor(this).toLong()
fun Double.ceil(): Long = Math.ceil(this).toLong()

fun Float.floor(): Long = toDouble().floor()
fun Float.ceil(): Long = toDouble().ceil()

fun Double.clamp(min: Double, max: Double): Double = Math.max(min, Math.min(this, max))
fun Float.clamp(min: Float, max: Float): Float = Math.max(min, Math.min(this, max))
fun Long.clamp(min: Long, max: Long): Long = Math.max(min, Math.min(this, max))
fun Int.clamp(min: Int, max: Int): Int = Math.max(min, Math.min(this, max))
fun Short.clamp(min: Short, max: Short): Short = toLong().clamp(min.toLong(), max.toLong()).toShort()
fun Byte.clamp(min: Byte, max: Byte): Byte = toLong().clamp(min.toLong(), max.toLong()).toByte()
fun Number.clamp(min: Number, max: Number): Long = toLong().clamp(min.toLong(), max.toLong())

infix fun Double.pow(exp: Double): Double = Math.pow(this, exp)
infix fun Float.pow(exp: Float): Double = toDouble() pow exp.toDouble()
infix fun Long.pow(exp: Int): Long = LongMath.pow(this, exp)
infix fun Int.pow(exp: Int): Long = toLong() pow exp
infix fun Short.pow(exp: Short): Long = toInt() pow exp.toInt()
infix fun Byte.pow(exp: Byte): Long = toInt() pow exp.toInt()
infix fun Number.pow(exp: Int): Long = toLong() pow exp

fun Double.abs(): Double = Math.abs(this)
fun Long.abs(): Long = Math.abs(this)
fun Int.abs(): Int = Math.abs(this)
fun Number.abs(): Int = Math.abs(toInt())

fun gcd(a: Long, b: Long): Long = LongMath.gcd(a, b)
fun gcd(a: Byte, b: Byte): Byte = gcd(a.toLong(), b.toLong()).toByte()
fun gcd(a: Short, b: Short): Short = gcd(a.toLong(), b.toLong()).toShort()
fun gcd(a: Int, b: Int): Int = gcd(a.toLong(), b.toLong()).toInt()

fun lcm(a: Long, b: Long) = a * b / gcd(a, b)
fun lcm(a: Byte, b: Byte): Byte = lcm(a.toLong(), b.toLong()).toByte()
fun lcm(a: Short, b: Short): Short = lcm(a.toLong(), b.toLong()).toShort()
fun lcm(a: Int, b: Int): Int = lcm(a.toLong(), b.toLong()).toInt()

fun Double.roundToLong(): Long = Math.round(this)
fun Float.roundToInt(): Int = Math.round(this)

infix fun Number.fdiv(y: Number): Float = toFloat() / y.toFloat()
infix fun Number.ddiv(y: Number): Double = toDouble() / y.toDouble()

infix fun Number.roundDiv(y: Number): Long = Math.round(ddiv(y))

fun Double.sqrt() = Math.sqrt(this)
fun Number.sqrt() = toDouble().sqrt()

fun Long.sqrt(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Long = LongMath.sqrt(this, roundingMode)
fun Number.sqrt(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Long = toLong().sqrt(roundingMode)

fun Long.isPowerOfTwo() = LongMath.isPowerOfTwo(this)
fun Number.isPowerOfTwo() = toLong().isPowerOfTwo()

fun Number.isPrime() = LongMath.isPrime(toLong())

fun Double.round(digits: Int = 2): String = toFixed(digits).trimEnd('0')
fun Float.round(digits: Int = 2): String = toDouble().round(digits)

fun Double.sin(): Double = Math.sin(this)
fun Float.sin(): Float = toDouble().sin().toFloat()

fun Double.cos(): Double = Math.cos(this)
fun Float.cos(): Float = toDouble().cos().toFloat()

fun Double.tan(): Double = Math.tan(this)
fun Float.tan(): Float = toDouble().tan().toFloat()

fun Double.asin(): Double = Math.asin(this)
fun Float.asin(): Float = toDouble().asin().toFloat()

fun Double.acos(): Double = Math.acos(this)
fun Float.acos(): Float = toDouble().acos().toFloat()

fun Double.atan(): Double = Math.atan(this)
fun Float.atan(): Float = toDouble().atan().toFloat()

fun Double.toRadians(): Double = Math.toRadians(this)
fun Float.toRadians(): Float = toDouble().toRadians().toFloat()

fun Double.toDegrees(): Double = Math.toDegrees(this)
fun Float.toDegrees(): Float = toDouble().toDegrees().toFloat()

fun Double.exp(): Double = Math.exp(this)
fun Float.exp(): Float = toDouble().exp().toFloat()

fun Double.log(): Double = Math.log(this)
fun Float.log(): Float = toDouble().log().toFloat()
fun Double.ln(): Double = log()
fun Float.ln(): Float = log()
fun Number.log(): Double = toDouble().log()
fun Number.ln(): Double = toDouble().log()

fun Double.log10(): Double = Math.log10(this)
fun Float.log10(): Float = toDouble().log10().toFloat()
fun Number.log10(): Double = toDouble().log10()

fun Long.log10(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Int = LongMath.log10(this, roundingMode)
fun Number.log10(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Int = LongMath.log10(toLong(), roundingMode)
fun Long.log2(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Int = LongMath.log2(this, roundingMode)
fun Double.log2(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Int = DoubleMath.log2(this, roundingMode)
fun Number.log2(roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Int = toDouble().log2(roundingMode)

fun Double.cbrt(): Double = Math.cbrt(this)
fun Float.cbrt(): Float = toDouble().cbrt().toFloat()

infix fun Double.IEEEremainder(divisor: Double): Double = Math.IEEEremainder(this, divisor)
infix fun Float.IEEEremainder(divisor: Double): Float = toDouble().IEEEremainder(divisor).toFloat()

fun Double.rint(): Double = Math.rint(this)
fun Float.rint(): Float = toDouble().rint().toFloat()

fun Double.atan2(x: Double): Double = Math.atan2(this, x)
fun Float.atan2(x: Float): Float = toDouble().atan2(x.toDouble()).toFloat()

fun Double.ulp(): Double = Math.ulp(this)
fun Float.ulp(): Float = Math.ulp(toDouble()).toFloat()

fun Double.signum(): Double = Math.signum(this)
fun Float.signum(): Float = Math.signum(toDouble()).toFloat()

fun Double.sinh(): Double = Math.sinh(this)
fun Float.sinh(): Float = Math.sinh(toDouble()).toFloat()

fun Double.cosh(): Double = Math.cosh(this)
fun Float.cosh(): Float = Math.cosh(toDouble()).toFloat()

fun Double.tanh(): Double = Math.tanh(this)
fun Float.tanh(): Float = Math.tanh(toDouble()).toFloat()

fun Double.expm1(): Double = Math.expm1(this)
fun Float.expm1(): Float = Math.expm1(toDouble()).toFloat()

fun Double.log1p(): Double = Math.log1p(this)
fun Float.log1p(): Float = Math.log1p(toDouble()).toFloat()

infix fun Double.copySign(sign: Double): Double = Math.copySign(this, sign)
infix fun Float.copySign(sign: Float): Float = Math.copySign(this, sign)

fun Double.exponent(): Int = Math.getExponent(this)
fun Float.exponent(): Int = Math.getExponent(this)

fun Double.next(direction: Double = Double.POSITIVE_INFINITY): Double = Math.nextAfter(this, direction)
fun Float.next(direction: Double = Double.POSITIVE_INFINITY): Float = Math.nextAfter(this, direction)

fun Double.nextUp(): Double = Math.nextUp(this)
fun Float.nextUp(): Float = Math.nextUp(this)

fun Double.nextDown(): Double = Math.nextDown(this)
fun Float.nextDown(): Float = Math.nextDown(this)

infix fun Double.scalb(scaleFactor: Int): Double = Math.scalb(this, scaleFactor)
infix fun Float.scalb(scaleFactor: Int): Float = Math.scalb(this, scaleFactor)