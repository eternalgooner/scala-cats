package com.david.ior

import com.david.validated.Model.{Address, Dublin, Ireland, Kerry, Mexico, Poland}
import com.david.validated.Validator.ValidationResult

/**
 * @author David Mackessy
 * @date 29/03/2021
 *
 *      Using Ior is great for representing 3 possible states
 *      - errors
 *      - valid data
 *      - both (usually valid data with a non-critical warning/informational message)
 **/
object IorDemo extends App {

  //using validation with Either returns valid user with valid data but only 1 error if invalid data with > 1 error
  val validResultWith1Msg = Validator.validatePerson(
    name = "David",
    age = 9,
    address = Address(
      street = "New Street",
      county = Dublin,
      country = Ireland
    )
  )

  val validResult1 = Validator.validatePerson(
    name = "David",
    age = 3,
    address = Address(
      street = "New Street",
      county = Kerry,
      country = Poland
    )
  )

  val invalidResultWith2Errors = Validator.validatePerson(
    name = "",
    age = 3,
    address = Address(
      street = "",
      county = Dublin,
      country = Ireland
    )
  )

  val validResultWith2Msgs = Validator.validatePerson(
    name = "Tom",
    age = 26,
    address = Address(
      street = "test",
      county = Dublin,
      country = Mexico
    )
  )

  println(s"validResultWith1Msg: $validResultWith1Msg")
  println(s"validResult1: $validResult1")
  println(s"invalidResultWith2Errors: $invalidResultWith2Errors")
  println(s"validResultWith2Msgs: $validResultWith2Msgs")
}
