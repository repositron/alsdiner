package ljw.alsdiner.domain.kitchen

import cats.Functor
import cats.effect.IO
import ljw.alsdiner.domain.menuitems.MenuItem

trait KitchenServiceAlgebra[F[_]] {
  def addOrder(callback: Functor[F], menuItem: MenuItem)(implicit F: Functor[F]): F[IO[Unit]]
}



