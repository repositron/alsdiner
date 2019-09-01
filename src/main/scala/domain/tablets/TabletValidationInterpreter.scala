package domain.tablets

import cats.Applicative
import cats.data.EitherT
import domain.{TabletAlreadyExists, TabletNotFound}

class TabletValidationInterpreter[F[_]: Applicative](repository: TabletRepositoryAlgebra[F])
    extends TabletValidationAlgebra[F] {
  override def doesNotExist(tablet: Tablet): EitherT[F, TabletAlreadyExists, Unit] = ???

  override def exist(tabletId: Option[Long]): EitherT[F, TabletNotFound.type, Unit] = ???
}

object TabletValidationInterpreter {
  def apply[F[_]: Applicative](repository: TabletRepositoryAlgebra[F]) =
    new TabletValidationInterpreter[F](repository)
}
