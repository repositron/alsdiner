package ljw.adclient

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.Uri.uri
import org.http4s.UrlForm
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.dsl.io.POST


import scala.concurrent.ExecutionContext.Implicits.global

object AdClient extends IOApp with Http4sClientDsl[IO] {
  def addTable[IO](): Unit = {

  }
  def addOrder[IO]() : Unit = {

  }

  def run(args: List[String]): IO[ExitCode] = {
    BlazeClientBuilder[IO](global).resource.use { client =>
      // use `client` here and return an `IO`.
      // the client will be acquired and shut down
      // automatically each time the `IO` is run.
      IO.unit
    }
    val req = POST(UrlForm("q" -> "http4s"), uri("https://duckduckgo.com/"))
    val responseBody = BlazeClientBuilder[IO](global).resource.use(_.expect[String](req))
    responseBody.flatMap(resp => IO(println(resp))).as(ExitCode.Success)
  }
}
