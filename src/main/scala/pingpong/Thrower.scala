package pingpong

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

/** Messages and behavior for an actor, which will respond to each Pong message with a Ping message.
* @author Christoph Knabe
* @since 2019-10-28 */
object Thrower {

  /** A `Pong` message coming as reply for a `Ping` message with the same `id`.*/
  case class Pong(id: Int)


  /** Creates an actor [[Behavior]], which will reply every received [[Pong]] message
   * with a [[Ping]] with an incremented `id`. */
  def apply(reflector: ActorRef[Reflector.Ping]): Behavior[Pong] = Behaviors.receive { (context, pong) =>
    context.log.info(s"Received Pong #{}", pong.id)
    reflector ! Reflector.Ping(pong.id + 1, context.self)
    Behaviors.same
  }


}
