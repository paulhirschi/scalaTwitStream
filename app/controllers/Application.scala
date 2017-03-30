package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import actors.TwitterStreamer
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class Application extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Ok(views.html.index("Tweets"))
  }

  def tweets = WebSocket.acceptWithActor[String, JsValue] {
    request => out => TwitterStreamer.props(out)
  }

  // def tweets = Action.async {
  //   credentials.map { case (consumerKey, requestToken) =>

  //     val (iteratee, enumerator) = Concurrent.joined[Array[Byte]]

  //     val jsonStream: Enumerator[JsObject] =
  //       enumerator &>
  //       Encoding.decode() &>
  //       Enumeratee.grouped(JsonIteratees.jsSimpleObject)

  //     val loggingIteratee = Iteratee.foreach[JsObject] { value =>
  //       Logger.info(value.toString)
  //     }

  //     jsonStream run loggingIteratee

  //       WS
  //         .url("https://stream.twitter.com/1.1/statuses/filter.json")
  //         .sign(OAuthCalculator(consumerKey, requestToken))
  //         .withQueryString("track" -> "cat")
  //         .get { response => // .get has been depricated. figure out how to make this work with .withMethod("GET").stream()
  //           Logger.info(s"Status: ${response.status}")
  //           iteratee
  //         }.map { _ =>
  //           Ok("Stream closed")
  //         }
  //   } getOrElse {
  //     Future.successful {
  //       InternalServerError("Twitter credentials missing")
  //     }
  //   }
  // }

  

}
