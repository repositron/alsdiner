package ljw.alsdiner.domain.tablets

import cats.data.EitherT
import ljw.alsdiner.domain.{TabletAlreadyExists, TabletNotFound}

trait TabletValidationAlgebra[F[_]] {
  def doesNotExist(tablet: Tablet): EitherT[F, TabletAlreadyExists, Unit]

  def exist(tabletId: Option[Long]): EitherT[F, TabletNotFound.type, Unit]
}
