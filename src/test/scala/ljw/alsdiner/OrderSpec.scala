package ljw.alsdiner

import cats.Functor
import domain.menuitems.MenuItem
import domain.orders.{Order, OrderService}
import domain.tables.Table
import domain.tablets.Tablet
import repository.inmemory.OrderRepositoryInMemoryInterpreter
import org.scalatest.{Failed, FunSpec, Outcome}

class OrderSpec extends FunSpec {

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


  describe("getOrdersForTable for a table") {
    val f = new fixture

    val orderService = OrderService(f.orderRepository, KitchenServiceMockInterpreter())
    f.placeOrdersOnTable2(orderService)
    val orderIdsAdded = List(orderService.placeOrder(
        Order(None, f.menuItems(0), f.tablet.tabletId, f.table1.tableId)).orderId,
      orderService.placeOrder(
        Order(None, f.menuItems(0), f.tablet.tabletId, f.table1.tableId)).orderId)
      .flatten
      .toSet

    val orderIdForTable = orderService
      .getOrdersForTable(f.table1.tableId)
      .map(o => o.orderId)
      .flatten
      .toSet
    it("should only contain orders made for that table") {
      assertResult(2)(orderIdForTable.size)
      assertResult(orderIdsAdded)(orderIdForTable)
    }
  }

  describe("delete order from table1 getOrdersForTable") {
    val f = new fixture
    val orderService = OrderService(f.orderRepository, KitchenServiceMockInterpreter())
    val orderIdsAdded = List(orderService.placeOrder(
      Order(None, f.menuItems(0), f.tablet.tabletId, f.table1.tableId)).orderId,
      orderService.placeOrder(
        Order(None, f.menuItems(0), f.tablet.tabletId, f.table1.tableId)).orderId)
      .flatten
    orderService.delete(orderIdsAdded(0))

    it("should return just 1 order") {
      val orderIdForTable = orderService
        .getOrdersForTable(f.table1.tableId)
        .map(o => o.orderId)
        .flatten
        assertResult(1)(orderIdForTable.size)
        assertResult(orderIdsAdded(1))(orderIdForTable.head)
    }
  }



  override protected def withFixture(test: NoArgTest): Outcome =
    super.withFixture(test) match {
      case failed: Failed =>
        failed
      case other => other
    }
}
