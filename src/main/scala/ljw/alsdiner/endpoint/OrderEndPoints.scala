package ljw.alsdiner.endpoint

import org.http4s.HttpRoutes
import cats.effect.Sync
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax._
import ljw.alsdiner.domain.OrderNotFound
import ljw.alsdiner.domain.orders.{Order, OrderService}
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl


class OrderEndpoints[F[_]: Sync] extends Http4sDsl[F] {

  /* Needed to decode entities */
  implicit val orderDecoder: EntityDecoder[F, Order] = jsonOf

  def placeOrderEndpoint(orderService: OrderService[F]): HttpRoutes[F]  = {
    HttpRoutes.of[F] {
      case req@POST -> Root =>
        for
        {
          order <- req.as[Order]

          saved
          <- orderService.placeOrder(order)
          resp
          <- Ok(saved.asJson)
        }
        yield resp
    }
  }

  private def getOrderEndpoint(orderService: OrderService[F]): HttpRoutes[F] = {
    HttpRoutes.of[F] {
      case GET -> Root / LongVar(id)  =>
        orderService.get(id).value.flatMap {
          case Right(found) => Ok(found.asJson)
          case Left(OrderNotFound) => NotFound("The order was not found")
        }
    }
  }

  private def deleteOrderEndpoint(orderService: OrderService[F]): HttpRoutes[F] = {
    HttpRoutes.of[F] {
      case DELETE -> Root / LongVar(id) =>
        for {
          _ <- orderService.delete(id)
          resp <- Ok()
        } yield resp
    }
  }

  def endpoints(orderService: OrderService[F]): HttpRoutes[F] = {
    placeOrderEndpoint(orderService) <+>
      getOrderEndpoint(orderService) <+>
      deleteOrderEndpoint(orderService)
  }
}

object OrderEndpoints {
  def endpoints[F[_]: Sync](orderService: OrderService[F]): HttpRoutes[F] =
    new OrderEndpoints[F].endpoints(orderService)
}