package controllers

import java.util.Calendar
import javax.inject.Inject

import models.flickr.UserToken
import play.api._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.Play._


import scala.concurrent.Future


class Application @Inject() (apiClient: WSClient) extends Controller with Base with Flickr
{


  val context = defaultContext
  val repository = apiRepository(apiClient)

  def index = Action.async { implicit request => {
    val token = getRequestToken("access")
    if (token.isDefined) {
      val ut = token.map{ t => UserToken(t.token, t.secret)}.get
      repository
        .checkToken(ut)
        .map(_ match {
          case Some(ti) => Ok(views.html.index(ti, ut))
          case _ => Unauthorized("Token mismatch.")
        })

    } else {
      Future {TemporaryRedirect(routes.Auth.login.absoluteURL)}
    }
  } }

  def test = Action.async( implicit request => {
   Future { Ok("nanana") }
  } )




}
