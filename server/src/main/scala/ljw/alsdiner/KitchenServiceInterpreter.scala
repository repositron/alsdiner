package ljw.alsdiner

import cats.effect.IO
import cats.{Applicative, Functor}
import domain.kitchen.KitchenServiceAlgebra
import domain.menuitems.MenuItem
import cats.implicits._
import cats.syntax._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.{FiniteDuration, SECONDS}

class KitchenServiceInterpreter[F[_]: Applicative] extends KitchenServiceAlgebra[F] {

  override def addOrder(callback: Functor[F], menuItem: MenuItem)(implicit F: Functor[F]): F[IO[Unit]] = {
    /*      def a(i: Int) = printf("az " + i)
      for {
        _ <- a(5)
        _ <- a(11)

      } yield()*/
    implicit val timer = IO.timer(ExecutionContext.global)
    val io: IO[Unit] = IO.sleep(
      FiniteDuration(menuItem.cookTimeSeconds, SECONDS) *> IO(println("complete")))


  }
}


object KitchenServiceInterpreter {
  def apply[F[_]: Applicative]() = new KitchenServiceInterpreter[F]()
}