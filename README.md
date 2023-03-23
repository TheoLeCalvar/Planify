# Planify

As part of the PROCOM project proposed by IMT Atlantique, our project aims to develop an application that assists in creating a timetable for the TAF DCL.

## Documentation

Link with documentation in LaTex: https://www.overleaf.com/8255417117zgkydfwjgjmp 

## File tree

* [back-end/](back-end) &#8594; Folder with back-end services
  * [Data/](back-end/Data) &#8594; Data service
  * [Solver/](back-end/Solver) &#8594; Solver service
  * [User/](back-end/User) &#8594; User service
  * [create-test-users.sh](back-end/create-test-users.sh) &#8594; File to create users in database for testing
  * [diagram.puml](back-end/diagram.puml) &#8594; Sequence diagram
  * [docker-compose.yml](back-end/docker-compose.yml) &#8594; Docker-compose file for deployment 
  * [README.md](back-end/README.md)
* [front-end/](front-end)
  * [PlanningFront/](front-end/PlanningFront) &#8594; Folder with Angular project (front-end)
* [README.md](README.md)

This is a general readme, if you want more specific information about a service or front-end, see the specific readme (in each folder).

## TODO's 

### FRONT-END

- Add validations in form fields
- Add guard by role
- Implement a real login
- Separate the unavailabilities of Nantes and Brest
- Show current preferences
- Improve error control
- Add visualization of file csv on app
- Add a main form which allows one of the two TAF supervisors (reponsable TAF) to fill in the general informations. Then, After submitting the main form, two separate forms will be displayed for each supervisor to enter the unavailability periods for their respective campuses in Nantes and Brest.

### BACK-END

- Add notification by e-mail
- Add more validations (to make it more robust)

#### SOLVER

> **Note**: In the master and dev branches we find the clean code versions, and in the ChocoModel branch we find a code version of the Solver service with commented code of the unfinished KeySort implementation. 

- Manage precedence efficiently; either by working on the search strategy of the KeySort method, or by finding a way other than KeySort to create precedence between modules.
