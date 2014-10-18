# dropwizard-angular-sbt-for-heroku

A sample, fairly bare-bones setup for Dropwizard API which serves necessary files for a frontend application. Built using SBT. Heroku-ready.

# Building locally

Just run `sbt "run server server.conf"` or use `run_api.sh` script, which does exactly same thing.

# Deploying to heroku

Assuming you've got Heroku tools installed and the repo cloned:

    # In repo's directory:
    heroku create
    git push heroku master

Et voila!

`Procfile` was prepared to use the port that is provided by Heroku through PORT env variable. 
It makes use of sbt-native packager, to avoid SBT's memory footprint. 

You can test the `Procfile` installing [foreman](https://rubygems.org/gems/foreman) and running 

    foreman start  

# Deployed example

Visit [http://secure-hamlet-8382.herokuapp.com](http://secure-hamlet-8382.herokuapp.com) to see a working sample

# Interesting bits

All static assets are served from src/main/resources/static folder through Dropwizard's AssetsBundle servlet.

I spent most time getting the server.conf to play well with Procfile used by Heroku.

Second interesting bit is the [AssetsServingFilter class](https://github.com/tomaszj/dropwizard-angular-sbt-for-heroku/blob/master/src/main/java/org/tomaszjaneczko/testpoc/api/AssetsServingFilter.java)
 which essentially handles the redirection of all user's requests to serve index.html page, excluding JS, CSS files and images.
 The purpose is to support HTML5 mode paths in Angular application. This way, http://secure-hamlet-8382.herokuapp.com/businesses
 link is resolved properly to Angular application, while preserving the path for correct resolution.

Servlet filter is used, as Dropwizard uses a separate servlet to handle static files, bypassing resolution used for normal Resources.

`system.properties` file is used by Heroku to install correct Java runtime version. In this case it's Java 8.

# License

Standard MIT.
