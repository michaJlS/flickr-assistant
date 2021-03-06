package controllers.actions

import domain.entities.TokenInfo
import play.api.mvc.{Request, WrappedRequest}

class RequestWithTokenInfo[A]( val tokenInfo:TokenInfo, request: RequestWithUserToken[A]) extends RequestWithUserToken[A](request.token, request.userNsid, request)
