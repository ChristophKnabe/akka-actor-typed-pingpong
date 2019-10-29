package pingpong

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

/** Messages and behavior for an actor, which will respond to each Ping message with a Pong message.
 * @author Christoph Knabe
 * @since 2019-10-28 */
object Reflector {

  /** A `Ping` identified by an `ìd` with the `ActorRef`, where to reply. */
  case class Ping(id: Int, replyTo: ActorRef[Thrower.Pong])

  /** Creates an actor [[Behavior]], which will respond to each [[Reflector.Ping]] message with a [[Thrower.Pong]] message with the same ID. */
  def apply(): Behavior[Ping] = Behaviors.receive { (context, ping) =>
    context.log.info(s"Received Ping #{}", ping.id)
    ping.replyTo ! Thrower.Pong(ping.id)
    Behaviors.same
  }

}
