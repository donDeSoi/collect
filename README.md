# collect / don de soi

Android application for blood collects, can be used for any other location purpose.

Features : 
Display informations about different gives (organs, plasma, ...). Display the different blood collect centers on a GoogleMap and their informations (as markers) by fetching the informations on the website of the french blood collect establishment (EFS). The informations are stored in a SQLIte database in case the user would not have an internet connection. There are also some hardcoded data (in the ConstantValues class) about the blood collect centers in case the database doesn't have enough entries.
Share your activity on facebook and twitter. Show notifications. Vibrate the phone. Save the date of the last give. See if the center is crowded (not complete). There are also other information about the different gives.

TODO : 
The geocoder (the thing used to get the coordinates with the address) isn't the most efficient in the world, if you want a more efficient one use the reverse geocoder (this one is not free).

You must complete the AndroidManifest.xml with your google maps API key, get one here :
https://developers.google.com/maps/documentation/geocoding/get-api-key

Made by french students (ECE Paris)
