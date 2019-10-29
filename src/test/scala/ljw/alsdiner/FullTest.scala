package ljw.alsdiner

import org.scalatest.FunSuite
import cats._
import cats.effect._
import cats.implicits._
import ljw.alsdiner.domain.menuitems.MenuItem
import ljw.alsdiner.domain.orders.{Order, OrderService}
import ljw.alsdiner.domain.tables.Table
import ljw.alsdiner.domain.tablets.Tablet
import ljw.alsdiner.repository.inmemory.OrderRepositoryInMemoryInterpreter
import org.http4s.Uri

import scala.concurrent.ExecutionContext.Implicits.global

class FullTest extends FunSuite {


  class fixture[F[_]]  {
    var orderRepository = OrderRepositoryInMemoryInterpreter()
    var tablet = Tablet(999)
    var table1 = Table(1, "Table1")
    var table2 = Table(2, "Table2")
    var menuItems =
      Array(MenuItem(1, "item1", 10), MenuItem(2, "item2", 12), MenuItem(3, "item3", 13))

    def placeOrdersOnTable2[F[_]](orderService: OrderService[F])(implicit F: Functor[F]): F[Order] = {
      orderService.placeOrder(Order(None, menuItems(0), tablet.tabletId, table2.tableId))
      orderService.placeOrder(Order(None, menuItems(0), tablet.tabletId, table2.tableId))
      orderService.placeOrder(Order(None, menuItems(0), tablet.tabletId, table2.tableId))
      orderService.placeOrder(Order(None, menuItems(0), tablet.tabletId, table2.tableId))
      orderService.placeOrder(Order(None, menuItems(0), tablet.tabletId, table2.tableId))

    }
  }

  test("multithreaded") {

  }
}
