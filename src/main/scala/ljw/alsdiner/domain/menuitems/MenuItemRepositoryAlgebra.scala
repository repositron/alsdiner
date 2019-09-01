package ljw.alsdiner.domain.menuitems

import cats.data.EitherT
import ljw.alsdiner.domain.MenuItemNotFound

trait MenuItemRepositoryAlgebra[F[_]] {
  def create(menuItem: MenuItem): F[MenuItem]
  def deleteMenuItem(menuItem: MenuItem): EitherT[F, MenuItemNotFound, MenuItem]
}
