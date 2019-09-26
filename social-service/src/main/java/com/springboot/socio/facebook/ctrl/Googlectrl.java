package com.springboot.socio.facebook.ctrl;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Googlectrl {
//collicoders: 5014057553-8gm9um6vnli3cle5rgigcdjpdrid14m9.apps.googleusercontent.com
//    clientSecret: tWZKVLxaD_ARWsriiiUFYoIk
//    564319405493-ldgbknrojsjh4m3lm187df00n5rt24bs.apps.googleusercontent.com
//    spring.security.oauth2.client.registration.google.client-secret: 6PnvyM2DObCTtL1CKvj_VTwq
    private GoogleConnectionFactory factory = new GoogleConnectionFactory("5014057553-8gm9um6vnli3cle5rgigcdjpdrid14m9.apps.googleusercontent.com",
        "tWZKVLxaD_ARWsriiiUFYoIk");
    private String forwardUrl="http://localhost:8080/oauth2/redirect";

//    @GetMapping(value= "/")
//    public ModelAndView index() {
//        return new ModelAndView("welcome");
//    }

    // Redirection uri.
    @GetMapping(value = "/glogin")
    public String redirect() {
        // Creates the OAuth2.0 flow and performs the oauth handshake on behalf of the user.
        OAuth2Operations operations= factory.getOAuthOperations();

        // Builds the OAuth2.0 authorize url and the scope parameters.
        OAuth2Parameters params= new OAuth2Parameters();
        params.setRedirectUri(forwardUrl);
        params.setScope("email,profile");

        // Url to redirect the user for authentication via OAuth2.0 authorization code grant.
        String authUrl = operations.buildAuthenticateUrl(params);
        System.out.println("Generated google url is= " + authUrl);
        return "redirect:" + authUrl;
    }

    // Welcome page.
    @GetMapping(value = "/oauth2/redirect")
    public ModelAndView prodducer(@RequestParam("code") String authorizationCode) {
        // Creates the OAuth2.0 flow and performs the oauth handshake on behalf of the user.
        OAuth2Operations operations= factory.getOAuthOperations();

        // OAuth2.0 access token.
        // "exchangeForAccess()" method exchanges the authorization code for an access grant.
        AccessGrant accessToken= operations.exchangeForAccess(authorizationCode, forwardUrl, null);

        Connection<Google> connection= factory.createConnection(accessToken);

        // Getting the connection that the current user has with facebook.
        Google google= connection.getApi();
        // Fetching the details from the facebook.
        String[] fields = { "id", "name", "email", "about", "birthday"};
        UserProfile userProfile= connection.fetchUserProfile();
        System.out.println("google Profile: "+userProfile);
//                google.fetchObject("me", User.class, fields);

        ModelAndView model = new ModelAndView("details");
        model.addObject("user", userProfile);
        return model;
    }
}
