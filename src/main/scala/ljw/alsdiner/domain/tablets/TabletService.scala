package ljw.alsdiner.domain.tablets

import cats.Monad
import cats.data.EitherT
import ljw.alsdiner.domain.TabletAlreadyExists
import ljw.alsdiner.domain.orders.Order

class TabletService[F[_]](
    repository: TabletRepositoryAlgebra[F],
    validation: TabletValidationAlgebra[F],
) {

  def create(tablet: Tablet)(implicit M: Monad[F]): EitherT[F, TabletAlreadyExists, Tablet] =
    for {
      _ <- validation.doesNotExist(tablet)
      saved <- EitherT.liftF(repository.create(tablet))
    } yield saved

  def orderReady(order: Order)(implicit M: Monad[F]): EitherT[F, TabletAlreadyExists, Tablet] =
    ???
}

object TabletService {
  def apply[F[_]](
      repository: TabletRepositoryAlgebra[F],
      validation: TabletValidationAlgebra[F]): TabletService[F] =
    new TabletService[F](repository, validation)
}
