package domain.orders

import domain.menuitems.MenuItem

case class Order(
    orderId: Option[Long],
    menuItem: MenuItem,
    tabletId: Long,
    tableId: Long,
)
