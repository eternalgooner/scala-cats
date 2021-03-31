package com.david.ior

import com.david.validated.Model._
import cats.data.{Ior, IorNec, NonEmptyChain => Nec}
import cats.implicits._

/**
 * @author David Mackessy
 * @date 29/03/2021
 *
 *      1. using Ior, returning
 *      - validation error (left)
 *      - valid type (right)
 *      - both
 *
 **/
object Validator {

  //*** Ior ***

  type Failures = Nec[String]

  def validatePerson(name: String, age: Int, address: Address): Failures Ior Person =
    (
      validatePersonName(name),
      validatePersonAge(age),
      validateAddress(address)
    ).mapN(Person)

  //person
  def validatePersonName(name: String): Failures Ior String =
    if (name.isEmpty)
      Ior.left(Nec(PersonMissingName.errorMessage))
    else if (name.contains("."))
      Ior.Both(Nec("using . will be removed soon"), name)
    else
      Ior.right(name)

  def validatePersonAge(age: Int): Failures Ior Int =
    if (age < 1)
      Ior.left(Nec(PersonMissingAge.errorMessage))
    else if (age > 24)
      Ior.Both(Nec("over 24, you are entitled to a discount"), age)
    else
      Ior.right(age)

  //address
  def validateAddress(address: Address): Failures Ior Address =
    (
      validateAddressStreet(address.street),
      validateAddressCounty(address.county),
      validateAddressCountry(address.country)
    ).mapN(Address)

  def validateAddressStreet(street: String): Failures Ior String =
    if (street.isEmpty)
      Ior.left(Nec(AddressMissingStreet.errorMessage))
    else if (street.contains("."))
      Ior.Both(Nec("using . in address will be removed soon"), street)
    else
      Ior.right(street)

  def validateAddressCounty(county: County): Failures Ior County =
    if (county == null)
      Ior.left(Nec(AddressMissingCounty.errorMessage))
    else if (county == Dublin)
      Ior.Both(Nec("Dublin qualifies for discount"), county)
    else
      Ior.right(county)

  def validateAddressCountry(country: Country): Failures Ior Country =
    if (country == null)
      Ior.left(Nec(AddressMissingCountry.errorMessage))
    else if (country == Mexico)
      Ior.Both(Nec("Mexico pay more for delivery"), country)
    else
      Ior.right(country)



}
