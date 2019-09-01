package repository.inmemory

import cats.effect.concurrent._
import cats._
import cats.effect.Sync
import cats.implicits._
import domain.orders.{Order, OrderRepositoryAlgebra}

import scala.concurrent.ExecutionContext
import scala.collection.concurrent.TrieMap
import java.util.concurrent.atomic
import java.util.concurrent.atomic.AtomicInteger


class OrderRepositoryInMemoryInterpreter[F[_]: Applicative ] extends OrderRepositoryAlgebra[F] {

  //private val idRef =  Ref.of[F, Long](1)
  private val idCounter = new AtomicInteger()
  private val orderLookup = new TrieMap[Long, Order]

  def create(order: Order): F[Order] = {
   /* for {

    }*/
    val toSave = order.copy(orderId = order.orderId.orElse(Some(idCounter.incrementAndGet())))
    toSave.orderId.foreach { orderLookup.put(_, toSave) }
    toSave.pure[F]
  }

  def get(orderId: Long): F[Option[Order]] =
    orderLookup.get(orderId).pure[F]

  def delete(orderId: Long): F[Option[Order]] =
    orderLookup.remove(orderId).pure[F]

  def getAllOrdersForTable(tableId: Long) : F[List[Order]] = {
    orderLookup.values.filter(order => order.tableId == tableId).toList.pure[F]
  }

}

object OrderRepositoryInMemoryInterpreter {
  def apply[F[_]: Applicative]() = new OrderRepositoryInMemoryInterpreter[F]()
}
