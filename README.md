
# <img src="https://user-images.githubusercontent.com/86946600/159285413-407a7cb4-8e9c-4359-b76c-9cef6b46487d.png" align="center" width="40" height="40"> Quick Weather

Shows current weather details as well as hourly and daily forecast. <br/>
**__Shows data even if internet is not available__**


# Flow of App

### If Internet is available
1. Checks if GPS is enabled:
    - If enabled: Gets latitude and longitude of current location and stores it in Shared Preference.
    - If not enabled: Asks user to enable it.
1. Fetches Data from openwathermap API wiht the help of Retrofit using the latitude and longitude from Shared Preferences.
2. Stores it in local Database with the help of ROOM.
3. Displays the Data to the User from the database.

### If Internet is not available
1. Displays the stored data from the database.
## Demo
![Screenshot_Collage jpg](https://user-images.githubusercontent.com/86946600/159305554-b4b693d5-bc39-4d8a-affe-10ed4336465b.png)

[Click here](https://drive.google.com/file/d/1tyax458TjD_Au6etMGLA_AjAHVCNArrR/view?usp=sharing) to watch a video demo.
## Tutorial Links

 - [Retrofit Tutorial](https://www.youtube.com/playlist?list=PLrnPJCHvNZuCbuD3xpfKzQWOj3AXybSaM)
 - [MVVM Architecture + ROOM](https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118)


## Small edge cases I considered, that I think helps in improving user experience

- Shows GPS is on/off even if internet is off
- Doesn't ask GPS permission if internet is off 
   cuz it doesn't matter.
- Doesn't ask for GPS again if the user hits "ignore" 
   (Unless user wishes to enable it).


