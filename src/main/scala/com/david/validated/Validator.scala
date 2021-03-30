package com.david.validated

import com.david.validated.Model._
import cats.data._
import cats.data.Validated._
import cats.implicits._
import cats.kernel.Semigroup

/**
 * @author David Mackessy
 * @date 29/03/2021
 *
 *      1. using Either, returning 1 validation error (left) or a valid type (right)
 *      2. using Validated, returning 1 or more errors (left) or a valid type (right)
 *      info: you can use NonEmptyChain or NonEmptyList for holding the errors
 *        Nec is more performant than Nel
 **/
object Validator {

  //*** EITHER ***
  //using Either we can return a valid type or an error
  def validatePerson1(name: String, age: Int, address: Address): Either[DomainValidation, Person] = {
    for {
      validatedName <- validatePersonName1(name)
      validatedAge <- validatePersonAge1(age)
      validatedAddress <- validateAddress1(address)
    } yield Person(validatedName, validatedAge, validatedAddress)
  }

  //person
  def validatePersonName1(name: String): Either[PersonValidation, String] =
    Either.cond(!name.isEmpty, name, PersonMissingName)

  def validatePersonAge1(age: Int): Either[PersonValidation, Int] =
    Either.cond(age > 0, age, PersonMissingAge)

  //address
  def validateAddress1(address: Address): Either[AddressValidation, Address] = {
    for {
      validatedStreet <- validateAddressStreet1(address.street)
      validatedCounty <- validateAddressCounty1(address.county)
      validatedCountry <- validateAddressCountry1(address.country)
    } yield Address(validatedStreet, validatedCounty, validatedCountry)
  }

  def validateAddressStreet1(street: String): Either[AddressValidation, String] =
    Either.cond(!street.isEmpty, street, AddressMissingStreet)

  def validateAddressCounty1(county: County): Either[AddressValidation, County] =
    Either.cond(county.equals(Dublin), county, AddressMissingCounty)

  def validateAddressCountry1(country: Country): Either[AddressValidation, Country] =
    Either.cond(country.equals(Ireland), country, AddressMissingCountry)


  //*** VALIDATED ***
  //using Validated we can return a valid type or a chain of errors
  //we can reuse the existing validations and call .toValidatedNec to transform it
  //or write new validations using validNec & invalidNec

  type ValidationResult[A] = ValidatedNec[DomainValidation, A]

  private def validateName2(name: String): ValidationResult[String] =
//    if (name.nonEmpty) name.validNec else PersonMissingName.invalidNec
    validatePersonName1(name).toValidatedNec

  private def validateAge2(age: Int): ValidationResult[Int] =
//    if (age > 0) age.validNec
//    else PersonMissingAge.invalidNec
    validatePersonAge1(age).toValidatedNec

  private def validateAddress2(address: Address): ValidationResult[Address] =
//    (
//      validateStreet2(address.street),
//      validateCounty2(address.county),
//      validateountry2(address.country)
//      ).mapN(Address)
    validateAddress1(address).toValidatedNec

  private def validateStreet2(street: String): ValidationResult[String] =
//    if (street.nonEmpty) street.validNec else AddressMissingStreet.invalidNec
    validateAddressStreet1(street).toValidatedNec

  private def validateCounty2(county: County): ValidationResult[County] =
//    if (county != null) county.validNec else AddressMissingCounty.invalidNec
    validateAddressCounty1(county).toValidatedNec

  private def validateountry2(country: Country): ValidationResult[Country] =
//    if (country != null) country.validNec else AddressMissingCountry.invalidNec
    validateAddressCountry1(country).toValidatedNec

  def validatePerson2(name: String, age: Int, address: Address): ValidationResult[Person] = {
    (
      validateName2(name),
      validateAge2(age),
      validateAddress2(address)).mapN(Person)
  }
}
