package com.hbmall.auth.controller;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class to show the index page, validate facebook credentials, and display the user details.
 * @author yatin-batra
 */
@RestController
@RequestMapping("/")
public class Facebookctrl {

	// Creates a facebook connection using the given application id and secret key. 487654301400466
	//spring.security.oauth2.client.registration.facebook.client-secret: 3ec76bf19fbfeaf187a6673d1cf16411
	private FacebookConnectionFactory factory = new FacebookConnectionFactory("487654301400466", "3ec76bf19fbfeaf187a6673d1cf16411");
	private String forwardUrl="http://localhost:5000/forwardLogin";

	// Index page.
	@GetMapping(value= "/")
	public ModelAndView index() {
		System.out.println("entering /sns/.");
		return new ModelAndView("welcome");
	}

	// Redirection uri.
	@GetMapping(value = "/useapp")
	public String redirect() {
		// Creates the OAuth2.0 flow and performs the oauth handshake on behalf of the user.
		OAuth2Operations operations= factory.getOAuthOperations();

		// Builds the OAuth2.0 authorize url and the scope parameters.
		OAuth2Parameters params= new OAuth2Parameters();
		params.setRedirectUri(forwardUrl);
		params.setScope("email, public_profile");

		// Url to redirect the user for authentication via OAuth2.0 authorization code grant.
		String authUrl = operations.buildAuthenticateUrl(params);
		System.out.println("Generated url is= " + authUrl);
		return "redirect:" + authUrl;
	}

	// Welcome page.
	@GetMapping(value = "/forwardLogin")
	public ModelAndView prodducer(@RequestParam("code") String authorizationCode) {
		// Creates the OAuth2.0 flow and performs the oauth handshake on behalf of the user.
		OAuth2Operations operations= factory.getOAuthOperations();

		// OAuth2.0 access token.
		// "exchangeForAccess()" method exchanges the authorization code for an access grant.
		AccessGrant accessToken= operations.exchangeForAccess(authorizationCode, forwardUrl, null);

		Connection<Facebook> connection= factory.createConnection(accessToken);

		// Getting the connection that the current user has with facebook.
		Facebook facebook= connection.getApi();
		// Fetching the details from the facebook.
		String[] fields = { "id", "name", "email", "about", "birthday"};
		User userProfile= facebook.fetchObject("me", User.class, fields);

		ModelAndView model = new ModelAndView("details");
		model.addObject("user", userProfile);
		return model;
	}
}