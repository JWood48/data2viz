package test.matchers

import test.matchers.Matcher
import kotlin.js.Math

interface DoubleMatchers {
  infix fun Double.plusOrMinus(tolerance: Double): ToleranceMatcher = ToleranceMatcher(this, tolerance)

  fun exactly(d: Double): Matcher<Double> = object : Matcher<Double> {
    override fun test(value: Double) {
      if (value != d)
        throw AssertionError("$value is not equal to expected value $d")
    }
  }
}

class ToleranceMatcher(val expected: Double, val tolerance: Double) : Matcher<Double> {

  override fun test(value: Double) {
    if (tolerance == 0.0)
      println("[WARN] When comparing doubles consider using tolerance, eg: a shouldBe b plusOrMinus c")
    val diff = Math.abs(value - expected)
    if (diff > tolerance)
      throw AssertionError("$value is not equal to $expected")
  }

  infix fun plusOrMinus(tolerance: Double): ToleranceMatcher = ToleranceMatcher(expected, tolerance)
}
