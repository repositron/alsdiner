package ljw.alsdiner.domain.orders

import cats.Functor
import cats.data.EitherT
import cats.implicits._
import ljw.alsdiner.domain.OrderNotFound
import ljw.alsdiner.domain.kitchen.KitchenServiceAlgebra

class OrderService[F[_]](orderRepo: OrderRepositoryAlgebra[F], kitchenService: KitchenServiceAlgebra[F]) {
  def placeOrder(order: Order)(implicit F: Functor[F]): F[Order] = {
    val savedOrder = orderRepo.create(order)
   // kitchenService.addOrder(Function[F](orderIsReady, order.menuItem).as(())\
    savedOrder
  }

  def get(id: Long)(implicit F: Functor[F]): EitherT[F, OrderNotFound.type, Order] =
    EitherT.fromOptionF(orderRepo.get(id), OrderNotFound)

  def delete(id: Long)(implicit F: Functor[F]): F[Unit] =
    orderRepo.delete(id).as(())

  def orderIsReady(id: Long)(implicit F: Functor[F]): F[Option[Order]] =
    orderRepo.delete(id)
    // need to POST to notify

  def getOrdersForTable(tableId: Long)(implicit F: Functor[F]): F[List[Order]] = {
    orderRepo.getAllOrdersForTable(tableId)
  }

}

object OrderService {
  def apply[F[_]](orderRepo: OrderRepositoryAlgebra[F], kitchenService: KitchenServiceAlgebra[F]): OrderService[F] =
    new OrderService(orderRepo, kitchenService)
}
