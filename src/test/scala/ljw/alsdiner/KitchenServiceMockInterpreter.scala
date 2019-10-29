package ljw.alsdiner

import cats.effect.{IO, Timer}
import cats.{Applicative, Functor}
import domain.kitchen.KitchenServiceAlgebra
import domain.menuitems.MenuItem

class KitchenServiceMockInterpreter[F[_]: Applicative] extends KitchenServiceAlgebra[F] {
  override def addOrder(callback: Functor[F], menuItem: MenuItem)(implicit F: Functor[F]): F[IO[Unit]]  = {
    ////callback // call back immediately
    //F.map(_ =>  print(""))
    new Timer[F] {
    }

    F.asInstanceOf[F[Unit]]

  }
}

object KitchenServiceMockInterpreter {
  def apply[F[_]: Applicative]() = new KitchenServiceMockInterpreter[F]()
}