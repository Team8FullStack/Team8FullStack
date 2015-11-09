var templates = {

signIn: [
"<h1 class='title'>ster·e·o·type</h1>",
"<div class='signIn'>",
"<div class='inputarea'>",
"<input type='text' class='username' name='username' placeholder='enter username'>",
"<input type='password' class='password' name='password' placeholder='enter password'>",
"<span class='msgArea'></span>",
"<button type='submit' class='login btn btn-default' name='submit' onclick='events.login()'>Login</button>",
"</div>",
"<div class='revealCreateUser' onClick='events.createUser()'>",
"<a href=''>Create New User</a>",
"</div>"
].join(""),

createUser: [
"<div class='createUser'>",
"<select name='gender' class='gender'>",
"<option>select your gender</option>",
"<option class='gender'>Male</option>",
"<option class='gender'>Female</option>",
"</select>",
"<input type='text' class='age' name='age' placeholder='enter your age'>",
"<input type='text' class='location' name='location' placeholder='enter your location'>",
"<input type='text' class='username' name='username' placeholder='enter username'>",
"<input type='password' class='password' name='password' placeholder='enter password'>",
"<input name='picture' type='text' placeholder='Enter an image URL'>",
"<select name='stereotypeName' class='selectpicker stereotypeName'>",
"<option>What are you?</option>",
"<option class='stereotypeName'>Hippie</option>",
"<option class='stereotypeName'>Skater</option>",
"<option class='stereotypeName'>Programmer</option>",
"<option class='stereotypeName'>Crossfit</option>",
"<option class='stereotypeName'>Hipster</option>",
"<option value='Frat Star / Sorority Sis' class='stereotypeName'>Frat Star / Sorostitute</option>",
"</select>",
"<button type='submit' class='signup btn btn-default' name='signup'>Start Stereotyping!</button>",
"</div>"
].join(""),

mainpage: [
"<div class='mainpage'>",
"<header class='col-md-12'>",
"<h2>ster·e·o·type</h2>",
"</header>",
"<div class='col-md-8'>",
"<div class='col-md-3'>",
"<button class='getmatch btn btn-default' onClick='get.closestMatch()'>Get Closest Match</button>",
"<button class='getopp btn btn-default' onClick='get.oppositeMatch()'>Get Opposite Match</button>",
"<button class='delete btn btn-default' onClick='events.deleteUser()'>Delete Account</button>",
"<section class='matches'>",
"<div class='match1'></div>",
"</section>",
"</div>",
"<div class='col-md-9'>",
"<div class='profilepic'></div>",
"<div class='profileinfo'></div>",
"</div>",
"</div>",
"</div>",
"</div>",
"<footer class='col-md-12'></footer>",
"</div>",
].join(""),

profile: [
  "<h3><%= username %></h3>",

  // "<img src='<%= picture %>'/>",
  "<ul>",
  "<li>Stereotype: <%= stereotype.typeName %></li>",
  "<li>Favorite Drink: <%= stereotype.drink %></li>",
  "<li>Favorite Food: <%= stereotype.food %></li>",
  "<li>Favorite Hangout Spot: <%= stereotype.hangout %></li>",
  "<li>Hobby: <%= stereotype.hobby %></li>",
  "<li>Music: <%= stereotype.music %></li>",
  "<li>Style: <%= stereotype.style %></li>",
  "</ul>"
].join("")

};
