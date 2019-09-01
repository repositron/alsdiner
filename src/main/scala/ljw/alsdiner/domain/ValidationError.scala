package ljw.alsdiner.domain

import menuitems.MenuItem
import tablets.Tablet

sealed trait ValidationError extends Product with Serializable
case class TabletAlreadyExists(tablet: Tablet) extends ValidationError
case object TabletNotFound extends ValidationError
case class MenuItemAlreadyExists(menuItem: MenuItem) extends ValidationError
case class MenuItemNotFound() extends ValidationError
case object OrderNotFound extends ValidationError
