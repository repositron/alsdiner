package ljw.alsdiner.domain.menuitems

import cats.Monad
import cats.data.EitherT
import ljw.alsdiner.domain.MenuItemAlreadyExists

class MenuItemService[F[_]](menuItemRepo: MenuItemRepositoryAlgebra[F]) {
  def createMenuItem(menuItem: MenuItem)(
      implicit M: Monad[F]): EitherT[F, MenuItemAlreadyExists, MenuItem] = {
    ???
  }
}

object MenuItemService {
  def apply[F[_]](repository: MenuItemRepositoryAlgebra[F]): MenuItemService[F] =
    new MenuItemService[F](repository)
}
