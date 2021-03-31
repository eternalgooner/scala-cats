package com.david.ior

/**
 * @author David Mackessy
 * @date 29/03/2021
 *
 *      having specific domain validation errors provides a richer model
 **/
object Model {

  case class Person(name: String, age: Int, address: Address)
  case class Address(street: String, county: County, country: Country) {
    def isValid: Boolean = {
      street.nonEmpty && (county!= null) && country != null
    }
  }

  sealed trait County
  case object Dublin extends County
  case object Wicklow extends County
  case object Louth extends County
  case object Kerry extends County

  sealed trait Country
  case object Ireland extends Country
  case object Poland extends Country
  case object Mexico extends Country

  //errors
  sealed trait DomainValidation
  sealed trait PersonValidation extends DomainValidation {
    def errorMessage: String
  }
  case object PersonMissingName extends PersonValidation {
    def errorMessage: String = "Person requires a name"
  }
  case object PersonMissingAge extends PersonValidation {
    def errorMessage: String = "Person requires an age"
  }

  sealed trait AddressValidation extends DomainValidation {
    def errorMessage: String
  }
  case object AddressMissingStreet extends AddressValidation {
    def errorMessage: String = "Address requires a street"
  }
  case object AddressMissingCounty extends AddressValidation {
    def errorMessage: String = "Address requires a county"
  }
  case object AddressMissingCountry extends AddressValidation {
    def errorMessage: String = "Address requires a country"
  }


}
