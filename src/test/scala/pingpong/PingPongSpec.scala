package pingpong

import akka.actor.testkit.typed.scaladsl.{ScalaTestWithActorTestKit}
import org.scalatest.WordSpecLike

/** Test driver for the [[Thrower]] and the [[Reflector]] actors.
 * @author Christoph Knabe
 * @since 2019-10-29 */
class PingPongSpec extends ScalaTestWithActorTestKit with WordSpecLike {

  //Test structure inspired by https://manuel.bernhardt.io/2019/07/11/tour-of-akka-typed-protocols-and-behaviors/
  //Source code at https://github.com/manuelbernhardt/typed-payment-processor/blob/master/src/test/scala/io/bernhardt/typedpayment/ConfigurationSpec.scala
  "A Reflector actor" must {
    "reply on a Ping by a Pong with the same id" in {
      // define a probe in the role of a Thrower actor, which allows to easily send messages and check responses
      val throwerProbe = createTestProbe[Thrower.Pong]()

      // spawn a new Reflector actor as child of the TestKit's guardian actor
      val reflectorActor = spawn(Reflector(), "reflector")
      reflectorActor ! Reflector.Ping(99, throwerProbe.ref)
      throwerProbe.expectMessage(Thrower.Pong(99))
    }
  }

  "A Thrower actor" must {
    "reply on a Pong by a Ping with the incremented id" in {
      val reflectorProbe = createTestProbe[Reflector.Ping]()
      val throwerActor = spawn(Thrower(reflectorProbe.ref), "thrower")
      throwerActor ! Thrower.Pong(99)
      reflectorProbe.expectMessage(Reflector.Ping(100, throwerActor))
    }
  }

}
