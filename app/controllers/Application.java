package controllers;

import models.Media;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	public static Result index() {
		return redirect(routes.Application.media());
	}

	public static Result media() {
		return ok(views.html.index.render(Media.getMediaList()));
	}

}
