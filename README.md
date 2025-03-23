# Spring 2025, CS342 Project 2: Weather App
Created by Alexander Tago and Nicholas Laird

Displays the weather forecast information in an area using the [National Weather Service (NWS) API](https://www.weather.gov/documentation/services-web-api). <br/>
Links we used to retrieve information: <br/>
1. https://api.weather.gov/gridpoints/LOT/75,73 - Min/max temperatures, as well as other data which we did not use <br/>
2. https://api.weather.gov/gridpoints/LOT/75,73/forecast - Daily period forecasts <br/>
3. https://api.weather.gov/gridpoints/LOT/75,73/forecast/hourly - Hourly period forecasts <br/>
4. https://api.weather.gov/alerts/active?point=41.882,-87.6324 - Active alerts <br/>

[NWS API FAQs](https://weather-gov.github.io/api/general-faqs)

This application has been tested on Windows 11 and macOS Sequoia and functions the same, with some minor differences in how certain elements appear on screen. <br/>
[Check out the Wireframe on Figma!](https://www.figma.com/community/file/1485087306862791228)

# Table of Contents
### 1. [Features](#features)
### 2. [Mapbox API key](#creating-a-mapbox-api-key)
### 3. [Images](#images)

## Features

### <ins> 1. A Home Screen which displays: </ins>
  - Location
  - Current temperature
  - Short & long forecast description
  - Low/High temperatures of the day
  - Forecast for the next 24 hours
  - Image of forecast description
  - Active alerts
<br/>
Clicking the alert box when there is an active alert creates a new window displaying its headline, description, and instructions.

### Clicking the location at the top creates a new window allowing the user to change the location based on the city, state, coordinates, or randomly. [^1] <br/>
  
  - Searching by city/state requires a Mapbox API key. [^2]
  - Searching by coordinates only works for WGS 84/EPSG 4326 coordinates. [^3]

[^1]:The NWS API only displays information for the United States.
[^2]:A Mapbox API key is not required for getting a random location.
[^3]:These coordinate systems are the only ones supported by the NWS API.

### <ins> 2. A Daily Forecast screen which displays the forecast for 3/5/7 days. </ins>

Each day can be clicked to create a new window which displays its associated image, description, and hourly forecast.

### <ins> 3. A Weekly Trends screen which displays the low/high temperatures for 3/5/7 days. </ins>

This is displayed on a line chart which updates based on the number of days.

### <ins> 4. A Settings screen which allows the user to change the following: </ins>

- Theme (Matcha, Cocoa, Milk, Ube)
- Temperature unit (Fahrenheit, Celsius)
- Time format (12-hour, 24-hour) <br/>

## Creating a Mapbox API key

In order to use the city/state inputs to change the location of the app, a [Mapbox](https://www.mapbox.com/) API key/access token is required. <br/>
It's free to make an account and acquire a token, and the first 100k requests for the geocoding service API are completely free of charge. <br/>
Once acquired, the easiest way to add your token as an environment variable is through editing the configurations in IntelliJ IDEA.

### Adding your token to the list of environment variables:
<img src= "https://github.com/user-attachments/assets/17e845f5-be62-4706-bf94-88f3113aac16" height="200"/> <br/>
Click on your run configuration then "Edit Configurations..."

<br/>
<img src= "https://github.com/user-attachments/assets/6ebf60e7-847f-41a8-89c6-228d20ff7a01" height="200"/> <br/>
Scroll down your run configuration to "Java Options" then click "Modify" to add "Environment variables" to the list of options. <br/>
Then, add the environment variable "MAPBOX_API_KEY=" with your token.

# Images

## Home Screen, Active Alerts, and Location screens
<img align="left" src= "https://github.com/user-attachments/assets/b03f7c1d-934f-4b1a-8bf9-681b4b359686" height="300"/>
<img align="left" src= "https://github.com/user-attachments/assets/e1cb58ea-7a7c-4c8f-9510-b82a68704087" height="250"/>
<img align="left" src= "https://github.com/user-attachments/assets/f6d74f87-fd6f-4443-9455-ac9a75a54f5e" height="250"/>

<br clear="left"/>

## Daily Forecast and Hourly Forecast Screens
<img align="left" src= "https://github.com/user-attachments/assets/75c455a7-f86e-4091-8480-cc504ed535a2" height="300"/>
<img align="left" src= "https://github.com/user-attachments/assets/85d0bcd8-eecc-4ce8-8df2-7b8fcc6e808c" height="250"/>

<br clear="left"/>

## Weekly Trends Screen
<img align="left" src= "https://github.com/user-attachments/assets/04e1ed41-83c4-478b-87cf-e28e675de620" height="300"/>

<br clear="left"/>

## Settings Screen
<img align="left" src= "https://github.com/user-attachments/assets/54d1a86b-f99b-4bb6-8cdd-912263ac8e25" height="300"/>

<br clear="left"/>

## Available Themes and Settings
<img align="left" src= "https://github.com/user-attachments/assets/110c5519-c21e-4379-a2a6-90f1b8110fd0" height="250"/>
<img align="left" src= "https://github.com/user-attachments/assets/6338633d-dfce-4e8d-8109-c4ca108423c3" height="250"/>
<img align="left" src= "https://github.com/user-attachments/assets/b9ce0afd-f132-40c8-9888-bd9b70290547" height="250"/>
<img align="left" src= "https://github.com/user-attachments/assets/742da80c-1124-4483-bf3a-6d5dcb76eab5" height="250"/>
<img align="left" src= "https://github.com/user-attachments/assets/34345eef-961a-4d28-b375-f2b679e1ce54" height="250"/>

<br clear="left"/>
