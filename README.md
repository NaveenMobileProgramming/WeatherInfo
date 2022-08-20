# WeatherInfo
Weather SDK

Tools
Android studio
JAVA JDK

Language
Kotlin


Designing  Architecture
MVVM 

Library we are using 
1) Retrofit
2) Hilt
3) lifecycle for viewModel
4) OKHttp

How the app works?

1) First we check the Location Permission for get the latitude and longitude 
2) Then we hit the api for getting current weather info  example- https://api.openweathermap.org/data/2.5/weather?lat=29.7428073&lon=78.5049627&appid=ae1c4977a943a50eaa7da25e6258d8b2
3) Show the weather info into the ui