package endpoint

import cats.data.EitherT
import cats.effect.{IO, Sync}
import cats.implicits._
import cats.data._
import domain.TabletAlreadyExists
import domain.tablets.{Tablet, TabletService}
import org.http4s._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityDecoder, HttpRoutes, QueryParamDecoder}


class TabletEndpoints[F[_]: Sync] extends Http4sDsl[F] {
  implicit val decoder: EntityDecoder[F, Tablet] = jsonOf[F, Tablet]

  private def createEndpoint(tabletService: TabletService[F]): HttpRoutes[F] = {
    HttpRoutes.of[F] {
      case req@POST -> Root =>
        val action = for {
          tablet <- req.as[Tablet]
          result <- tabletService.create(tablet).value
        } yield result

        action.flatMap {
          case Right(saved) =>
            Ok(saved.asJson)
          case Left(TabletAlreadyExists(existing)) =>
            Conflict(s"The table ${existing.tabletId} already exists")
        }
    }
  }
}

object TabletEndpoints {
  def endpoints[F[_]: Sync](tabletService: TabletService[F]): HttpRoutes[F] =
    new TabletEndpoints[F].createEndpoint(tabletService)
}
