package ljw.alsdiner

import config._
import domain.tablets._
import cats.effect._
import cats.implicits._
import endpoint.TabletEndpoints
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.{Router, Server, defaults}
import org.http4s.implicits._
import repository.inmemory.TabletInMemoryInterpreter
import io.circe.generic.auto._
import io.circe.config.parser

object RestaurantServer extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    createServer.use(_ => IO.never).as(ExitCode.Success)
  def createServer[F[_]: ContextShift: ConcurrentEffect: Timer : Sync]: Resource[F, Server[F]] =
    for {
      config <- Resource.liftF(parser.decodePathF[F, ServerConfig]("restaurant.server"))
      tabletRepository = TabletInMemoryInterpreter[F]()
      tabletValidation = TabletValidationInterpreter[F](tabletRepository)
      tabletService = TabletService[F](tabletRepository, tabletValidation)
      httpApp = Router(
        "/tablets" -> TabletEndpoints.endpoints[F](tabletService),
        "/menuitems" -> TabletEndpoints.endpoints[F](tabletService),
        "/tables" -> TabletEndpoints.endpoints[F](tabletService),
      ).orNotFound
      server <- BlazeServerBuilder[F]
        .bindHttp(config.port, config.host)
        .withHttpApp(httpApp)
        .resource
    } yield server
}
