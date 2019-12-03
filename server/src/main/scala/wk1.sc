import cats.Functor
import cats.instances.list._

Functor[List].map(List(1,2,3))(x => x + 2)
