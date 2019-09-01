# Restaurant Rest App

Uses Http4s, cats, scalatest.
Architecture. Base on scala-pet-store. Uses Repository pattern architecture. Data isn't persisted to DB; it is stored in memory.

# Tests
Tests can be run from sbt using test

## Components
### OrderService

### KitchenService
For each order this will call a callback when a order is completed. 