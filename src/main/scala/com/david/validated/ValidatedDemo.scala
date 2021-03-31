package com.david.validated

import com.david.validated.Model.{Address, Dublin, Ireland}
import com.david.validated.Validator.ValidationResult

/**
 * @author David Mackessy
 * @date 29/03/2021
 *
 *      Using Validated is great for validating data & accumulating errors when they occur
 **/
object ValidatedDemo extends App {

  //using validation with Either returns valid user with valid data but only 1 error if invalid data with > 1 error
  val validResult1 = Validator.validatePerson1(
    name = "David",
    age = 39,
    address = Address(
      street = "New Street",
      county = Dublin,
      country = Ireland
    )
  )

  val invalidResultWith2Errors1 = Validator.validatePerson1(
    name = "",
    age = 3,
    address = Address(
      street = "",
      county = Dublin,
      country = Ireland
    )
  )

  //using Validated returns valid type with valid data and 1 or more errors with invalid data
  val validResult2 = Validator.validatePerson2(
    name = "David",
    age = 39,
    address = Address(
      street = "New Street",
      county = Dublin,
      country = Ireland
    )
  )

  val invalidResultWith2Errors2: ValidationResult[Model.Person] = Validator.validatePerson2(
    name = "",
    age = 3,
    address = Address(
      street = "",
      county = Dublin,
      country = Ireland
    )
  )

  println(s"validResult1: $validResult1")
  println(s"invalidResultWith2Errors1: $invalidResultWith2Errors1")
  println()
  println(s"validResult2: $validResult2")
  println(s"invalidResultWith2Errors2: $invalidResultWith2Errors2")
}
