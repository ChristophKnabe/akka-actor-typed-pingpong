package pingpong

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import scala.concurrent.duration._

/** A demonstration of Akka Typed Actors playing Ping Pong.
 * @author Christoph Knabe
 * @since 2019-10-29 */
object PingPongMain extends App {

  /** Message signaling the [[ActorSystem]] to shut down. */
  case object Shutdown

  ActorSystem(PingPongMain(), "PingPong")

  /** The [[Behavior]] for the [[ActorSystem]]. It starts the [[Reflector]], and the [[Thrower]] actor.
   * They will send [[Thrower.Pong]] and [[Reflector.Ping]]  messages to each other.
   * Each received message will be logged with `info` severity.
   * After 4 milliseconds a shut down of the [[ActorSystem]] will be initiated.
   * This results in an unpredictable number of messages sent, received, and logged. */
  def apply(): Behavior[PingPongMain.Shutdown.type] =
    Behaviors.setup { context =>
      val duration = 4.millis
      context.log.info(s"Starting {} with a scheduled duration of {}", context.system, duration)
      val reflector = context.spawn(Reflector(), "reflector")
      val thrower = context.spawn(Thrower(reflector), "thrower")
      context.scheduleOnce(duration, context.self, Shutdown)
      context.watch(reflector)
      reflector ! Reflector.Ping(1, thrower)

      Behaviors.receiveMessage { shutdown =>
        val line = "="*80
        println(s"$line ${context.system} received $shutdown, now stopping...")
        Behaviors.stopped
      }
    }

}
