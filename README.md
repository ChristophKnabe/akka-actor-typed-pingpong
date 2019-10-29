# Scala Example for Asynchronous Communication by Akka Typed Actors
Christoph Knabe, 2019-10-29

This example has a `Thrower` actor, and a `Reflector` actor. Whenever the `Reflector` actor receives a 
`Ping` message with a numerical `id`, it will reply with a `Pong` message with the same `id`
to the `Thrower` actor.
Whenever the `Thrower` actor receives a `Pong` message with a numerical `id`,
it will reply with a `Ping` message with an incremented `id` to the `Reflector` actor.

The `Reflector` actor gets the `ActorRef` of the `Thrower` actor as part of the `Ping` message, 
whereas the `Thrower` actor gets the `ActorRef` of the `Reflector` actor as parameter of its 
`Behavior` factory method.

The application is set up by class `PingPongMain`. The activity is started by sending a `Ping` message with `id=1`
to the `Reflector` actor.

The application initiates termination by scheduling a `Shutdown` message to itself 
after some milliseconds.
If you run it several times, you can notice the indeterministic behavior, as the number of logged messages
 varies from run to run. 
