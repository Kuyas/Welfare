![logo](./app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png?raw=true "welfare app logo")
# Welfare App
This android application has been made by students of [BITS Pilani](http://www.bits-pilani.ac.in/) as their Internship (Practise School) project under Centre of Development for Imaging Technology ([CDIT](http://cdit.org))
The application has been made for [Kerala Traders Welfare Board](https://kerala.gov.in/welfare-fund-boards) to allow remote registration of traders. It also allows the traders to pay annual fee, check application status and make changes to details.
The developers are:
Ishan Bhanuka ([twitu](https://github.com/twitu))
Omkar Kanade ([omkar-decode](https://github.com/omkar-decode))
Shreyas Sunil Kulkarni ([Kuyas](https://github.com/Kuyas))

## Note
The backend of the app is written in PHP and stored in [Welfare-backend](https://github.com/Kuyas/Welfare-backend) repo. We have taken a number of design decisions based on given specifications and our understanding of how UX should be. We have taken the help of many blogs, videos and only tutorials for implementing the given features.

# Features
#### Authentication
It has a two-factor authentication using MSG91 otp service. It also provides a forgot password to change password.

#### Registration
It has a 5 part form which has performs numerous text related checks and highlights errors before storing the data. The forms also have a caching mechanism that stores users latest entry allowing them to edit the data at a later stage. The network requests are handled using retrofit2 library

#### Class Change
Allows users to change annual turnover

#### Renew Membership
Allows user to renew annual membership through online payment

#### Status
Display live status of any claims/application submitted by user

# Features left to implement
There are many features left to implement before the app is completely functional and deployable, however bulk of the work is done.
1. Integrate payment gateway for renewal of membership
2. Hosting of PHP backend on protected server
3. Malayalam Translation of all strings
4. Yearly notification to remind users to make annual payment
5. Engage OTP service
6. A number of security considerations
6.1 Delete cache files on logout
6.2 Remove the need for cache files by maintaining sessions
6.3 Make sign up more secure by implementing some form of otp in the server
7. Give user more informative prompts in case of errors or mistakes
8. Make user experience more smooth by showing loading bars for network requests
9. Mark compulsory fields to make UX more intuitive

# Contribute or use the code
The app is functional and can be directly cloned and run. Feel free to contribute to this project or use it for your own purpose. **For any suggestions or bugs create an issue** and we'll get back to you. To contribute make a fork and generate a pull request with your changes (and you'll need the backend which is in this [repo](https://github.com/Kuyas/Welfare-backend))

