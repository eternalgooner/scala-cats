package com.david.reducible

import cats._
import cats.data._
import cats.implicits._

/**
 * @author David Mackessy
 * @date 02/04/2021
 **/
object ReducibleDemo extends App {

  println(Reducible[NonEmptyList].reduce(NonEmptyList.of("a", "b", "c")))
  println(Reducible[NonEmptyList].reduceMap(NonEmptyList.of(1, 2, 4))(_.toString))
  println(Reducible[NonEmptyVector].reduceK(NonEmptyVector.of(List(1,2,3), List(2,3,4))))
  println(Reducible[NonEmptyVector].reduceLeft(NonEmptyVector.of(1,2,3,4))((s,i) => s + i))
  println(Reducible[NonEmptyList].reduceRight(NonEmptyList.of(1,2,3,4))((i,s) => Later(s.value + i)).value)
  println(Reducible[NonEmptyList].reduceLeftTo(NonEmptyList.of(1,2,3,4))(_.toString)((s,i) => s + i))
  println(Reducible[NonEmptyList].reduceRightTo(NonEmptyList.of(1,2,3,4))(_.toString)((i,s) => Later(s.value + i)).value)

}
