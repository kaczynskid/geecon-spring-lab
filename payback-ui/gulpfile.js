// FOUNDATION FOR APPS TEMPLATE GULPFILE
// -------------------------------------
// This file processes all of the assets in the "src/main/js" folder, combines them with the Foundation for Apps assets,
// and outputs the finished files in the "target/classes/static" folder as a finished app.

// 1. LIBRARIES
// - - - - - - - - - - - - - - -

var $        = require('gulp-load-plugins')();
var argv     = require('yargs').argv;
var gulp     = require('gulp');
var rimraf   = require('rimraf');
var router   = require('front-router');
var sequence = require('run-sequence');

// Check for --production flag
var isProduction = !!(argv.production);

// 2. FILE PATHS
// - - - - - - - - - - - - - - -

var paths = {
  assets: [
    './src/main/js/**/*.*',
    '!./src/main/js/templates/**/*.*',
    '!./src/main/js/assets/{scss,js}/**/*.*'
  ],
  // Sass will check these folders for files when you use @import.
  sass: [
    'src/main/js/assets/scss',
    'target/bower/foundation-apps/scss',
    'target/bower/foundation/scss'
  ],
  // These files include Foundation for Apps and its dependencies
  foundationJS: [
    'target/bower/fastclick/lib/fastclick.js',
    'target/bower/viewport-units-buggyfill/viewport-units-buggyfill.js',
    'target/bower/tether/tether.js',
    'target/bower/hammerjs/hammer.js',
    'target/bower/angular/angular.js',
    'target/bower/angular-animate/angular-animate.js',
    'target/bower/angular-resource/angular-resource.js',
    'target/bower/angular-ui-router/release/angular-ui-router.js',
    'target/bower/angular-payments/lib/angular-payments.js',
    'target/bower/checklist-model/checklist-model.js',
    'target/bower/foundation-apps/js/vendor/**/*.js',
    'target/bower/foundation-apps/js/angular/**/*.js',
    '!target/bower/foundation-apps/js/angular/app.js'
  ],
  // These files are for your app's JavaScript
  appJS: [
    'src/main/js/assets/js/app.js'
  ]
}

// 3. TASKS
// - - - - - - - - - - - - - - -

// Cleans the target/classes/static directory
gulp.task('clean', function(cb) {
  rimraf('./target/classes/static', cb);
});

// Copies everything in the src/main/js folder except templates, Sass, and JS
gulp.task('copy', function() {
  return gulp.src(paths.assets, {
    base: './src/main/js/'
  })
    .pipe(gulp.dest('./target/classes/static'))
  ;
});

// Copies your app's page templates and generates URLs for them
gulp.task('copy:templates', function() {
  return gulp.src('./src/main/js/templates/**/*.html')
    .pipe(router({
      path: 'target/classes/static/assets/js/routes.js',
      root: 'src/main/js'
    }))
    .pipe(gulp.dest('./target/classes/static/templates'))
  ;
});

// Compiles the Foundation for Apps directive partials into a single JavaScript file
gulp.task('copy:foundation', function(cb) {
  gulp.src('target/bower/foundation-apps/js/angular/components/**/*.html')
    .pipe($.ngHtml2js({
      prefix: 'components/',
      moduleName: 'foundation',
      declareModule: false
    }))
    .pipe($.uglify())
    .pipe($.concat('templates.js'))
    .pipe(gulp.dest('./target/classes/static/assets/js'))
  ;

  // Iconic SVG icons
  gulp.src('./target/bower/foundation-apps/iconic/**/*')
    .pipe(gulp.dest('./target/classes/static/assets/img/iconic/'))
  ;

  cb();
});

// Compiles Sass
gulp.task('sass', function () {
  return gulp.src('src/main/js/assets/scss/app.scss')
    .pipe($.sass({
      includePaths: paths.sass,
      outputStyle: (isProduction ? 'compressed' : 'nested'),
      errLogToConsole: true
    }))
    .pipe($.autoprefixer({
      browsers: ['last 2 versions', 'ie 10']
    }))
    .pipe(gulp.dest('./target/classes/static/assets/css/'))
  ;
});

// Compiles and copies the Foundation for Apps JavaScript, as well as your app's custom JS
gulp.task('uglify', ['uglify:foundation', 'uglify:app'])

gulp.task('uglify:foundation', function(cb) {
  var uglify = $.if(isProduction, $.uglify()
    .on('error', function (e) {
      console.log(e);
    }));

  return gulp.src(paths.foundationJS)
    .pipe(uglify)
    .pipe($.concat('foundation.js'))
    .pipe(gulp.dest('./target/classes/static/assets/js/'))
  ;
});

gulp.task('uglify:app', function() {
  var uglify = $.if(isProduction, $.uglify()
    .on('error', function (e) {
      console.log(e);
    }));

  return gulp.src(paths.appJS)
    .pipe(uglify)
    .pipe($.concat('app.js'))
    .pipe(gulp.dest('./target/classes/static/assets/js/'))
  ;
});

// Starts a test server, which you can view at http://localhost:8080
gulp.task('server', ['build'], function() {
  gulp.src('./target/classes/static')
    .pipe($.webserver({
      port: 9000,
      host: 'localhost',
      //fallback: 'index.html',
      livereload: true,
      open: true,
      proxies: [
        { source: '/customer-service', target: 'http://172.17.0.67:8080/customer-service' },
        { source: '/merchant-service', target: 'http://172.17.0.67:8080/merchant-service' },
        { source: '/payback-service', target: 'http://172.17.0.67:8080/payback-service' }
      ]
    }))
  ;
});

// Builds your entire app once, without starting a server
gulp.task('build', function(cb) {
  sequence('clean', ['copy', 'copy:foundation', 'sass', 'uglify'], 'copy:templates', cb);
});

// Default task: builds your app, starts a server, and recompiles assets when they change
gulp.task('default', ['server'], function () {
  // Watch Sass
  gulp.watch(['./src/main/js/assets/scss/**/*', './scss/**/*'], ['sass']);

  // Watch JavaScript
  gulp.watch(['./src/main/js/assets/js/**/*', './js/**/*'], ['uglify:app']);

  // Watch static files
  gulp.watch(['./src/main/js/**/*.*', '!./src/main/js/templates/**/*.*', '!./src/main/js/assets/{scss,js}/**/*.*'], ['copy']);

  // Watch app templates
  gulp.watch(['./src/main/js/templates/**/*.html'], ['copy:templates']);
});
