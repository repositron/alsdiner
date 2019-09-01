package repository.inmemory

import cats.Applicative
import domain.tablets.{Tablet, TabletRepositoryAlgebra}

class TabletInMemoryInterpreter[F[_]: Applicative] extends TabletRepositoryAlgebra[F] {
  override def create(tablet: Tablet): F[Tablet] = ???

  override def update(tablet: Tablet): F[Option[Tablet]] = ???

  override def get(id: Long): F[Option[Tablet]] = ???
}

object TabletInMemoryInterpreter {
  def apply[F[_]: Applicative]() = new TabletInMemoryInterpreter[F]()
}
