# appointments-manager-api
Stylists Appointment Manager API

## What is there now!
3 endpoints to cover the required functionality:
- **POST** /stylists: to add new stylist
- **GET** /timeslots: to list all available time slots (considering all stylists)
- **POST** /appointments: to book appointment for certain customer on a given time

Detailed documentation for API can be found at: /swagger-ui.html

## What is planned to be covered
Error/Exception handling and some corner cases:
- available timeslots are calculated for today only, to be extended to cover x days from now; provided in API request.
- rejecting booking appointment if booked by another customer and no more stylists available.
- other minor error/exception handling
