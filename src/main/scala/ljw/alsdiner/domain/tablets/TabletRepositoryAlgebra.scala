package ljw.alsdiner.domain.tablets

trait TabletRepositoryAlgebra[F[_]] {
  def create(tablet: Tablet): F[Tablet]

  def update(tablet: Tablet): F[Option[Tablet]]

  def get(id: Long): F[Option[Tablet]]
}
