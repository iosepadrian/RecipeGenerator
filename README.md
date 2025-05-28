# RecipeGenerator

Recipe generator using GeminiAPi GenerativeModel library. 
MVVM Jetpack compose targeting latest android version, using Hilt for injection and Room for database persistance.
There is some leftover code of using Retrofit for network (tried using some AI tools with endpoints), but ended up using GenerativeModel from GeminiApi as it was more easy and free.
For images I used a placeholder.
The navigation is made by 3 screens. The main screen presents the list of recipes (always 3 shown) with a btton that refreshes them, a searchbar for query and a button to display the Favorite recipes.
The second screen is the Favorite screen where you can see the list of favorite recipes which is persisted with Room database.
The third screen is the Recipe Details screen where you can see the details of the recipes.
You can add to favorite from both main screen and details screen.
