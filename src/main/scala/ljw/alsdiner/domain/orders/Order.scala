package ljw.alsdiner.domain.orders

import ljw.alsdiner.domain.menuitems.MenuItem


case class Order(
    orderId: Option[Long],
    menuItem: MenuItem,
    tabletId: Long,
    tableId: Long,
)
