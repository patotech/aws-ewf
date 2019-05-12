/*global WebAppEvolution _config*/
var _config = window._config;
var winAuthToken = window.winAuthToken;
var WebAppEvolution = window.WebAppEvolution || {};
WebAppEvolution.map = WebAppEvolution.map || {};

(function adminScopeWrapper($) {
    var authToken;

    WebAppEvolution.authToken.then(function setAuthToken(token) {
        if (token) {
            authToken = token;
            winAuthToken = token;
        } else {
            window.location.href = '../public/login.html';
        }
    }).catch(function handleTokenError(error) {
        alert(error);
        window.location.href = '../public/login.html';
    });

    $(function onDocReady() {
        pageOnDocReadyWithAuthToken( authToken );
        //$('#request').click(handleRequestClick);
        /*$('#signOut').click(function() {
            WildRydes.signOut();
            alert("You have been signed out.");
            window.location = "../public/login.html";
        });
        $(WildRydes.map).on('pickupChange', handlePickupChanged);*/
    });
}(jQuery));