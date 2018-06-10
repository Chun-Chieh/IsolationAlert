# Isolation Alert

Isolation is an Android app to identify **[social isolation][Social Isolation]** based on the user’s social activities on a 
mobile device.

Adviser: [Dr. Yuting Zhang][Dr. Yuting Zhang] and [Dr. Dan Fulford][Dr Dan Fulford]
 
Contributed to [Metropolitan College][Metropolitan College] and [Sargent College][Sargent College] 
at Boston University.

Features
--------

#### Dashboard

* Device Usage
    * Acquire and show the counts of calls, sms, photos and videos of the day.
    
* Social App Usage
    * Acquire and show the onscreen time of common social apps. E.g. Facebook, Instagram.

* Activity (In progress)
    * Acquire and show the total time of user's activity (e.g. in a vehicle) within the day.

* Location (In progress)
    * Acquire the locations whenever a user moves (or changes the location) with a specific time-span. 

#### EMA (Ecological Momentary Assessment)
* Survey (normal)
    * Acquire the answers chosen by the user for further mental assessment.
    
* Survey (sensor-initiated) (In progress)
    * Pop up a notification to ask a yes-no question when microphone detects conversation 
    within given block of time.


#### Conversation Recognition
* Training session (In progress)
    * Let the user record the voice in a short span of time.

* Testing session (In progress)
    * Retrieve the accuracy of the user's voice from the microphone and keep the specific information.
     (e.g. location, time) 


Screenshots
--------
[![mutt light](https://github.com/altercation/solarized/raw/master/img/screen-mutt-light-th.png)](https://github.com/altercation/solarized/raw/master/img/screen-mutt-light.png)


Platform
--------
Editor: Android Studio 3.1

Language: Java 8

Library Usage
--------
[Gson][Gson]

[GreenDAO][GreenDao]

[EasyPermissions][EasyPermissions]





[Social Isolation]:https://en.wikipedia.org/wiki/Social_isolation
[Dr. Yuting Zhang]: http://www.bu.edu/met/faculty/full-time/yuting-zhang/
[Dr Dan Fulford]:https://www.bu.edu/sargent/profile/dan-fulford/
[Sargent College]:https://www.bu.edu/sargent
[Metropolitan College]:http://www.bu.edu/met
[Gson]: https://github.com/google/gson
[GreenDAO]: https://github.com/greenrobot/greenDAO
[EasyPermissions]: https://github.com/googlesamples/easypermissions