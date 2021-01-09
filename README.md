# My Personal Project: A Film Photography Archive Application, "Analog Archive"

## Proposal
Analog Archive is an application that will be used to keep a digital archive 
of a photographer's analog photography. The application will allow the user to
store and view all of their analog photography, categorised by:
- Roll/Pack of Film
- Type of Film
- Film ISO
- Camera Used
- Film Manufacturer
- Film Expiry Date
- Film Developing Date
- Film Developing Location

This project is of interest to me personally due to the fact that I am very 
interested in film photography, and have been taking film photos for over 1
year. Unfortunately, there is currently no solution for digitally archiving
all of your photos, while also documenting the properties of the film that 
the photo is taken with. This application will provide useful functionality to
my workflow when editing photos, and organizing my archives of previous photos.

## User Stories
 - As a user, I want to be able to categorise all of my analog photography by
 the type of film used.
 - As a user, I want to be able to add a roll/pack of film to my current
 archive.
 - As a user, I want to be able to select a roll of film and view the photos 
 from that roll.
 - As a user, I want to be able to remove a roll/pack of film from my archive.
 - As a user, I want to be able to view a list of my rolls/packs of film,
 both complete list and filtered.
 - As a user, I want to be able to save my list of cameras and film
 - As a user, I want to be able to load a list of cameras and film from file
 
 ## Phase 4: Task 2 
 - This program has made appropriate use of a Map in the EditPanel class, 
 which is contained in the ui package
 
 ## Phase 4: Task 3
 If I had more time to work on my project, I would try to reduce the
 coupling in the program. This great amount of coupling can be seen between the
 classes in the panel package, and FilmCollection or CameraCollection. This
 makes the program really fragile, because if a method in the AnalogArchiveApp
 is changed, then it can break a lot of code in the panels package.