import cats.{Applicative, Functor}
import domain.kitchen.KitchenServiceAlgebra
import domain.menuitems.MenuItem

class KitchenServiceMockInterpreter[F[_]: Applicative] extends KitchenServiceAlgebra[F] {
  override def addOrder(callback: Functor[F], menuItem: MenuItem)(implicit F: Functor[F]): F[Unit] = {
    ////callback // call back immediately
    //F.map(_ =>  print(""))
    F.asInstanceOf[F[Unit]]

  }
}

object KitchenServiceMockInterpreter {
  def apply[F[_]: Applicative]() = new KitchenServiceMockInterpreter[F]()
}