package domain.kitchen

import cats.Functor
import domain.menuitems.MenuItem

trait KitchenServiceAlgebra[F[_]] {
  def addOrder(callback: Functor[F], menuItem: MenuItem)(implicit F: Functor[F]): F[Unit]
}



